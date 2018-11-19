package com.aucklandvision.biblerecite.theme;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.utils.ThemeColor;

public class ThemeView extends LinearLayout {
    private ImageView image_theme_rectangle;
    private TextView text_theme_colortext;
    private ImageView image_theme_selected;
    public ThemeView(Context context) {
        super(context);
        init(context);
    }

    public ThemeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.theme_content, this, true);

        image_theme_rectangle = (ImageView) findViewById(R.id.image_theme_rectangle);
        text_theme_colortext = (TextView) findViewById(R.id.text_theme_colortext);
        image_theme_selected = (ImageView) findViewById(R.id.image_theme_selected);
    }

    public void setView(Theme theme) {
        GradientDrawable themeShape = (GradientDrawable) image_theme_rectangle.getBackground();
        themeShape.setColor(theme.getArgb_content());
        text_theme_colortext.setText(theme.getColorName());

        if (ThemeColor.ARGB == theme.getArgb_content())
            image_theme_selected.setVisibility(VISIBLE);
        else
            image_theme_selected.setVisibility(INVISIBLE);
    }
}
