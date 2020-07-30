package com.srs.knox.execution.utils;

public class Notifier {
	private String type;
	private String id;
	
	public Notifier() {
		this.type = "VDPR";
		this.id = "VDPR-14";
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setId(String id) {
		this.id = id;
	}
}
