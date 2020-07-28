package com.srs.knox.execution.utils;

public class InvokeExecutionResponse {
	private String ver;
	private String timestamp;
	private String txnid;
	private String executionid;
	
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
	public String getExecutionid() {
		return executionid;
	}
	public void setExecutionid(String executionid) {
		this.executionid = executionid;
	}
}
