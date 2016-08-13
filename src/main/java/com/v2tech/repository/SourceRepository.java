package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Source;
@Repository
public interface SourceRepository extends GraphRepository<Source>{
	
	@Query("MATCH (src:Source) WHERE src.source = {0} return src;")
	public Set<Source> findSourceByName(String source);

}