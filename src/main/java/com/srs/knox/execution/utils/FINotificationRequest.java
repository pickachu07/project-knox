package com.srs.knox.execution.utils;

public class FINotificationRequest {
	private String ver;
	private String timestamp;
	private String txnid;
	private String sessionid;
	private String executionid;
	private Notifier NotifierObject;

	public String getVer() {
		return ver;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getTxnid() {
		return txnid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getExecutionid() {
		return executionid;
	}

	public Notifier getNotifier() {
		return NotifierObject;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public void setExecutionid(String executionid) {
		this.executionid = executionid;
	}

	public void setNotifier(Notifier NotifierObject) {
		this.NotifierObject = NotifierObject;
	}
}
