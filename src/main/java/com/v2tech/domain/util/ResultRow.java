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
@JsonPropertyOrder({ "state", "city", "name", "cStreams", "rExams", "typesOfCoursesOffered", "typeOfProgram", "website", "phoneNumber", "juniorCollegesPartnerShip", "location", "address", "summary", "branch", "zip", "courses", "targetStudents", "duration", "addedBy", "batchSize", "medium", "keyword", "searchable", "averageRating", "rateCount", "lat", "lon", "schedule", "gtype", "bbox_abc", "id" })

public class ResultRow
	{
		
		@JsonProperty("state")
		private String				state;
		@JsonProperty("city")
		private String				city;
		@JsonProperty("name")
		private String				name;
		@JsonProperty("cStreams")
		private String				cStreams;
		@JsonProperty("rExams")
		private String				rExams;
		@JsonProperty("typesOfCoursesOffered")
		private String				typesOfCoursesOffered;
		@JsonProperty("typeOfProgram")
		private String				typeOfProgram;
		@JsonProperty("website")
		private String				website;
		@JsonProperty("phoneNumber")
		private String				phoneNumber;
		@JsonProperty("juniorCollegesPartnerShip")
		private String				juniorCollegesPartnerShip;
		@JsonProperty("location")
		private String				location;
		@JsonProperty("address")
		private String				address;
		@JsonProperty("summary")
		private String				summary;
		@JsonProperty("branch")
		private String				branch;
		@JsonProperty("zip")
		private String				zip;
		@JsonProperty("courses")
		private String				courses;
		@JsonProperty("targetStudents")
		private String				targetStudents;
		@JsonProperty("duration")
		private String				duration;
		@JsonProperty("addedBy")
		private String				addedBy;
		@JsonProperty("batchSize")
		private Integer				batchSize;
		@JsonProperty("medium")
		private String				medium;
		@JsonProperty("keyword")
		private String				keyword;
		@JsonProperty("searchable")
		private String				searchable;
		@JsonProperty("averageRating")
		private Double				averageRating;
		@JsonProperty("rateCount")
		private Integer				rateCount;
		@JsonProperty("lat")
		private Double				lat;
		@JsonProperty("lon")
		private Double				lon;
		@JsonProperty("schedule")
		private String				schedule;
		@JsonProperty("gtype")
		private Integer				gtype;
		@JsonProperty("bbox_abc")
		private List<Double>		bboxAbc					= new ArrayList<Double>();
		@JsonProperty("id")
		private Integer				id;
		@JsonIgnore
		private Map<String, Object>	additionalProperties	= new HashMap<String, Object>();
		
		
		
		/**
		 * @return The state
		 */
		@JsonProperty("state")
		public String getState()
			{
				return state;
			}
			
		/**
		 * @param state
		 *            The state
		 */
		@JsonProperty("state")
		public void setState(String state)
			{
				this.state = state;
			}
			
		/**
		 * @return The city
		 */
		@JsonProperty("city")
		public String getCity()
			{
				return city;
			}
			
		/**
		 * @param city
		 *            The city
		 */
		@JsonProperty("city")
		public void setCity(String city)
			{
				this.city = city;
			}
			
		/**
		 * @return The name
		 */
		@JsonProperty("name")
		public String getName()
			{
				return name;
			}
			
		/**
		 * @param name
		 *            The name
		 */
		@JsonProperty("name")
		public void setName(String name)
			{
				this.name = name;
			}
			
		/**
		 * @return The cStreams
		 */
		@JsonProperty("cStreams")
		public String getCStreams()
			{
				return cStreams;
			}
			
		/**
		 * @param cStreams
		 *            The cStreams
		 */
		@JsonProperty("cStreams")
		public void setCStreams(String cStreams)
			{
				this.cStreams = cStreams;
			}
			
		/**
		 * @return The rExams
		 */
		@JsonProperty("rExams")
		public String getRExams()
			{
				return rExams;
			}
			
		/**
		 * @param rExams
		 *            The rExams
		 */
		@JsonProperty("rExams")
		public void setRExams(String rExams)
			{
				this.rExams = rExams;
			}
			
		/**
		 * @return The typesOfCoursesOffered
		 */
		@JsonProperty("typesOfCoursesOffered")
		public String getTypesOfCoursesOffered()
			{
				return typesOfCoursesOffered;
			}
			
		/**
		 * @param typesOfCoursesOffered
		 *            The typesOfCoursesOffered
		 */
		@JsonProperty("typesOfCoursesOffered")
		public void setTypesOfCoursesOffered(String typesOfCoursesOffered)
			{
				this.typesOfCoursesOffered = typesOfCoursesOffered;
			}
			
		/**
		 * @return The typeOfProgram
		 */
		@JsonProperty("typeOfProgram")
		public String getTypeOfProgram()
			{
				return typeOfProgram;
			}
			
		/**
		 * @param typeOfProgram
		 *            The typeOfProgram
		 */
		@JsonProperty("typeOfProgram")
		public void setTypeOfProgram(String typeOfProgram)
			{
				this.typeOfProgram = typeOfProgram;
			}
			
		/**
		 * @return The website
		 */
		@JsonProperty("website")
		public String getWebsite()
			{
				return website;
			}
			
		/**
		 * @param website
		 *            The website
		 */
		@JsonProperty("website")
		public void setWebsite(String website)
			{
				this.website = website;
			}
			
		/**
		 * @return The phoneNumber
		 */
		@JsonProperty("phoneNumber")
		public String getPhoneNumber()
			{
				return phoneNumber;
			}
			
		/**
		 * @param phoneNumber
		 *            The phoneNumber
		 */
		@JsonProperty("phoneNumber")
		public void setPhoneNumber(String phoneNumber)
			{
				this.phoneNumber = phoneNumber;
			}
			
		/**
		 * @return The juniorCollegesPartnerShip
		 */
		@JsonProperty("juniorCollegesPartnerShip")
		public String getJuniorCollegesPartnerShip()
			{
				return juniorCollegesPartnerShip;
			}
			
		/**
		 * @param juniorCollegesPartnerShip
		 *            The juniorCollegesPartnerShip
		 */
		@JsonProperty("juniorCollegesPartnerShip")
		public void setJuniorCollegesPartnerShip(String juniorCollegesPartnerShip)
			{
				this.juniorCollegesPartnerShip = juniorCollegesPartnerShip;
			}
			
		/**
		 * @return The location
		 */
		@JsonProperty("location")
		public String getLocation()
			{
				return location;
			}
			
		/**
		 * @param location
		 *            The location
		 */
		@JsonProperty("location")
		public void setLocation(String location)
			{
				this.location = location;
			}
			
		/**
		 * @return The address
		 */
		@JsonProperty("address")
		public String getAddress()
			{
				return address;
			}
			
		/**
		 * @param address
		 *            The address
		 */
		@JsonProperty("address")
		public void setAddress(String address)
			{
				this.address = address;
			}
			
		/**
		 * @return The summary
		 */
		@JsonProperty("summary")
		public String getSummary()
			{
				return summary;
			}
			
		/**
		 * @param summary
		 *            The summary
		 */
		@JsonProperty("summary")
		public void setSummary(String summary)
			{
				this.summary = summary;
			}
			
		/**
		 * @return The branch
		 */
		@JsonProperty("branch")
		public String getBranch()
			{
				return branch;
			}
			
		/**
		 * @param branch
		 *            The branch
		 */
		@JsonProperty("branch")
		public void setBranch(String branch)
			{
				this.branch = branch;
			}
			
		/**
		 * @return The zip
		 */
		@JsonProperty("zip")
		public String getZip()
			{
				return zip;
			}
			
		/**
		 * @param zip
		 *            The zip
		 */
		@JsonProperty("zip")
		public void setZip(String zip)
			{
				this.zip = zip;
			}
			
		/**
		 * @return The courses
		 */
		@JsonProperty("courses")
		public String getCourses()
			{
				return courses;
			}
			
		/**
		 * @param courses
		 *            The courses
		 */
		@JsonProperty("courses")
		public void setCourses(String courses)
			{
				this.courses = courses;
			}
			
		/**
		 * @return The targetStudents
		 */
		@JsonProperty("targetStudents")
		public String getTargetStudents()
			{
				return targetStudents;
			}
			
		/**
		 * @param targetStudents
		 *            The targetStudents
		 */
		@JsonProperty("targetStudents")
		public void setTargetStudents(String targetStudents)
			{
				this.targetStudents = targetStudents;
			}
			
		/**
		 * @return The duration
		 */
		@JsonProperty("duration")
		public String getDuration()
			{
				return duration;
			}
			
		/**
		 * @param duration
		 *            The duration
		 */
		@JsonProperty("duration")
		public void setDuration(String duration)
			{
				this.duration = duration;
			}
			
		/**
		 * @return The addedBy
		 */
		@JsonProperty("addedBy")
		public String getAddedBy()
			{
				return addedBy;
			}
			
		/**
		 * @param addedBy
		 *            The addedBy
		 */
		@JsonProperty("addedBy")
		public void setAddedBy(String addedBy)
			{
				this.addedBy = addedBy;
			}
			
		/**
		 * @return The batchSize
		 */
		@JsonProperty("batchSize")
		public Integer getBatchSize()
			{
				return batchSize;
			}
			
		/**
		 * @param batchSize
		 *            The batchSize
		 */
		@JsonProperty("batchSize")
		public void setBatchSize(Integer batchSize)
			{
				this.batchSize = batchSize;
			}
			
		/**
		 * @return The medium
		 */
		@JsonProperty("medium")
		public String getMedium()
			{
				return medium;
			}
			
		/**
		 * @param medium
		 *            The medium
		 */
		@JsonProperty("medium")
		public void setMedium(String medium)
			{
				this.medium = medium;
			}
			
		/**
		 * @return The keyword
		 */
		@JsonProperty("keyword")
		public String getKeyword()
			{
				return keyword;
			}
			
		/**
		 * @param keyword
		 *            The keyword
		 */
		@JsonProperty("keyword")
		public void setKeyword(String keyword)
			{
				this.keyword = keyword;
			}
			
		/**
		 * @return The searchable
		 */
		@JsonProperty("searchable")
		public String getSearchable()
			{
				return searchable;
			}
			
		/**
		 * @param searchable
		 *            The searchable
		 */
		@JsonProperty("searchable")
		public void setSearchable(String searchable)
			{
				this.searchable = searchable;
			}
			
		/**
		 * @return The averageRating
		 */
		@JsonProperty("averageRating")
		public Double getAverageRating()
			{
				return averageRating;
			}
			
		/**
		 * @param averageRating
		 *            The averageRating
		 */
		@JsonProperty("averageRating")
		public void setAverageRating(Double averageRating)
			{
				this.averageRating = averageRating;
			}
			
		/**
		 * @return The rateCount
		 */
		@JsonProperty("rateCount")
		public Integer getRateCount()
			{
				return rateCount;
			}
			
		/**
		 * @param rateCount
		 *            The rateCount
		 */
		@JsonProperty("rateCount")
		public void setRateCount(Integer rateCount)
			{
				this.rateCount = rateCount;
			}
			
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
		 * @return The lon
		 */
		@JsonProperty("lon")
		public Double getLon()
			{
				return lon;
			}
			
		/**
		 * @param lon
		 *            The lon
		 */
		@JsonProperty("lon")
		public void setLon(Double lon)
			{
				this.lon = lon;
			}
			
		/**
		 * @return The schedule
		 */
		@JsonProperty("schedule")
		public String getSchedule()
			{
				return schedule;
			}
			
		/**
		 * @param schedule
		 *            The schedule
		 */
		@JsonProperty("schedule")
		public void setSchedule(String schedule)
			{
				this.schedule = schedule;
			}
			
		/**
		 * @return The gtype
		 */
		@JsonProperty("gtype")
		public Integer getGtype()
			{
				return gtype;
			}
			
		/**
		 * @param gtype
		 *            The gtype
		 */
		@JsonProperty("gtype")
		public void setGtype(Integer gtype)
			{
				this.gtype = gtype;
			}
			
		/**
		 * @return The bboxAbc
		 */
		@JsonProperty("bbox_abc")
		public List<Double> getBboxAbc()
			{
				return bboxAbc;
			}
			
		/**
		 * @param bboxAbc
		 *            The bbox_abc
		 */
		@JsonProperty("bbox_abc")
		public void setBboxAbc(List<Double> bboxAbc)
			{
				this.bboxAbc = bboxAbc;
			}
			
		/**
		 * @return The id
		 */
		@JsonProperty("id")
		public Integer getId()
			{
				return id;
			}
			
		/**
		 * @param id
		 *            The id
		 */
		@JsonProperty("id")
		public void setId(Integer id)
			{
				this.id = id;
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
