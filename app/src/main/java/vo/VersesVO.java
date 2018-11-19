package vo;

import java.io.Serializable;

public class VersesVO implements Serializable {
	private int verses_sq;
	private String verse_title_kr;
	private String verse_title_en;
	private int verse_section;
	private String verse_kr;
	private String verse_en;
	private int version;
	private int yr;
	private int mon;
	
	public int getVerses_sq() {
		return verses_sq;
	}
	public void setVerses_sq(int verses_sq) {
		this.verses_sq = verses_sq;
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
	
	public int getVerse_section() {
		return verse_section;
	}
	public void setVerse_section(int verse_section) {
		this.verse_section = verse_section;
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
	@Override
	public String toString() {
		return "VersesVO [verses_sq=" + verses_sq + ", verse_title_kr=" + verse_title_kr + ", verse_title_en="
				+ verse_title_en + ", verse_section=" + verse_section + ", verse_kr=" + verse_kr + ", verse_en="
				+ verse_en + ", version=" + version + ", yr=" + yr + ", mon=" + mon + "]";
	}
}
