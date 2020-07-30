package com.srs.knox.execution.utils;

public class ExecutionStatusNotification {
	private String sessionid;
	private String executionid;
	private String executionstatus;

	public ExecutionStatusNotification(String sessionid, String executionid, String executionstatus) {
		super();
		this.sessionid = sessionid;
		this.executionid = executionid;
		this.executionstatus = executionstatus;
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
	
	public void setExecutionid(String executionid) {
		this.executionid = executionid;
	}
	
	public String getExecutionstatus() {
		return executionstatus;
	}
	
	public void setExecutionstatus(String executionstatus) {
		this.executionstatus = executionstatus;
	}
}
