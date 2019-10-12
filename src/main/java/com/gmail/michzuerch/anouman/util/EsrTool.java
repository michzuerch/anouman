/*
 * Copyright (c) 2005 internettechnik.ch. All Rights Reserved.
 */
package com.gmail.michzuerch.anouman.backend.util;

import com.gmail.michzuerch.anouman.backend.util.exception.EsrToolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EsrTool {
    private static Logger logger = LoggerFactory.getLogger(EsrTool.class.getName());

    public boolean isPlzSwitzerland(String plz) {
        return plz.matches("\\d\\d\\d\\d");
    }

    public String formatEsrBetragLeft(String val) {
        val = removeNonNumberChars(val);
        val = val.substring(0, val.length() - 2);

        return val;
    }

    public String formatEsrBetragRight(String val) {
        val = removeNonNumberChars(val);
        val = val.substring(val.length() - 2, val.length());

        return val;
    }

    public String formatKlientennummer(String val) throws EsrToolException {
        if (val.length() < 3) throw new EsrToolException("Klientennummer kleiner als 3-stellig:[" + val + "]");
        if (val.length() == 3) {
            return val;
        }
        String p = val.substring(1, 4);
        return p;
    }

    public String formatDokumentnummer(String val) throws EsrToolException {
        if (val.length() < 6) throw new EsrToolException("Dokumentnummer kleiner als 6-stellig:[" + val + "]");
        if (val.length() == 6) {
            return val;
        }
        return val.substring(0, 6);
    }

    public String generatePruefziffer(String val) {
        int modulo = 0;
        int uebertrag = 0;
        int[] table = new int[10];

        table[0] = 0;
        table[1] = 9;
        table[2] = 4;
        table[3] = 6;
        table[4] = 8;
        table[5] = 2;
        table[6] = 7;
        table[7] = 1;
        table[8] = 3;
        table[9] = 5;

        for (int t = 0; t < val.length(); t++) {
            int value = 0;
            try {
                value = Integer.parseInt(val.substring(t, t + 1));
            } catch (Exception ex) {
                System.err.println(ex);
            }
            uebertrag = table[(value + uebertrag) % 10];
        }

        modulo = (10 - uebertrag) % 10;

        return String.valueOf(modulo);
    }

    private String removeNonNumberChars(String val) {
        char[] val2 = val.toCharArray();
        String result = new String();

        for (int t = 0; t < val.length(); t++) {
            switch (val2[t]) {
                case '1':
                    result = result + val2[t];

                    break;

                case '2':
                    result = result + val2[t];

                    break;

                case '3':
                    result = result + val2[t];

                    break;

                case '4':
                    result = result + val2[t];

                    break;

                case '5':
                    result = result + val2[t];

                    break;

                case '6':
                    result = result + val2[t];

                    break;

                case '7':
                    result = result + val2[t];

                    break;

                case '8':
                    result = result + val2[t];

                    break;

                case '9':
                    result = result + val2[t];

                    break;

                case '0':
                    result = result + val2[t];

                    break;
            }
        }

        return result;
    }

    public String formatLeadingZeroesNumeric(String val, int len) {
        String result = new String();
        String val2 = removeNonNumberChars(val);

        try {
            String zeroString = new String();
            int anzahlZeroes = len - val2.length();

            for (int t = 0; t < anzahlZeroes; t++) {
                zeroString = zeroString + "0";
            }

            result = zeroString + val2;
        } catch (NumberFormatException pe) {
            logger.error(pe.getLocalizedMessage());

            return null;
        }

        return result;
    }

    public String generateCodierzeileString(String betrag,
                                            String klientennummer, String dokumentnummer) throws EsrToolException {
        //String esrTeilnehmernummer = "010 479 943";
        String esrTeilnehmernummer = "010085135";
        String result = new String();
        String fieldA = "01"; //Belegart 01=Beleg mit vorgedrucktem Betrag
        String fieldB = formatLeadingZeroesNumeric(betrag, 10); //Betrag
        String fieldC = generatePruefziffer(fieldA + fieldB); //Pruefziffer A + B

        String fieldD = ">"; //Steuerzeichen

        String fieldE = "207611";
        //String fieldE = formatLeadingZeroesNumeric(klientennummer, 6); //Partnernummer
        String fieldF1 = formatKlientennummer(klientennummer);
        String fieldF2 = "00990000";
        String fieldF3 = formatDokumentnummer(dokumentnummer);
        String fieldF4 = "000";
        //String fieldG = generatePruefziffer(fieldF1 + fieldF2); //Pruefziffer F1 + F2
        String fieldG = generatePruefziffer(fieldE + fieldF1 + fieldF2 + fieldF3 + fieldF4);

        String fieldH = "+ "; //Steuerzeichen
        String fieldI = esrTeilnehmernummer; //ESR-Teilnehmernummer
        String fieldJ = ">"; //Steuerzeichen
        result = fieldA + fieldB + fieldC + fieldD + fieldE + fieldF1 +
                fieldF2 + fieldF3 + fieldF4 + fieldG + fieldH + fieldI + fieldJ;
        return result;
    }

    public String generateReferenznummerString(String klientennummer,
                                               String dokumentnummer) throws EsrToolException {
        String field1 = "207611"; //Partnernummer
        String field2 = formatKlientennummer(klientennummer); //Debitornummer

        String field3 = "00990000";
        String field4 = formatDokumentnummer(dokumentnummer);
        String field5 = "000";

        String field6 = generatePruefziffer(field1 + field2 + field3 + field4 + field5); //Pruefziffer F1 + F2

        String helpResult = field1 + field2 + field3 + field4 + field5 + field6;

        String block1 = helpResult.substring(0, 2);
        String block2 = helpResult.substring(2, 7);
        String block3 = helpResult.substring(7, 12);
        String block4 = helpResult.substring(12, 17);
        String block5 = helpResult.substring(17, 22);
        String block6 = helpResult.substring(22, 27);

        String result = block1 + " " + block2 + " " + block3 + " " + block4 +
                " " + block5 + " " + block6;


        return result;
    }
}
