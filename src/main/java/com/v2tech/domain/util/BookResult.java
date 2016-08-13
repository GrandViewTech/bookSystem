package com.v2tech.domain.util;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

import com.v2tech.domain.Book;

@QueryResult
public interface BookResult {
	@ResultColumn("rated") int getRated();
	   @ResultColumn("book") Book getBook();
}
