package cis573.carecoor.bean;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5611808715709161372L;

	public static final String TAG = "Appointment";
	
	private Date date;
	private String detail;
	private int remind;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getRemind() {
		return remind;
	}
	public void setRemind(int remind) {
		this.remind = remind;
	}
}
