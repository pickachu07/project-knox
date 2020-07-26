package com.srs.knox.models;

public class FIUUserBaseModel {
	private String username;
	private String password;
	private String fiuId;

	public String getFiuId() {
		return fiuId;
	}

	public void setFiuId(String fiuId) {
		this.fiuId = fiuId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}