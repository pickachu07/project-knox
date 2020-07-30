package com.srs.knox.action.utils;

public class UpdateActionRequest {
	private String fiuid;
	private String actionid;
	private String main;
	private String runtime;
	private String timeout;
	private String memory;
	private String code;
	
	public String getFiuid() {
		return fiuid;
	}
	
	public void setFiuid(String fiuid) {
		this.fiuid = fiuid;
	}
	
	public String getActionid() {
		return actionid;
	}
	
	public void setActionid(String actionid) {
		this.actionid = actionid;
	}
	
	public String getMain() {
		return main;
	}
	
	public void setMain(String main) {
		this.main = main;
	}
	
	public String getRuntime() {
		return runtime;
	}
	
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	
	public String getTimeout() {
		return timeout;
	}
	
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	public String getMemory() {
		return memory;
	}
	
	public void setMemory(String memory) {
		this.memory = memory;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
