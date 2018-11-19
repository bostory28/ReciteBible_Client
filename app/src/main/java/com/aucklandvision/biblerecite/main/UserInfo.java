package com.aucklandvision.biblerecite.main;

public class UserInfo {

    private int curYr = 0;           //current year
    private int language = 0;        //language
    private int curPage = 0;         //page number in total
    private int fontsize = 0;
    private String fonttype = "";
    private String themeName = "";
    private String argb = "";
    private String _lastdate_server;

    public int getCurYr() {
        return curYr;
    }

    public void setCurYr(int curYr) {
        this.curYr = curYr;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public String getFonttype() {
        return fonttype;
    }

    public void setFonttype(String fonttype) {
        this.fonttype = fonttype;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getArgb() {
        return argb;
    }

    public void setArgb(String argb) {
        this.argb = argb;
    }

    public String get_lastdate_server() {
        return _lastdate_server;
    }

    public void set_lastdate_server(String _lastdate_server) {
        this._lastdate_server = _lastdate_server;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "curYr=" + curYr +
                ", language=" + language +
                ", curPage=" + curPage +
                ", fontsize=" + fontsize +
                ", fonttype='" + fonttype + '\'' +
                ", themeName='" + themeName + '\'' +
                ", argb='" + argb + '\'' +
                ", _lastdate_server='" + _lastdate_server + '\'' +
                '}';
    }
}
