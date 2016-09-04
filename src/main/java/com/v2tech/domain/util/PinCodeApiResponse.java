package com.v2tech.domain.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "query", "pincode" })
public class PinCodeApiResponse
	{
		
		@JsonProperty("query")
		private String				query;
		@JsonProperty("pincode")
		private String				pincode;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The query
		 */
		@JsonProperty("query")
		public String getQuery()
			{
				return query;
			}
			
		/**
		 * @param query
		 *            The query
		 */
		@JsonProperty("query")
		public void setQuery(String query)
			{
				this.query = query;
			}
			
		/**
		 * @return The pincode
		 */
		@JsonProperty("pincode")
		public String getPincode()
			{
				return pincode;
			}
	}
