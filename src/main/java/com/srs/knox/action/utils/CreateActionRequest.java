package com.srs.knox.action.utils;

public class CreateActionRequest {
	
	private String fiuid;
	private String actionname;
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
	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public String getMain() {
		return main;
	}
	public void setMainclass(String main) {
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
