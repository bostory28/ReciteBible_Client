package vo;

import java.io.Serializable;

public class UpdateVO implements Serializable {
	private int updatedate_sq;
	private String lastdate;
	private int update_type;
	private int verses_sq;
	private String verse_title_kr;
	private String verse_title_en;
	private int verse_section;
	private String verse_kr;
	private String verse_en;
	private int yr;
	private int mon;
	
	
	public int getUpdatedate_sq() {
		return updatedate_sq;
	}

	public void setUpdatedate_sq(int updatedate_sq) {
		this.updatedate_sq = updatedate_sq;
	}

	public String getLastdate() {
		return lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public int getUpdate_type() {
		return update_type;
	}

	public void setUpdate_type(int update_type) {
		this.update_type = update_type;
	}

	public int getVerses_sq() {
		return verses_sq;
	}

	public void setVerses_sq(int verses_sq) {
		this.verses_sq = verses_sq;
	}
	
	

	public int getYr() {
		return yr;
	}

	public void setYr(int yr) {
		this.yr = yr;
	}

	public String getVerse_title_en() {
		return verse_title_en;
	}

	public void setVerse_title_en(String verse_title_en) {
		this.verse_title_en = verse_title_en;
	}
	
	

	public String getVerse_title_kr() {
		return verse_title_kr;
	}

	public void setVerse_title_kr(String verse_title_kr) {
		this.verse_title_kr = verse_title_kr;
	}

	public int getVerse_section() {
		return verse_section;
	}

	public void setVerse_section(int verse_section) {
		this.verse_section = verse_section;
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

	public int getMon() {
		return mon;
	}

	public void setMon(int mon) {
		this.mon = mon;
	}

	@Override
	public String toString() {
		return "UpdateVO [updatedate_sq=" + updatedate_sq + ", lastdate=" + lastdate + ", update_type=" + update_type
				+ ", verses_sq=" + verses_sq + ", verse_title_kr=" + verse_title_kr + ", verse_title_en="
				+ verse_title_en + ", verse_section=" + verse_section + ", verse_kr=" + verse_kr + ", verse_en="
				+ verse_en + ", yr=" + yr + ", mon=" + mon + "]";
	}
}
