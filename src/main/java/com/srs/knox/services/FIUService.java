package com.srs.knox.services;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.srs.knox.models.FIU;
import com.srs.knox.models.FIUUserBaseModel;
import com.srs.knox.repositories.FIURepo;

@Service
public class FIUService implements UserDetailsService{

	Logger logger = LoggerFactory.getLogger(FIUService.class);
	
	@Autowired
	FIURepo fiuRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		FIU fiu = fiuRepo.findByUsername(username);
		if(fiu == null) {
			throw new UsernameNotFoundException("FIU not found with username: " + username);
		}
		
		return new org.springframework.security.core.userdetails.User(fiu.getUsername(), fiu.getPassword(),
				new ArrayList<>());
	}
	
	
	public FIU save(FIUUserBaseModel fiu,String apiKey) {
		FIU newFIU = new FIU();
		newFIU.setUsername(fiu.getUsername());
		newFIU.setPassword(bcryptEncoder.encode(fiu.getPassword()));
		newFIU.setApiKey(apiKey);
		return fiuRepo.save(newFIU);
	}

	public boolean isNameUnique(String name) {
		FIU fiu = fiuRepo.findByName(name);
		if(fiu == null) {
			return true;
		}
		return false;
	}
}



