package com.v2tech.domain.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "layer", "node" })
public class AddNode
	{
		
		@JsonProperty("layer")
		private String				layer;
		@JsonProperty("node")
		private String				node;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The layer
		 */
		@JsonProperty("layer")
		public String getLayer()
			{
				return layer;
			}
			
		/**
		 * @param layer
		 *            The layer
		 */
		@JsonProperty("layer")
		public void setLayer(String layer)
			{
				this.layer = layer;
			}
			
		/**
		 * @return The node
		 */
		@JsonProperty("node")
		public String getNode()
			{
				return node;
			}
			
		/**
		 * @param node
		 *            The node
		 */
		@JsonProperty("node")
		public void setNode(String node)
			{
				this.node = node;
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