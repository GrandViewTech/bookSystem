package com.v2tech.domain.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "statement", "parameters" })
public class Neo4jSearchStatement
	{
		
		@JsonProperty("statement")
		private String				statement;
		@JsonProperty("parameters")
		private Parameters			parameters;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
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
			
		/**
		 * @return The parameters
		 */
		@JsonProperty("parameters")
		public Parameters getParameters()
			{
				return parameters;
			}
			
		/**
		 * @param parameters
		 *            The parameters
		 */
		@JsonProperty("parameters")
		public void setParameters(Parameters parameters)
			{
				this.parameters = parameters;
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
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
		
})
class Parameters
	{
		
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();
		
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
