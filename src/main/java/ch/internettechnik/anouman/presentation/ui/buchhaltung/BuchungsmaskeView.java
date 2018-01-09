package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.FloatField;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@UIScope
@SpringView(name = "BuchungsmaskeView")
public class BuchungsmaskeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BuchungsmaskeView.class.getName());
    @Inject
    Menu menu;
    @Inject
    BuchhaltungDeltaspikeFacade buchhaltungDeltaspikeFacade;
    @Inject
    KontoklasseDeltaspikeFacade kontoklasseDeltaspikeFacade;
    @Inject
    KontohauptgruppeDeltaspikeFacade kontohauptgruppeDeltaspikeFacade;
    @Inject
    KontogruppeDeltaspikeFacade kontogruppeDeltaspikeFacade;
    @Inject
    KontoDeltaspikeFacade kontoDeltaspikeFacade;
    @Inject
    MehrwertsteuercodeDeltaspikeFacade mehrwertsteuercodeDeltaspikeFacade;
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
        buchhaltungNativeSelect.setItems(buchhaltungDeltaspikeFacade.findAll());
        buchhaltungNativeSelect.setSelectedItem(buchhaltungDeltaspikeFacade.findAll().get(0));

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


        sollKontoklasse.setItems(kontoklasseDeltaspikeFacade.findByBuchhaltung(buchhaltungNativeSelect.getValue()));
        sollKontoklasse.setSelectedItem(kontoklasseDeltaspikeFacade.findAll().get(0));

        sollKontoklasse.addValueChangeListener(
                valueChangeEvent -> {
                    sollKontohauptgruppe.setItems(kontohauptgruppeDeltaspikeFacade.findByKontoklasse(valueChangeEvent.getValue()));
                    sollKontohauptgruppe.setSelectedItem(kontohauptgruppeDeltaspikeFacade.findByKontoklasse(valueChangeEvent.getValue()).get(0));
                });
        sollKontohauptgruppe.addValueChangeListener(
                valueChangeEvent -> {
                    sollKontogruppe.setItems(kontogruppeDeltaspikeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()));
                    sollKontogruppe.setSelectedItem(kontogruppeDeltaspikeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()).get(0));
                });

        sollKontogruppe.addValueChangeListener(
                valueChangeEvent -> {
                    sollKonto.setItems(kontoDeltaspikeFacade.findByKontogruppe(valueChangeEvent.getValue()));
                    sollKonto.setSelectedItem(kontoDeltaspikeFacade.findByKontogruppe(valueChangeEvent.getValue()).get(0));
                });

        sollKonto.addValueChangeListener(
                valueChangeEvent -> {
                    sollKontoLabel.setValue("Konto Soll: " + valueChangeEvent.getValue().getShowKontonummer());
                });


        habenKontoklasse.setItems(kontoklasseDeltaspikeFacade.findByBuchhaltung(buchhaltungNativeSelect.getValue()));
        habenKontoklasse.setSelectedItem(kontoklasseDeltaspikeFacade.findAll().get(0));

        habenKontoklasse.addValueChangeListener(
                valueChangeEvent -> {
                    habenKontohauptgruppe.setItems(kontohauptgruppeDeltaspikeFacade.findByKontoklasse(valueChangeEvent.getValue()));
                    habenKontohauptgruppe.setSelectedItem(kontohauptgruppeDeltaspikeFacade.findByKontoklasse(valueChangeEvent.getValue()).get(0));
                });
        habenKontohauptgruppe.addValueChangeListener(
                valueChangeEvent -> {
                    habenKontogruppe.setItems(kontogruppeDeltaspikeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()));
                    habenKontogruppe.setSelectedItem(kontogruppeDeltaspikeFacade.findByKontohauptgruppe(valueChangeEvent.getValue()).get(0));
                });

        habenKontogruppe.addValueChangeListener(
                valueChangeEvent -> {
                    habenKonto.setItems(kontoDeltaspikeFacade.findByKontogruppe(valueChangeEvent.getValue()));
                    habenKonto.setSelectedItem(kontoDeltaspikeFacade.findByKontogruppe(valueChangeEvent.getValue()).get(0));
                });

        habenKonto.addValueChangeListener(
                valueChangeEvent -> {
                    habenKontoLabel.setValue("Konto Haben: " + valueChangeEvent.getValue().getShowKontonummer());
                });

        mehrwertsteuercodeNativeSelect.setItemCaptionGenerator(mehrwertsteuercode -> mehrwertsteuercode.getBezeichnung());
        mehrwertsteuercodeNativeSelect.setEmptySelectionAllowed(false);
        mehrwertsteuercodeNativeSelect.setItems(mehrwertsteuercodeDeltaspikeFacade.findByBuchhaltung(buchhaltungNativeSelect.getValue()));
        mehrwertsteuercodeNativeSelect.setCaption("Mehrwertsteuercode");


        buchenLayout.addComponents(mehrwertsteuercodeNativeSelect, betragField, buchenButton);
        buchenLayout.setMargin(true);
        buchenPanel.setContent(buchenLayout);
        addComponent(buchenPanel);

    }
}
