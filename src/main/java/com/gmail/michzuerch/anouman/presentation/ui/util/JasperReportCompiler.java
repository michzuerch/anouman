package com.gmail.michzuerch.anouman.presentation.ui.util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;

public class JasperReportCompiler {
    public static byte[] compileJRXML(byte[] val) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager
                    .compileReport(new ByteArrayInputStream(val));
            return (SerializationUtils.serialize(jasperReport));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
