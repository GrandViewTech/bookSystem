package com.v2tech.domain.util;

import java.io.Serializable;
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
@JsonPropertyOrder({ "results", "status" })
public class GoogleApiResponse implements Serializable
	{
		private static final long	serialVersionUID		= 1L;
		@JsonProperty("results")
		private List<Result>		results					= new ArrayList<Result>();
		@JsonProperty("status")
		private String				status;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The results
		 */
		@JsonProperty("results")
		public List<Result> getResults()
			{
				return results;
			}
			
		/**
		 * @param results
		 *            The results
		 */
		@JsonProperty("results")
		public void setResults(List<Result> results)
			{
				this.results = results;
			}
			
		/**
		 * @return The status
		 */
		@JsonProperty("status")
		public String getStatus()
			{
				return status;
			}
			
		/**
		 * @param status
		 *            The status
		 */
		@JsonProperty("status")
		public void setStatus(String status)
			{
				this.status = status;
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
			
		public Double getLongitude()
			{
				Double longitude = null;
				if (results != null && results.size() > 0)
					{
						longitude = getResults().get(0).getGeometry().getLocation().getLng();
						
					}
				return longitude;
			}
			
		public Double getlatitude()
			{
				Double latitude = null;
				if (results != null && results.size() > 0)
					{
						latitude = getResults().get(0).getGeometry().getLocation().getLat();
					}
				return latitude;
			}
	}
	
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "address_components", "formatted_address", "geometry", "place_id", "types" })
class Result implements Serializable
	{
		private static final long		serialVersionUID		= 1L;
		@JsonProperty("address_components")
		private List<AddressComponent>	addressComponents		= new ArrayList<AddressComponent>();
		@JsonProperty("formatted_address")
		private String					formattedAddress;
		@JsonProperty("geometry")
		private Geometry				geometry;
		@JsonProperty("place_id")
		private String					placeId;
		@JsonProperty("types")
		private List<String>			types					= new ArrayList<String>();
		@JsonIgnore
		private Map<String, Object>		additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The addressComponents
		 */
		@JsonProperty("address_components")
		public List<AddressComponent> getAddressComponents()
			{
				return addressComponents;
			}
			
		/**
		 * @param addressComponents
		 *            The address_components
		 */
		@JsonProperty("address_components")
		public void setAddressComponents(List<AddressComponent> addressComponents)
			{
				this.addressComponents = addressComponents;
			}
			
		/**
		 * @return The formattedAddress
		 */
		@JsonProperty("formatted_address")
		public String getFormattedAddress()
			{
				return formattedAddress;
			}
			
		/**
		 * @param formattedAddress
		 *            The formatted_address
		 */
		@JsonProperty("formatted_address")
		public void setFormattedAddress(String formattedAddress)
			{
				this.formattedAddress = formattedAddress;
			}
			
		/**
		 * @return The geometry
		 */
		@JsonProperty("geometry")
		public Geometry getGeometry()
			{
				return geometry;
			}
			
		/**
		 * @param geometry
		 *            The geometry
		 */
		@JsonProperty("geometry")
		public void setGeometry(Geometry geometry)
			{
				this.geometry = geometry;
			}
			
		/**
		 * @return The placeId
		 */
		@JsonProperty("place_id")
		public String getPlaceId()
			{
				return placeId;
			}
			
		/**
		 * @param placeId
		 *            The place_id
		 */
		@JsonProperty("place_id")
		public void setPlaceId(String placeId)
			{
				this.placeId = placeId;
			}
			
		/**
		 * @return The types
		 */
		@JsonProperty("types")
		public List<String> getTypes()
			{
				return types;
			}
			
		/**
		 * @param types
		 *            The types
		 */
		@JsonProperty("types")
		public void setTypes(List<String> types)
			{
				this.types = types;
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
	
class AddressComponent implements Serializable
	{
		private static final long	serialVersionUID	= 1L;
		private String				long_name;
		private String				short_name;
		private List<String>		types				= new ArrayList<String>();
		private String				place_id;
		
		public String getLong_name()
			{
				return long_name;
			}
			
		public void setLong_name(String long_name)
			{
				this.long_name = long_name;
			}
			
		public String getShort_name()
			{
				return short_name;
			}
			
		public void setShort_name(String short_name)
			{
				this.short_name = short_name;
			}
			
		public List<String> getTypes()
			{
				return types;
			}
			
		public void setTypes(List<String> types)
			{
				this.types = types;
			}
			
		public String getPlace_id()
			{
				return place_id;
			}
			
		public void setPlace_id(String place_id)
			{
				this.place_id = place_id;
			}
			
	}
	
@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({ "location", "location_type", "viewport" })
class Geometry implements Serializable
	{
		private static final long	serialVersionUID		= 1L;
		@JsonProperty("location")
		private Location			location;
		@JsonProperty("location_type")
		private String				locationType;
		@JsonProperty("viewport")
		private Viewport			viewport;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The location
		 */
		@JsonProperty("location")
		public Location getLocation()
			{
				return location;
			}
			
		/**
		 * @param location
		 *            The location
		 */
		@JsonProperty("location")
		public void setLocation(Location location)
			{
				this.location = location;
			}
			
		/**
		 * @return The locationType
		 */
		@JsonProperty("location_type")
		public String getLocationType()
			{
				return locationType;
			}
			
		/**
		 * @param locationType
		 *            The location_type
		 */
		@JsonProperty("location_type")
		public void setLocationType(String locationType)
			{
				this.locationType = locationType;
			}
			
		/**
		 * @return The viewport
		 */
		@JsonProperty("viewport")
		public Viewport getViewport()
			{
				return viewport;
			}
			
		/**
		 * @param viewport
		 *            The viewport
		 */
		@JsonProperty("viewport")
		public void setViewport(Viewport viewport)
			{
				this.viewport = viewport;
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
@JsonPropertyOrder({ "lat", "lng" })
class Location implements Serializable
	{
		private static final long	serialVersionUID		= 1L;
		@JsonProperty("lat")
		private Double				lat;
		@JsonProperty("lng")
		private Double				lng;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The lat
		 */
		@JsonProperty("lat")
		public Double getLat()
			{
				return lat;
			}
			
		/**
		 * @param lat
		 *            The lat
		 */
		@JsonProperty("lat")
		public void setLat(Double lat)
			{
				this.lat = lat;
			}
			
		/**
		 * @return The lng
		 */
		@JsonProperty("lng")
		public Double getLng()
			{
				return lng;
			}
			
		/**
		 * @param lng
		 *            The lng
		 */
		@JsonProperty("lng")
		public void setLng(Double lng)
			{
				this.lng = lng;
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
@JsonPropertyOrder({ "northeast", "southwest" })
class Viewport implements Serializable
	{
		private static final long	serialVersionUID		= 1L;
		
		@JsonProperty("northeast")
		private Northeast			northeast;
		@JsonProperty("southwest")
		private Southwest			southwest;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The northeast
		 */
		@JsonProperty("northeast")
		public Northeast getNortheast()
			{
				return northeast;
			}
			
		/**
		 * @param northeast
		 *            The northeast
		 */
		@JsonProperty("northeast")
		public void setNortheast(Northeast northeast)
			{
				this.northeast = northeast;
			}
			
		/**
		 * @return The southwest
		 */
		@JsonProperty("southwest")
		public Southwest getSouthwest()
			{
				return southwest;
			}
			
		/**
		 * @param southwest
		 *            The southwest
		 */
		@JsonProperty("southwest")
		public void setSouthwest(Southwest southwest)
			{
				this.southwest = southwest;
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
@JsonPropertyOrder({ "lat", "lng" })
class Northeast implements Serializable
	{
		private static final long	serialVersionUID		= 1L;
		@JsonProperty("lat")
		private Double				lat;
		@JsonProperty("lng")
		private Double				lng;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The lat
		 */
		@JsonProperty("lat")
		public Double getLat()
			{
				return lat;
			}
			
		/**
		 * @param lat
		 *            The lat
		 */
		@JsonProperty("lat")
		public void setLat(Double lat)
			{
				this.lat = lat;
			}
			
		/**
		 * @return The lng
		 */
		@JsonProperty("lng")
		public Double getLng()
			{
				return lng;
			}
			
		/**
		 * @param lng
		 *            The lng
		 */
		@JsonProperty("lng")
		public void setLng(Double lng)
			{
				this.lng = lng;
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
@JsonPropertyOrder({ "lat", "lng" })
class Southwest
	{
		
		@JsonProperty("lat")
		private Double				lat;
		@JsonProperty("lng")
		private Double				lng;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		/**
		 * @return The lat
		 */
		@JsonProperty("lat")
		public Double getLat()
			{
				return lat;
			}
			
		/**
		 * @param lat
		 *            The lat
		 */
		@JsonProperty("lat")
		public void setLat(Double lat)
			{
				this.lat = lat;
			}
			
		/**
		 * @return The lng
		 */
		@JsonProperty("lng")
		public Double getLng()
			{
				return lng;
			}
			
		/**
		 * @param lng
		 *            The lng
		 */
		@JsonProperty("lng")
		public void setLng(Double lng)
			{
				this.lng = lng;
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
