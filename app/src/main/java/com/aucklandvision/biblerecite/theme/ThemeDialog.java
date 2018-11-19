package com.aucklandvision.biblerecite.theme;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.utils.ThemeColor;

import java.util.ArrayList;

public class ThemeDialog {
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private ThemePresenter themePresenter;

    private Dialog dialog;
    private ThemeApdater themeApdater;

    public ThemeDialog(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
    }

    public void showThemeDialog() {
        themePresenter = new ThemePresenter();
        dialog = new Dialog(mainActivity);
        int width = (int) (mainActivity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (mainActivity.getResources().getDisplayMetrics().heightPixels * 0.80);
        //v1.0.1
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //v1.0.1
        dialog.setContentView(R.layout.theme_main);
        dialog.getWindow().setLayout(width, height);

        TextView text_theme_title = dialog.findViewById(R.id.text_theme_title);
        text_theme_title.setBackgroundColor(ThemeColor.ARGB);
        text_theme_title.setText(" Theme");

        ListView theme_listview = dialog.findViewById(R.id.theme_listview);
        themeApdater = new ThemeApdater(mainActivity);

        ThemeColor themeColor = new ThemeColor(mainActivity);
        ArrayList<Theme> themeList = themeColor.getThemeList();
        themeApdater.setItems(themeList);
        theme_listview.setAdapter(themeApdater);

        theme_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theme theme = (Theme) themeApdater.getItem(position);
                int argb_content = theme.getArgb_content();
                String colorName = theme.getColorName();
                String strArgb_content = "0x"+String.valueOf(Integer.toHexString(argb_content));
                themePresenter.updateTheme(colorName, strArgb_content);
                mainActivity.finish();
                mainActivity.startActivities(new Intent[]{new Intent(mainActivity, mainActivity.getClass())});
                dialog.cancel();
            }
        });
        ImageView image_theme_xbutton = dialog.findViewById(R.id.image_theme_xbutton);
        image_theme_xbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
