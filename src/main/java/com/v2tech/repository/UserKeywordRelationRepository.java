package com.v2tech.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.UserKeywordRelation;

public interface UserKeywordRelationRepository extends GraphRepository<UserKeywordRelation>{
	
	@Query("MATCH (a:User)-[search:HAVE_SEARCHED]->(b:Keyword) WHERE a.user =~ {0} AND b.text =~{1} RETURN search LIMIT 1")
	public UserKeywordRelation findUserKeywordRelation(String user, String keyword);
	
	@Query("MATCH (a)-[r:HAVE_SEARCHED]->(b)  where a.user =~ {0} RETURN b.text order by r.count desc LIMIT 1")
	public String findTopRatedKeywordsForUser(String user);

}
