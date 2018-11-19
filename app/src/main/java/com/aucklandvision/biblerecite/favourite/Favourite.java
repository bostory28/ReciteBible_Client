package com.aucklandvision.biblerecite.favourite;

public class Favourite {
    private int _id;
    private String verse_title_kr;
    private String verse_title_en;
    private int yr;
    private int mon;
    private int favorite;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getVerse_title_kr() {
        return verse_title_kr;
    }

    public void setVerse_title_kr(String verse_title_kr) {
        this.verse_title_kr = verse_title_kr;
    }

    public String getVerse_title_en() {
        return verse_title_en;
    }

    public void setVerse_title_en(String verse_title_en) {
        this.verse_title_en = verse_title_en;
    }

    public int getYr() {
        return yr;
    }

    public void setYr(int yr) {
        this.yr = yr;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "_id=" + _id +
                ", verse_title_kr='" + verse_title_kr + '\'' +
                ", verse_title_en='" + verse_title_en + '\'' +
                ", yr=" + yr +
                ", mon=" + mon +
                ", favourite=" + favorite +
                '}';
    }
}
