package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Exam;
@Repository
public interface ExamRepository extends GraphRepository<Exam>{
	
	@Query("MATCH (ex:Exam) WHERE ex.exam = {0} return ex;")
	public Set<Exam> findExamByName(String exam);

}
