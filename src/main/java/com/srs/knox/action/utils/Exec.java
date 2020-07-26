package com.srs.knox.action.utils;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Exec {
	private String kind;
	private String code;
	private String image;
	private String main;
	private boolean binary;
	ArrayList <Object> components = new ArrayList <Object> ();

	public String getKind() {
		return kind;
	}

	public String getCode() {
		return code;
	}

	public String getImage() {
		return image;
	}

	public String getMain() {
		return main;
	}

	public boolean getBinary() {
		return binary;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setMain(String main) {
		this.main = main;
	}	

	public void setBinary(boolean binary) {
		this.binary = binary;
	}
}
