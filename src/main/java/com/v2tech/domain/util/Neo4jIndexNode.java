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
public class Neo4jIndexNode
	{
		@JsonProperty("statements")
		private List<Statement>		statements				= new ArrayList<Statement>();
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		public Neo4jIndexNode(String className)
			{
				statements.add(new Statement("match (n:" + className + "y) set n.id=id(n)"));
			}
			
		/**
		 * @return The statements
		 */
		@JsonProperty("statements")
		public List<Statement> getStatements()
			{
				return statements;
			}
			
		/**
		 * @param statements
		 *            The statements
		 */
		@JsonProperty("statements")
		public void setStatements(List<Statement> statements)
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
	
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "statement" })
class Statement
	{
		
		@JsonProperty("statement")
		private String				statement;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		public Statement(String statement)
			{
				this.statement = statement;
			}
			
		/**
		 * @return The statement
		 */
		@JsonProperty("statement")
		public String getStatement()
			{
				return statement;
			}
			
		/**
		 * @param statement
		 *            The statement
		 */
		@JsonProperty("statement")
		public void setStatement(String statement)
			{
				this.statement = statement;
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
