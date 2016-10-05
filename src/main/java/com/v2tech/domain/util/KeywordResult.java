package com.v2tech.domain.util;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface KeywordResult {
	@ResultColumn("searchedCount") int getSearchedCount();
	   @ResultColumn("searched") String getSearchedTerm();
}
