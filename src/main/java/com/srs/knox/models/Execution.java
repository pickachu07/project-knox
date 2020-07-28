package com.srs.knox.models;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.srs.knox.utils.HashMapConverter;
import com.srs.knox.utils.UUIDAttributeConverter;

@Entity
@Table(name="EXECUTION")
public class Execution {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
	@Convert(converter = UUIDAttributeConverter.class)
    private UUID id;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "fiuid")
	private long fiuid;
	
	@Column(name = "actionid")
	private long actionid;
	
	@Column(name = "fisessionid")
	private String fisessionid;

	@Column(name = "activationid")
	private String activationId;
	
	@Convert(converter = HashMapConverter.class)
	private Map<String, Object> output;
	
	@Column(name = "ttl")
	private long ttl;
	
	@Column(name = "lastupdated")
	private LocalDateTime lastupdated;
	
	@Column(name = "archived")
	private boolean archived;
	
	public Execution() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Execution(Status status, long fiuid, long actionid, String fisessionid, LocalDateTime lastupdated) {
		super();
		this.status = status;
		this.fiuid = fiuid;
		this.actionid = actionid;
		this.fisessionid = fisessionid;
		this.lastupdated = lastupdated;
		this.archived = false;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

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
	
	public String getFisessionid() {
		return fisessionid;
	}

	public void setFisessionid(String fisessionid) {
		this.fisessionid = fisessionid;
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

	public long getTtl() {
		return ttl;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	public LocalDateTime getLastUpdated() {
		return lastupdated;
	}

	public void setLastUpdated(LocalDateTime lastupdated) {
		this.lastupdated = lastupdated;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
}
