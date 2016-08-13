package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.College;

public interface CollegeRepository extends GraphRepository<College>{
	
	@Query("MATCH (college:College) WHERE college.name = {0} return college LIMIT 1;")
	public College getCollegeByName(String college);
	
//	@Query("MATCH (college:College) WHERE college.course =~ {0} AND college.stream =~ {1}  return college LIMIT {2};")
//	public Set<College> getCollegesByCourseAndStream(String course, String stream, Integer limit);
	

}
