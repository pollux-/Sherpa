package com.pollux.sherpa.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SPARK on 07/02/16.
 */
public class Util {

    public static String getTime(String time) {
        DateFormat formatter1;
        formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            SimpleDateFormat simpleDateFormatArrivals = new SimpleDateFormat("HH:mm", Locale.UK);
            Date date = formatter1.parse(time);
            String format = simpleDateFormatArrivals.format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
