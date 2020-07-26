package com.srs.knox.action.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Limits {
	private long timeout;
	private long memory;
	private long logs;
	private long concurrency;
	
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public long getMemory() {
		return memory;
	}
	public void setMemory(long memory) {
		this.memory = memory;
	}
	public long getLogs() {
		return logs;
	}
	public void setLogs(long logs) {
		this.logs = logs;
	}
	public long getConcurrency() {
		return concurrency;
	}
	public void setConcurrency(long concurrency) {
		this.concurrency = concurrency;
	}
	
}
