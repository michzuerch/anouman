package com.gmail.michzuerch.anouman.presentation.ui.testviews;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.reports.PrintPreviewReport;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CDIView("ReportUITestView")
public class ReportUITestView extends VerticalLayout implements View {

    @Inject
    AdresseDeltaspikeFacade adresseDeltaspikeFacade;

    private Component buildDownloadButtons() {
        CssLayout layout = new CssLayout();
        layout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        //SerializableSupplier<List<? extends Adresse>> itemsSupplier = () -> adresseDeltaspikeFacade.findAll();

//        Button pdf = new Button("Pdf");
//        report.downloadPdfOnClick(pdf, "adressen.pdf", itemsSupplier);
//
//        Button xls = new Button("Xls");
//        report.downloadXlsOnClick(xls, "adressen.xls", itemsSupplier);
//
//        Button docx = new Button("Docx");
//        report.downloadDocxOnClick(docx, "adressen.docx", itemsSupplier);
//
//        Button pptx = new Button("Pptx");
//        report.downloadPptxOnClick(pptx, "adressen.pptx", itemsSupplier);
//
//        Button rtf = new Button("Rtf");
//        report.downloadRtfOnClick(rtf, "adressen.rtf", itemsSupplier);
//
//        Button odt = new Button("Odt");
//        report.downloadOdtOnClick(odt, "adressen.odt", itemsSupplier);
//
//        Button csv = new Button("Csv");
//        report.downloadCsvOnClick(csv, "adressen.csv", itemsSupplier);
//
//        Button xml = new Button("Xml");
//        report.downloadXmlOnClick(xml, "adressen.xml", itemsSupplier);
//        layout.addComponents(pdf, xls, docx, pptx, rtf, csv, xml);
        return layout;
    }

    private Component buildSimpleReport() {
        Style headerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).build();
        Style groupStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM_BOLD).build();
        AbstractColumn ort;

        //report = new PrintPreviewReport<>(Adresse.class, "firma", "anrede", "vorname", "nachname", "strasse", "ort", "postleitzahl");
        PrintPreviewReport<ReportUITestData> report = new PrintPreviewReport<ReportUITestData>(ReportUITestData.class);


        report.getReportBuilder()
                .setMargins(20, 20, 40, 40)
                .setTitle("Adressen")
                .addAutoText("For internal use only", AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT, 200, headerStyle)
                .addAutoText(LocalDateTime.now().toString(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, headerStyle)
                .setPrintBackgroundOnOddRows(true)
                .addWatermark("Watermark")
                .addColumn(ort = ColumnBuilder.getNew()
                        .setColumnProperty("ort", String.class)
                        .setTitle("Ort")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("nachname", String.class)
                        .setTitle("Nachname")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("vorname", String.class)
                        .setTitle("Vorname")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("nachname", String.class)
                        .setTitle("Nachname")
                        .build());

        List<ReportUITestData> list = new ArrayList<>();
        list.add(new ReportUITestData("Michael", "Manoush", "internettechnik estonia öä", "Talinn"));
        report.setItems(list);

        return report;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponents(buildSimpleReport(), buildDownloadButtons());
    }
}
