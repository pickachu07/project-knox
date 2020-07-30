package com.srs.knox.events;

import org.springframework.context.ApplicationEvent;

import com.srs.knox.models.Execution;
import com.srs.knox.models.Status;

public class DataReadyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private Status status;
	
	public DataReadyEvent(Execution executionRecord) {
		super(executionRecord);
		this.status = Status.DATA_READY;
	}
	
	public Status getStatus() {
		return this.status;
	}
}
