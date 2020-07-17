package com.srs.knox.repositories;

import org.springframework.data.repository.CrudRepository;

import com.srs.knox.models.Execution;

public interface ExecutionRepo extends CrudRepository<Execution, Integer>{

}
