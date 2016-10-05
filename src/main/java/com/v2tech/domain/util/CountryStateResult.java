package com.v2tech.domain.util;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface CountryStateResult
	{
		@ResultColumn("city")
		String getCity();
		
		@ResultColumn("region")
		String getRegion();
	}
