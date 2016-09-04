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
@JsonPropertyOrder({ "results", "errors" })
public class Neo4jSearchResult
	{
		
		@JsonProperty("results")
		private List<SearchResult>	results					= new ArrayList<SearchResult>();
		@JsonProperty("errors")
		private List<Object>		errors					= new ArrayList<Object>();
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		public List<ResultRow> getResultRow()
			{
				List<ResultRow> resultRows = new ArrayList<ResultRow>();
				for (SearchResult searchResult : results)
					{
						List<Data> datas = searchResult.getData();
						for (Data data : datas)
							{
								List<ResultRow> rows = data.getRow();
								resultRows.addAll(rows);
							}
					}
				return resultRows;
			}
			
		/**
		 * @return The results
		 */
		@JsonProperty("results")
		public List<SearchResult> getResults()
			{
				return results;
			}
			
		/**
		 * @param results
		 *            The results
		 */
		@JsonProperty("results")
		public void setResults(List<SearchResult> results)
			{
				this.results = results;
			}
			
		/**
		 * @return The errors
		 */
		@JsonProperty("errors")
		public List<Object> getErrors()
			{
				return errors;
			}
			
		/**
		 * @param errors
		 *            The errors
		 */
		@JsonProperty("errors")
		public void setErrors(List<Object> errors)
			{
				this.errors = errors;
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
@JsonPropertyOrder({ "columns", "data" })
class SearchResult
	{
		
		@JsonProperty("columns")
		private List<String>		columns					= new ArrayList<String>();
		@JsonProperty("data")
		private List<Data>			data					= new ArrayList<Data>();
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The columns
		 */
		@JsonProperty("columns")
		public List<String> getColumns()
			{
				return columns;
			}
			
		/**
		 * @param columns
		 *            The columns
		 */
		@JsonProperty("columns")
		public void setColumns(List<String> columns)
			{
				this.columns = columns;
			}
			
		/**
		 * @return The data
		 */
		@JsonProperty("data")
		public List<Data> getData()
			{
				return data;
			}
			
		/**
		 * @param data
		 *            The data
		 */
		@JsonProperty("data")
		public void setData(List<Data> data)
			{
				this.data = data;
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
@JsonPropertyOrder({ "row" })
class Data
	{
		
		@JsonProperty("row")
		private List<ResultRow>		row						= new ArrayList<ResultRow>();
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The row
		 */
		@JsonProperty("row")
		public List<ResultRow> getRow()
			{
				return row;
			}
			
		/**
		 * @param row
		 *            The row
		 */
		@JsonProperty("row")
		public void setRow(List<ResultRow> row)
			{
				this.row = row;
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
