package com.aucklandvision.biblerecite.font;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.utils.FontUtil;

import java.util.ArrayList;

public class FontDialog {
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private FontPresenter fontPresenter;
    private Context context;

    private View prevViewFontsize;
    private View prevViewFonttype;

    public FontDialog(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
    }

    public void showFontDialog() {
        fontPresenter = new FontPresenter();
        context = mainActivity.getApplicationContext();
        //font dialog setting
        Dialog dialog = new Dialog(mainActivity);
        int width = (int) (mainActivity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (mainActivity.getResources().getDisplayMetrics().heightPixels * 0.35);
        //v1.0.1
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //v1.0.1
        dialog.setContentView(R.layout.font_main);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        /*Font size*/
        //show font size you selected before
        ArrayList<Object> fontList = fontPresenter.getFontInfo();
        prevViewFontsize = getViewSavedBeforInFontsize((int)fontList.get(0), dialog);
        setChangingColorInFontsize(prevViewFontsize);

        //font setting
        Typeface typeface = null;
        TextView text_fonttype_americano = dialog.findViewById(R.id.text_fonttype_americano);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/americano.ttf");
        text_fonttype_americano.setTypeface(typeface);
        TextView text_fonttype_letter = dialog.findViewById(R.id.text_fonttype_letter);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/letter.ttf");
        text_fonttype_letter.setTypeface(typeface);
        TextView text_fonttype_music = dialog.findViewById(R.id.text_fonttype_music);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/music.ttf");
        text_fonttype_music.setTypeface(typeface);

        //font size event
        dialog.findViewById(R.id.outer_fontsize_1step).setOnClickListener(fontSizeClick);
        dialog.findViewById(R.id.outer_fontsize_2step).setOnClickListener(fontSizeClick);
        dialog.findViewById(R.id.outer_fontsize_3step).setOnClickListener(fontSizeClick);
        dialog.findViewById(R.id.outer_fontsize_4step).setOnClickListener(fontSizeClick);
        dialog.findViewById(R.id.outer_fontsize_5step).setOnClickListener(fontSizeClick);

        /*Font type*/
        prevViewFonttype = getViewSavedBeforInFonttype((String)fontList.get(1), dialog);
        setChangingColorInFonttype(prevViewFonttype);

        dialog.findViewById(R.id.outer_fonttype_normal).setOnClickListener(fonttypeClick);
        dialog.findViewById(R.id.outer_fonttype_americano).setOnClickListener(fonttypeClick);
        dialog.findViewById(R.id.outer_fonttype_letter).setOnClickListener(fonttypeClick);
        dialog.findViewById(R.id.outer_fonttype_music).setOnClickListener(fonttypeClick);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                prevViewFontsize = null;
            }
        });
        dialog.show();
    }

    private View.OnClickListener fontSizeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //back to 'Aa' of original color
            FrameLayout outer = (FrameLayout) prevViewFontsize;
            FrameLayout inner = (FrameLayout) outer.getChildAt(0);
            FontUtil fontUtil = new FontUtil(context);
            fontUtil.returnFontsize();
            inner.setBackgroundDrawable(fontUtil);

            TextView textView_fontsize = (TextView) inner.getChildAt(0);
            textView_fontsize.setTextColor(0xff3C3F41);

            //'Aa' color setting
            textView_fontsize = setChangingColorInFontsize(v);
            int fontSizeDp = Integer.parseInt(String.valueOf(textView_fontsize.getHint()));
            fontPresenter.updateFontSize(fontSizeDp);
            mainFragment.getTextView_content().setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSizeDp);
            //save prvious view
            prevViewFontsize = v;
        }
    };

    public View getViewSavedBeforInFontsize(int fontSizeDp, Dialog dialog) {
        View tempView = null;
        switch (fontSizeDp) {
            case 19:
                tempView = (View) dialog.findViewById(R.id.outer_fontsize_1step);
                break;
            case 22:
                tempView = (View) dialog.findViewById(R.id.outer_fontsize_2step);
                break;
            case 25:
                tempView = (View) dialog.findViewById(R.id.outer_fontsize_3step);
                break;
            case 28:
                tempView = (View) dialog.findViewById(R.id.outer_fontsize_4step);
                break;
            case 31:
                tempView = (View) dialog.findViewById(R.id.outer_fontsize_5step);
                break;
        }
        return tempView;
    }

    public TextView setChangingColorInFontsize(View view) {
        FrameLayout outer = (FrameLayout) view;
        FrameLayout inner = (FrameLayout) outer.getChildAt(0);
        FontUtil fontUtil = new FontUtil(context);
        fontUtil.changeFontsize();
        inner.setBackgroundDrawable(fontUtil);

        TextView textView_fontsize = (TextView) inner.getChildAt(0);
        textView_fontsize.setTextColor(Color.WHITE);
        return textView_fontsize;
    }

    public View getViewSavedBeforInFonttype(String fontType, Dialog dialog) {
        View tempView = null;
        if (fontType.equals("Normal")) {
            tempView = (View) dialog.findViewById(R.id.outer_fonttype_normal);
        } else if (fontType.equals("Americano")) {
            tempView = (View) dialog.findViewById(R.id.outer_fonttype_americano);
        } else if (fontType.equals("Letter")) {
            tempView = (View) dialog.findViewById(R.id.outer_fonttype_letter);
        } else if (fontType.equals("Music")) {
            tempView = (View) dialog.findViewById(R.id.outer_fonttype_music);
        }
        return tempView;
    }

    public TextView setChangingColorInFonttype(View view) {
        LinearLayout outer = (LinearLayout) view;
        FrameLayout inner = (FrameLayout) outer.getChildAt(0);
        FontUtil fontUtil = new FontUtil(context);
        fontUtil.changeFontsize();
        inner.setBackgroundDrawable(fontUtil);

        TextView textView_fonttype = (TextView) inner.getChildAt(0);
        textView_fonttype.setTextColor(Color.WHITE);
        return textView_fonttype;
    }

    private View.OnClickListener fonttypeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //back to fonttype of original color
            LinearLayout outer = (LinearLayout) prevViewFonttype;
            FrameLayout inner = (FrameLayout) outer.getChildAt(0);
            FontUtil fontUtil = new FontUtil(context);
            fontUtil.returnFontsize();
            inner.setBackgroundDrawable(fontUtil);

            TextView textView_fonttype = (TextView) inner.getChildAt(0);
            textView_fonttype.setTextColor(0xff3C3F41);

            //fonttype color setting
            textView_fonttype = setChangingColorInFonttype(v);
            fontPresenter.updateFontType(String.valueOf(textView_fonttype.getText()));
            Typeface typeface = new FontUtil(context).setFonttype(String.valueOf(textView_fonttype.getText()));
            mainFragment.getTextView_content().setTypeface(typeface);
            mainFragment.getTextView_title().setTypeface(typeface);
            //save prvious view
            prevViewFonttype = v;
        }
    };
}
