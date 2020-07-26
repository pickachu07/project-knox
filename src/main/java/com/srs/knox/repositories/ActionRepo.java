package com.srs.knox.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.srs.knox.models.Action;

@Repository
public interface ActionRepo extends CrudRepository<Action, Integer>{
	Action findById(long id);
	Action findByName(String name);
	List<Action> findByFiuid(long fiuid);
}