package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class CountryStateCity
	{
		@GraphId
		private Long	id;
		@Indexed
		private String	referenceId;
		@Indexed
		private String	country;
		@Indexed
		private String	state;
		@Indexed
		private String	city;
		@Indexed
		private String	region;
		@Indexed
		private Integer	zipcode;
		@Indexed
		private String	countryCode;
		
		public Long getId()
			{
				return id;
			}
			
		public void setId(Long id)
			{
				this.id = id;
			}
			
		public String getCountry()
			{
				return country;
			}
			
		public void setCountry(String country)
			{
				this.country = country;
			}
			
		public String getState()
			{
				return state;
			}
			
		public void setState(String state)
			{
				this.state = state;
			}
			
		public String getCity()
			{
				return city;
			}
			
		public void setCity(String city)
			{
				this.city = city;
			}
			
		public Integer getZipcode()
			{
				return zipcode;
			}
			
		public void setZipcode(Integer zipcode)
			{
				this.zipcode = zipcode;
			}
			
		public String getCountryCode()
			{
				return countryCode;
			}
			
		public void setCountryCode(String countryCode)
			{
				this.countryCode = countryCode;
			}
			
		public String getRegion()
			{
				return region;
			}
			
		public void setRegion(String region)
			{
				this.region = region;
			}
			
		public String getReferenceId()
			{
				return referenceId;
			}
			
		public void setReferenceId(String referenceId)
			{
				this.referenceId = referenceId;
			}
			
	}
