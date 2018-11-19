package com.aucklandvision.biblerecite.theme;

public class Theme {
    private String colorName;
    private int argb_content;
    private int argb_system;

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getArgb_content() {
        return argb_content;
    }

    public void setArgb_content(int argb_content) {
        this.argb_content = argb_content;
    }

    public int getArgb_system() {
        return argb_system;
    }

    public void setArgb_system(int argb_system) {
        this.argb_system = argb_system;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "colorName='" + colorName + '\'' +
                ", argb_content=" + argb_content +
                ", argb_system=" + argb_system +
                '}';
    }
}
