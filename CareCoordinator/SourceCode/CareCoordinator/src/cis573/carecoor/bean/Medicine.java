package cis573.carecoor.bean;

import java.io.Serializable;

public class Medicine implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TAG = "Medicine";
	
	private int id;
	private String name;
	private String detailedName;
	private String instructions;
	private String capacity;
	private int dose;
	private int times;
	private int interval;
	private int duration;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetailedName() {
		return detailedName;
	}

	public void setDetailedName(String detailedName) {
		this.detailedName = detailedName;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public int getDose() {
		return dose;
	}

	public void setDose(int dose) {
		this.dose = dose;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
