package com.pollux.sherpa.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

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

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

}
