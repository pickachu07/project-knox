package com.srs.knox.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.srs.knox.models.Execution;

public interface ExecutionRepo extends CrudRepository<Execution, Integer> {
	Execution findById(UUID id);
	List<Execution> findByFiuid(long Fiuid);
	List<Execution> findByFiuidAndArchived(long Fiuid, boolean Archived);
	List<Execution> findByActionid(long Actionid);
	List<Execution> findByActionidAndArchived(long Actionid, boolean Archived);
}
