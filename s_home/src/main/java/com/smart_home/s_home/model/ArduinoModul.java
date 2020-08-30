package com.smart_home.s_home.model;


public class ArduinoModul {
	
	String serial;
	String ora;
	String data;
	String durata;
	boolean executat;
	
	
	
	public ArduinoModul(String serial, String ora, String data, String durata, boolean executat) {
		super();
		this.serial = serial;
		this.ora = ora;
		this.data = data;
		this.durata = durata;
		this.executat = executat;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata = durata;
	}
	public boolean isExecutat() {
		return executat;
	}
	public void setExecuted(boolean executat) {
		this.executat = executat;
	}

}
