package com.srs.knox.execution.utils;

public class DHPublicKey {
	private String expiry;
	private String Parameters;
	private String KeyValue;

	public String getExpiry() {
		return expiry;
	}

	public String getParameters() {
		return Parameters;
	}

	public String getKeyValue() {
		return KeyValue;
	} 

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public void setParameters(String Parameters) {
		this.Parameters = Parameters;
	}

	public void setKeyValue(String KeyValue) {
		this.KeyValue = KeyValue;
	}
}
