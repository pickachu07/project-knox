package com.srs.knox.execution.utils;

import java.time.LocalDateTime;
import java.util.UUID;

public class ORNotificationRequest {
	private String ver;
	private String timestamp;
	private String txnid;
	private Notifier notifier;
	private ExecutionStatusNotification notification;
	
	public ORNotificationRequest(Notifier notifier, ExecutionStatusNotification notification) {
		this.ver = "1.0";
		this.timestamp = LocalDateTime.now().toString();
		this.txnid = UUID.randomUUID().toString();
		this.notifier = notifier;
		this.notification = notification;
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

	public Notifier getNotifier() {
		return notifier;
	}

	public void setNotifier(Notifier notifier) {
		this.notifier = notifier;
	}

	public ExecutionStatusNotification getNotification() {
		return notification;
	}

	public void setNotification(ExecutionStatusNotification notification) {
		this.notification = notification;
	}
}
