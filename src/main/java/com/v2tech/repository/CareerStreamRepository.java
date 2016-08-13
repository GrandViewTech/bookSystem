package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.CareerStream;
@Repository
public interface CareerStreamRepository extends GraphRepository<CareerStream>{
	
	@Query("MATCH (cs:CareerStream) WHERE cs.stream = {0} return cs;")
	public Set<CareerStream> findCareerStreamByName(String stream);

}
