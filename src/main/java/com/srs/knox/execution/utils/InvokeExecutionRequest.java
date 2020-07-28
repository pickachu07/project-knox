package com.srs.knox.execution.utils;

public class InvokeExecutionRequest {
	private String ver;
	private String timestamp;
	private String txnid;
	private String sessionId;
	private String fiuid;
	private String actionid;
	KeyMaterial KeyMaterialObject;

	public String getVer() {
		return ver;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getTxnid() {
		return txnid;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getFiuid() {
		return fiuid;
	}

	public String getActionid() {
		return actionid;
	}

	public KeyMaterial getKeyMaterial() {
		return KeyMaterialObject;
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

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setFiuid(String fiuid) {
		this.fiuid = fiuid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	public void setKeyMaterial(KeyMaterial KeyMaterialObject) {
		this.KeyMaterialObject = KeyMaterialObject;
	}
}
