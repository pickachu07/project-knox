package com.srs.knox.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.srs.knox.models.Execution;

public interface ExecutionRepo extends CrudRepository<Execution, Integer> {
	Execution findById(UUID id);
}
