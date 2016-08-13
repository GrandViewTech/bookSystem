package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.DigitalTool;

public interface DigitalToolRepository extends GraphRepository<DigitalTool>
	{
		
		@Query("MATCH (dt:DigitalTool) WHERE dt.name =~ {0} return dt;")
		public Set<DigitalTool> findDigitalToolByName(String name);
		
		@Query("MATCH (dt:DigitalTool) WHERE dt.keyword =~ {0} return dt LIMIT {1};")
		Set<DigitalTool> searchDigitalToolByGenericKeyword(String keyword, Integer limit);
	}
