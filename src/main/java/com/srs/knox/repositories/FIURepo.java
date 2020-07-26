package com.srs.knox.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.srs.knox.models.FIU;

@Repository
public interface FIURepo extends CrudRepository<FIU, Integer>{
	FIU findByUsername(String username);	
	FIU findByName(String name);
	FIU findById(long id);
}
