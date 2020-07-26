package com.srs.knox.action.utils;

public class CreateActionResponse {
	private long fiuid;
	private long actionid;
	
	public long getFiuid() {
		return fiuid;
	}
	
	public void setFiuid(long fiuid) {
		this.fiuid = fiuid;
	}
	
	public long getActionid() {
		return actionid;
	}
	
	public void setActionid(long actionid) {
		this.actionid = actionid;
	}

	public CreateActionResponse(long fiuid, long actionid) {
		this.fiuid = fiuid;
		this.actionid = actionid;
	}
}
