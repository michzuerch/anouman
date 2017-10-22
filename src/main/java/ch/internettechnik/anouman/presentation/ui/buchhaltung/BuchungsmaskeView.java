package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.FloatField;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView("Buchungsmaske")
public class BuchungsmaskeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BuchungsmaskeView.class.getName());
    @Inject
    Menu menu;
    @Inject
    BuchhaltungFacade buchhaltungFacade;
    @Inject
    KontoklasseFacade kontoklasseFacade;
    @Inject
    KontohauptgruppeFacade kontohauptgruppeFacade;
    @Inject
    KontogruppeFacade kontogruppeFacade;
    @Inject
    KontoFacade kontoFacade;
    @Inject
    MehrwertsteuercodeFacade mehrwertsteuercodeFacade;
    private NativeSelect<Buchhaltung> buchhaltungNativeSelect = new NativeSelect<>();
    private Panel sollPanel = new Panel("Sollkonto");
    private NativeSelect<Kontoklasse> sollKontoklasse = new NativeSelect<>();
    private NativeSelect<Kontohauptgruppe> sollKontohauptgruppe = new NativeSelect<>();
    private NativeSelect<Kontogruppe> sollKontogruppe = new NativeSelect<>();
    private NativeSelect<Konto> sollKonto = new NativeSelect<>();
    private Label sollKontoLabel = new Label("Kontonummer Soll:");
    private HorizontalLayout sollKontoLayout = new HorizontalLayout();
    private Panel habenPanel = new Panel("Habenkonto");
    private NativeSelect<Kontoklasse> habenKontoklasse = new NativeSelect<>();
    private NativeSelect<Kontohauptgruppe> habenKontohauptgruppe = new NativeSelect<>();
    private NativeSelect<Kontogruppe> habenKontogruppe = new NativeSelect<>();
    private NativeSelect<Konto> habenKonto = new NativeSelect<>();
    private Label habenKontoLabel = new Label("Kontonummer Haben:");
    private HorizontalLayout habenKontoLayout = new HorizontalLayout();
    private Panel buchenPanel = new Panel("Buchen");
    private NativeSelect<Mehrwertsteuercode> mehrwertsteuercodeNativeSelect = new NativeSelect<>();
    private FloatField betragField = new FloatField("Betrag");
    private Button buchenButton = new Button("Buchen");
    private HorizontalLayout buchenLayout = new HorizontalLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        buchhaltungNativeSelect.setCaption("Buchhaltung");
        buchhaltungNativeSelect.setEmptySelectionAllowed(false);
        buchhaltungNativeSelect.setItemCaptionGenerator(buchhaltung -> buchhaltung.getBezeichnung() + " " + buchhaltung.getJahr() + " " + buchhaltung.getId());
        buchhaltungNativeSelect.setItems(buchhaltungFacade.findAll());
        buchhaltungNativeSelect.setSelectedItem(buchhaltungFacade.findAll().get(0));

        sollKontoklasse.setCaption("Kontoklasse");
        sollKontoklasse.setItemCaptionGenerator(kontoklasse -> kontoklasse.getBezeichnung() + " " + kontoklasse.getShowKontonummer());
        sollKontoklasse.setEmptySelectionAllowed(false);

        sollKontohauptgruppe.setCaption("Kontohauptgruppe");
        sollKontohauptgruppe.setItemCaptionGenerator(kontohauptgruppe -> kontohauptgruppe.getBezeichnung() + " " + kontohauptgruppe.getShowKontonummer());
        sollKontohauptgruppe.setEmptySelectionAllowed(false);

        sollKontogruppe.setCaption("Kontogruppe");
        sollKontogruppe.setItemCaptionGenerator(kontogruppe -> kontogruppe.getBezeichnung() + " " + kontogruppe.getShowKontonummer());
        sollKontogruppe.setEmptySelectionAllowed(false);

        sollKonto.setCaption("Konto");
        sollKonto.setItemCaptionGenerator(konto -> konto.getBezeichnung() + " " + konto.getShowKontonummer());
        sollKonto.setEmptySelectionAllowed(false);

        sollKontoLabel.setStyleName(ValoTheme.LABEL_HUGE);
        sollKontoLayout.setMargin(true);
        sollKontoLayout.addComponents(sollKontoklasse, sollKontohauptgruppe, sollKontogruppe, sollKonto, sollKontoLabel);
        sollPanel.setContent(sollKontoLayout);

        habenKontoklasse.setCaption("Kontoklasse");
        habenKontoklasse.setItemCaptionGenerator(kontoklasse -> kontoklasse.getBezeichnung() + " " + kontoklasse.getShowKontonummer());
        habenKontoklasse.setEmptySelectionAllowed(false);

        habenKontohauptgruppe.setCaption("Kontohauptgruppe");
        habenKontohauptgruppe.setItemCaptionGenerator(kontohauptgruppe -> kontohauptgruppe.getBezeichnung() + " " + kontohauptgruppe.getShowKontonummer());
        habenKontohauptgruppe.setEmptySelectionAllowed(false);

        habenKontogruppe.setCaption("Kontogruppe");
        habenKontogruppe.setItemCaptionGenerator(kontogruppe -> kontogruppe.getBezeichnung() + " " + kontogruppe.getShowKontonummer());
        habenKontogruppe.setEmptySelectionAllowed(false);

        habenKonto.setCaption("Konto");
        habenKonto.setItemCaptionGenerator(konto -> konto.getBezeichnung() + " " + konto.getShowKontonummer());
        habenKonto.setEmptySelectionAllowed(false);

        habenKontoLabel.setStyleName(ValoTheme.LABEL_HUGE);
        habenKontoLayout.addComponents(habenKontoklasse, habenKontohauptgruppe, habenKontogruppe, habenKonto, habenKontoLabel);
        habenKontoLayout.setMargin(true);
        habenPanel.setContent(habenKontoLayout);

        addComponent(menu);
        addComponent(buchhaltungNativeSelect);
        addComponent(new HorizontalLayout(sollPanel, habenPanel));


        sollKontoklasse.setItems(kontoklasseFacade.findByBuchhaltung(buchhaltungNativeSelect.getValue()));
        sollKontoklasse.setSelectedItem(kontoklasseFacade.findAll().get(0));

        sollKontoklasse.addValueChangeListener(
                valueChangeEvent -> {
                    sollKontohauptgruppe.setItems(kontohauptgruppeFacade.findByKontoklasse(valueChangeEvent.getValue()));
                    sollKontohauptgruppe.setSelectedItem(kontohauptgruppeFacade.findByKontoklasse(valueChangeEvent.getValue()).get(0));
                });
        sollKontohauptgruppe.addValueChangeListener(
                valueChangeEvent -> {
                    sollKontogruppe.setItems(kontogruppeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()));
                    sollKontogruppe.setSelectedItem(kontogruppeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()).get(0));
                });

        sollKontogruppe.addValueChangeListener(
                valueChangeEvent -> {
                    sollKonto.setItems(kontoFacade.findByKontogruppe(valueChangeEvent.getValue()));
                    sollKonto.setSelectedItem(kontoFacade.findByKontogruppe(valueChangeEvent.getValue()).get(0));
                });

        sollKonto.addValueChangeListener(
                valueChangeEvent -> {
                    sollKontoLabel.setValue("Konto Soll: " + valueChangeEvent.getValue().getShowKontonummer());
                });


        habenKontoklasse.setItems(kontoklasseFacade.findByBuchhaltung(buchhaltungNativeSelect.getValue()));
        habenKontoklasse.setSelectedItem(kontoklasseFacade.findAll().get(0));

        habenKontoklasse.addValueChangeListener(
                valueChangeEvent -> {
                    habenKontohauptgruppe.setItems(kontohauptgruppeFacade.findByKontoklasse(valueChangeEvent.getValue()));
                    habenKontohauptgruppe.setSelectedItem(kontohauptgruppeFacade.findByKontoklasse(valueChangeEvent.getValue()).get(0));
                });
        habenKontohauptgruppe.addValueChangeListener(
                valueChangeEvent -> {
                    habenKontogruppe.setItems(kontogruppeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()));
                    habenKontogruppe.setSelectedItem(kontogruppeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()).get(0));
                });

        habenKontogruppe.addValueChangeListener(
                valueChangeEvent -> {
                    habenKonto.setItems(kontoFacade.findByKontogruppe(valueChangeEvent.getValue()));
                    habenKonto.setSelectedItem(kontoFacade.findByKontogruppe(valueChangeEvent.getValue()).get(0));
                });

        habenKonto.addValueChangeListener(
                valueChangeEvent -> {
                    habenKontoLabel.setValue("Konto Haben: " + valueChangeEvent.getValue().getShowKontonummer());
                });

        mehrwertsteuercodeNativeSelect.setItemCaptionGenerator(mehrwertsteuercode -> mehrwertsteuercode.getBezeichnung());
        mehrwertsteuercodeNativeSelect.setEmptySelectionAllowed(false);
        mehrwertsteuercodeNativeSelect.setItems(mehrwertsteuercodeFacade.findByBuchhaltung(buchhaltungNativeSelect.getValue()));
        mehrwertsteuercodeNativeSelect.setCaption("Mehrwertsteuercode");


        buchenLayout.addComponents(mehrwertsteuercodeNativeSelect, betragField, buchenButton);
        buchenLayout.setMargin(true);
        buchenPanel.setContent(buchenLayout);
        addComponent(buchenPanel);

    }
}
