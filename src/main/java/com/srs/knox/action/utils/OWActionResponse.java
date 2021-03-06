package com.srs.knox.action.utils;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OWActionResponse {
	private String namespace;
	private String name;
	private String version;
	private boolean publish;
	Exec ExecObject;
	ArrayList <Object> annotations = new ArrayList <Object> ();
	ArrayList <Object> parameters = new ArrayList <Object> ();
	Limits LimitsObject;
	private String updated;

	public String getNamespace() {
		return namespace;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public boolean getPublish() {
		return publish;
	}

	public Exec getExec() {
		return ExecObject;
	}

	public Limits getLimits() {
		return LimitsObject;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public void setExec(Exec execObject) {
		this.ExecObject = execObject;
	}

	public void setLimits(Limits limitsObject) {
		this.LimitsObject = limitsObject;
	}
	
	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
}
