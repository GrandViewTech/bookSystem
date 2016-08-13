package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class DigitalTool
	{
		
		@GraphId
		private Long	id;
		
		String			name			= "";
		
		String			careerStreams	= "";
		
		String			exams			= "";
		
		String			yearFounded		= "";
		
		String			whatDoesItDo	= "";
		
		String			yesNo			= "";
		
		String			freeServices	= "";
		
		String			webSite			= "";
		
		String			app				= "";
		
		String			totalUsers		= "";
		
		String			sources			= "";
		
		String			questions		= "";
		
		String			keyword			= "";
		
		String			searchable		= "yes";
		
		String			features		= "";
		
		String			otherComments	= "";
		
		private Double	averageRating	= 1.0;
		
		private Integer	rateCount		= 1;
		
		public Long getId()
			{
				return id;
			}
			
		public void setId(Long id)
			{
				this.id = id;
			}
			
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getCareerStreams()
			{
				return careerStreams;
			}
			
		public void setCareerStreams(String careerStreams)
			{
				this.careerStreams = careerStreams;
			}
			
		public String getExams()
			{
				return exams;
			}
			
		public void setExams(String exams)
			{
				this.exams = exams;
			}
			
		public String getYearFounded()
			{
				return yearFounded;
			}
			
		public void setYearFounded(String yearFounded)
			{
				this.yearFounded = yearFounded;
			}
			
		public String getWhatDoesItDo()
			{
				return whatDoesItDo;
			}
			
		public void setWhatDoesItDo(String whatDoesItDo)
			{
				this.whatDoesItDo = whatDoesItDo;
			}
			
		public String getYesNo()
			{
				return yesNo;
			}
			
		public void setYesNo(String yesNo)
			{
				this.yesNo = yesNo;
			}
			
		public String getFreeServices()
			{
				return freeServices;
			}
			
		public void setFreeServices(String freeServices)
			{
				this.freeServices = freeServices;
			}
			
		public String getWebSite()
			{
				return webSite;
			}
			
		public void setWebSite(String webSite)
			{
				this.webSite = webSite;
			}
			
		public String getApp()
			{
				return app;
			}
			
		public void setApp(String app)
			{
				this.app = app;
			}
			
		public String getTotalUsers()
			{
				return totalUsers;
			}
			
		public void setTotalUsers(String totalUsers)
			{
				this.totalUsers = totalUsers;
			}
			
		public String getSources()
			{
				return sources;
			}
			
		public void setSources(String sources)
			{
				this.sources = sources;
			}
			
		public String getQuestions()
			{
				return questions;
			}
			
		public void setQuestions(String questions)
			{
				this.questions = questions;
			}
			
		public String getKeyword()
			{
				return keyword;
			}
			
		public void setKeyword(String keyword)
			{
				this.keyword = keyword;
			}
			
		public String getSearchable()
			{
				return searchable;
			}
			
		public void setSearchable(String searchable)
			{
				this.searchable = searchable;
			}
			
		public String getFeatures()
			{
				return features;
			}
			
		public void setFeatures(String features)
			{
				this.features = features;
			}
			
		public String getOtherComments()
			{
				return otherComments;
			}
			
		public void setOtherComments(String otherComments)
			{
				this.otherComments = otherComments;
			}
			
		public Double getAverageRating()
			{
				return averageRating;
			}
			
		public void setAverageRating(Double averageRating)
			{
				averageRating=averageRating%5;
				this.averageRating = averageRating;
			}
			
		public Integer getRateCount()
			{
				return rateCount;
			}
			
		public void setRateCount(Integer rateCount)
			{
				this.rateCount = rateCount;
			}
			
	}
