package com.aucklandvision.biblerecite.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;

public class FontUtil extends GradientDrawable {
    public static int fontSize = 0;
    public static Typeface typeface = null;
    private Context context;
    public FontUtil(Context context) {
        this.context = context;
    }
    public Typeface setFonttype(String strFonttype) {
        Typeface typeface = null;
        if (strFonttype.equals("Americano")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/americano.ttf");
        } else if (strFonttype.equals("Letter")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/letter.ttf");
        } else if (strFonttype.equals("Music")) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/music.ttf");
        }
        return typeface;

    }
    public void changeFontsize() {
        setColor(0xff69AAE6);
        setCornerRadius(10);
    }

    public void returnFontsize() {
        setColor(0x00ffffff);
        setStroke(3, 0xff3C3F41);
        setCornerRadius(10);
    }
}
