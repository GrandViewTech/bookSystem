package com.v2tech.domain.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "statements" })
public class Neo4jSearchStatements
	{
		
		@JsonProperty("statements")
		private List<Neo4jSearchStatement>	statements				= new ArrayList<Neo4jSearchStatement>();
		@JsonIgnore
		private Map<String, Object>			additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The statements
		 */
		@JsonProperty("statements")
		public List<Neo4jSearchStatement> getStatements()
			{
				return statements;
			}
			
		/**
		 * @param statements
		 *            The statements
		 */
		@JsonProperty("statements")
		public void setStatements(List<Neo4jSearchStatement> statements)
			{
				this.statements = statements;
			}
			
		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties()
			{
				return this.additionalProperties;
			}
			
		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value)
			{
				this.additionalProperties.put(name, value);
			}
			
	}