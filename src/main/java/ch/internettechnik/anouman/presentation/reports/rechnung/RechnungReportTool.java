package ch.internettechnik.anouman.presentation.reports.rechnung;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import ch.internettechnik.anouman.util.Runden;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by michzuerch on 29.06.15.
 */
public class RechnungReportTool implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(RechnungReportTool.class.getName());

    //@todo java.lang.ClassNotFoundException: org.codehaus.groovy.control.CompilationFailedException
    public static byte[] getPdf(Rechnung val, ReportTemplate reportTemplate) {
        JasperReport report;
        JasperPrint print;
        HashMap params = new HashMap();
        params.put("id", val.getId());

        String adresszeile1;
        String adresszeile2;
        String adresszeile3;
        String adresszeile4;

        Adresse adresse = val.getAdresse();

        /* todo Check wegen Firma?? */
        if (adresse.getFirma() == null) {
            adresszeile1 = adresse.getAnrede() + " " + adresse.getVorname() + " " + adresse.getNachname();
            adresszeile2 = adresse.getStrasse();
            adresszeile3 = adresse.getPostleitzahl() + " " + adresse.getOrt();
            adresszeile4 = "";
        } else {
            adresszeile1 = adresse.getFirma();
            adresszeile2 = adresse.getAnrede() + " " + adresse.getVorname() + " " + adresse.getNachname();
            adresszeile3 = adresse.getStrasse();
            adresszeile4 = adresse.getPostleitzahl() + " " + adresse.getOrt();
        }

        params.put("az1", adresszeile1);
        params.put("az2", adresszeile2);
        params.put("az3", adresszeile3);
        params.put("az4", adresszeile4);

        SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat rnformat = new SimpleDateFormat("ddMMyy");
        params.put("Rechnungsnummer", val.getId() + rnformat.format(val.getRechnungsdatum()));
        params.put("Rechnungsdatum", dateformat.format(val.getRechnungsdatum()));
        params.put("Zahlungsziel", val.getFaelligInTagen() + " Tage");
        params.put("Zwischentotal", val.getZwischentotal());
        params.put("Mehrwertsteuer", Runden.runden(val.getMehrwertsteuer()));
        params.put("Rechnungstotal", Runden.runden(val.getRechnungstotal()));
        List<Rechnungsposition> list = new ArrayList<>();
        for (ch.internettechnik.anouman.backend.entity.Rechnungsposition rp : val.getRechnungspositionen()) {
            list.add(new Rechnungsposition(rp.getBezeichnung(), rp.getBezeichnunglang(), rp.getMengeneinheit(),
                    rp.getStueckpreis(), rp.getAnzahl(), rp.getPositionstotal()));
        }
        for (ch.internettechnik.anouman.backend.entity.Aufwand aw : val.getAufwands()) {
            list.add(new Rechnungsposition(aw.getTitel(), aw.getBezeichnung(), "Stunden",
                    aw.getRechnung().getAdresse().getStundensatz(),
                    aw.getDauerInStunden(), aw.getPositionstotal()));
        }
        logger.debug("Anzahl gefundende Rechnungspositonen:" + list.size());
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(list);

        Locale locale = new Locale("de", "CH");
        params.put(JRParameter.REPORT_LOCALE, locale);

        byte[] bytes = new byte[0];
        try {
            byte[] template = reportTemplate.getTemplate();

            JasperDesign jasperDesign = JRXmlLoader.load(new ByteArrayInputStream(template));
            report = JasperCompileManager.compileReport(jasperDesign);
            print = JasperFillManager.fillReport(report, params, collectionDataSource);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (JRException e1) {
            e1.printStackTrace();
        }
        return bytes;
    }
}
