package com.srs.knox.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTION")
public class Action {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private long id;
	
	@Column(name = "fiuid")
    private long fiuid;

	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "main")
	private String main;
	
	@Column(name = "runtime")
	private String runtime;
	
	@Column(name = "timeout")
	private int timeout;
	
	@Column(name = "memory")
	private int memory;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "deleted")
	private boolean deleted;

	public Action(long fiuid, String name, String main, String code, String runtime, int timeout, int memory, String version) {
		super();
		this.fiuid = fiuid;
		this.name = name;
		this.code = code;
		this.main = main;
		this.runtime = runtime;
		this.timeout = timeout;
		this.memory = memory;
		this.active = true;
		this.deleted = false;
		this.version = version;
	}
	
	public Action() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getFiuid() {
		return fiuid;
	}

	public void setFiuid(long fiuid) {
		this.fiuid = fiuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public long getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
