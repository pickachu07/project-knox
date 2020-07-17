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
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "main_class")
	private String mainClass;
	
	@Column(name = "timeout")
	private int timeout;
	
	@Column(name = "memory")
	private int memory;

	public Action(String name, String mainClass, int timeout, int memory) {
		super();
		this.name = name;
		this.mainClass = mainClass;
		this.timeout = timeout;
		this.memory = memory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
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

	public Long getId() {
		return id;
	}

}
