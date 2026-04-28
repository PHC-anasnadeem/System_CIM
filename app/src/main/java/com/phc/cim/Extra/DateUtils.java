package com.phc.cim.Extra;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatMSJsonDate(String dateStr) {
        if (dateStr == null || dateStr.equals("null") || dateStr.isEmpty() || dateStr.equals("N/A")) {
            return "N/A";
        }
        try {
            if (dateStr.startsWith("/Date(")) {
                String milliStr = dateStr.replace("/Date(", "").replace(")/", "");
                long millis;
                if (milliStr.contains("+")) {
                    millis = Long.parseLong(milliStr.substring(0, milliStr.indexOf("+")));
                } else if (milliStr.contains("-")) {
                    millis = Long.parseLong(milliStr.substring(0, milliStr.indexOf("-")));
                } else {
                    millis = Long.parseLong(milliStr);
                }
                Date date = new Date(millis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                return sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
