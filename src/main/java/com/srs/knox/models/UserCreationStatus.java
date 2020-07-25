package com.srs.knox.models;

public class UserCreationStatus {
	
	String message;
	int exitCode;
	public UserCreationStatus(String message, int exitCode) {
		super();
		this.message = message;
		this.exitCode = exitCode;
	}
	
	public UserCreationStatus() {
		super();
		this.exitCode = -1;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getExitCode() {
		return exitCode;
	}
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	
}
