package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.Class1;

public interface Class1Repository extends GraphRepository<Class1>{

	@Query("MATCH (user:Class1) WHERE user.test = {0} return user;")
	public Set<Class1> findUserByUserName(String test);
}
