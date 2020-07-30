package com.srs.knox.models;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String username;
	private final Long fiuId;
	private final String apikey;
	private final String name;

	public JwtResponse(String jwttoken,String username,Long fiuId,String apikey,String name) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.fiuId = fiuId;
		this.apikey = apikey;
		this.name = name;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public String getUsername() {
		return this.username;
	}
	public Long getFiuId() {
		return this.fiuId;
	}
	
	public String getApikey() {
		return this.apikey;
	}
	
	public String getName() {
		return this.name;
	}
}