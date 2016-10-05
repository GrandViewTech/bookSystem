package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Subject;

@Repository
public interface SubjectRepository extends GraphRepository<Subject>{
	
	@Query("MATCH (sub:Subject) WHERE sub.name = {0} return sub;")
	public Set<Subject> findSubjectByName(String subject);

}
