package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Keyword;

@Repository
public interface KeywordRepository extends GraphRepository<Keyword>{
	
	@Query("MATCH (keyword:Keyword) WHERE keyword.text = {0} RETURN keyword LIMIT 1;")
	public Keyword findKeywordByText(String text);
	
	@Query("MATCH (keyword:Keyword) where keyword.text =~ {0} RETURN keyword ORDER BY keyword.text;")
	public Set<Keyword> getKeywords(String keyword);
	
	@Query("MATCH (keyword:Keyword) where keyword.text =~ {0} RETURN keyword.text ORDER BY keyword.text;")
	public Set<String> getKeywordTexts(String keyword);

}
