package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.Class2;

public interface Class2Repository extends GraphRepository<Class2>{
	
	@Query("MATCH (user:Class2) WHERE user.test = {0} return user;")
	public Set<Class2> findUserByUserName(String test);

}
