package com.aucklandvision.biblerecite.main;

public class Verses {
	private int _id;
	private int _id_server;
	private String verse_title_kr;
	private String verse_title_en;
	private int verse_section;
	private String verse_kr;
	private String verse_en;
	private int version;
	private int yr;
	private int mon;
	private int quiz_level;
	private int check_remember_kr;
	private int check_remember_en;
	private int countnum;
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

	public String getVerse_kr() {
		return verse_kr;
	}

	public void setVerse_kr(String verse_kr) {
		this.verse_kr = verse_kr;
	}

	public String getVerse_en() {
		return verse_en;
	}

	public void setVerse_en(String verse_en) {
		this.verse_en = verse_en;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public int getQuiz_level() {
		return quiz_level;
	}

	public void setQuiz_level(int quiz_level) {
		this.quiz_level = quiz_level;
	}

	public int getCheck_remember_kr() {
		return check_remember_kr;
	}

	public void setCheck_remember_kr(int check_remember_kr) {
		this.check_remember_kr = check_remember_kr;
	}

	public int getCheck_remember_en() {
		return check_remember_en;
	}

	public void setCheck_remember_en(int check_remember_en) {
		this.check_remember_en = check_remember_en;
	}

	public int getCountnum() {
		return countnum;
	}

	public void setCountnum(int countnum) {
		this.countnum = countnum;
	}

	public int getVerse_section() {
		return verse_section;
	}

	public void setVerse_section(int verse_section) {
		this.verse_section = verse_section;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int get_id_server() {
		return _id_server;
	}

	public void set_id_server(int _id_server) {
		this._id_server = _id_server;
	}

	@Override
	public String toString() {
		return "Verses{" +
				"_id=" + _id +
				", _id_server=" + _id_server +
				", verse_title_kr='" + verse_title_kr + '\'' +
				", verse_title_en='" + verse_title_en + '\'' +
				", verse_section=" + verse_section +
				", verse_kr='" + verse_kr + '\'' +
				", verse_en='" + verse_en + '\'' +
				", version=" + version +
				", yr=" + yr +
				", mon=" + mon +
				", quiz_level=" + quiz_level +
				", check_remember_kr=" + check_remember_kr +
				", check_remember_en=" + check_remember_en +
				", countnum=" + countnum +
				", favorite=" + favorite +
				'}';
	}
}
