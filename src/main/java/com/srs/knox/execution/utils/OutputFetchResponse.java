package com.srs.knox.execution.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class OutputFetchResponse {
	private String ver;
	private String timestamp;
	private String txnid;
	private String sessionid;
	HashMap<String, Object> output;
	
	public OutputFetchResponse(String sessionid, HashMap<String, Object> output) {
		super();
		this.ver = "1.0";
		this.timestamp = LocalDateTime.now().toString();
		this.txnid = UUID.randomUUID().toString();
		this.sessionid = sessionid;
		this.output = output;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public HashMap<String, Object> getOutput() {
		return output;
	}

	public void setOutput(HashMap<String, Object> output) {
		this.output = output;
	}
}
