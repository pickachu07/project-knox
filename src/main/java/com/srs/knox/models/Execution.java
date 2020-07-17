package com.srs.knox.models;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.srs.knox.utils.HashMapConverter;


@Entity
@Table(name="EXECUTION")
public class Execution {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id", referencedColumnName = "id")
	private Action action;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fiu_id", referencedColumnName = "id")
	private FIU fiu;
	
	@Column(name = "activation_id")
	private String activationId;
	
	@Convert(converter = HashMapConverter.class)
	private Map<String, Object> output;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public Execution(Action action, FIU fiu, Status status, LocalDateTime timestamp) {
		super();
		this.action = action;
		this.fiu = fiu;
		this.status = status;
		this.timestamp = timestamp;
	}

	public Execution() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public FIU getFiu() {
		return fiu;
	}

	public void setFiu(FIU fiu) {
		this.fiu = fiu;
	}

	public String getActivationId() {
		return activationId;
	}

	public void setActivationId(String activationId) {
		this.activationId = activationId;
	}

	public Map<String, Object> getOutput() {
		return output;
	}

	public void setOutput(Map<String, Object> output) {
		this.output = output;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}
	
	
}
