package com.v2tech.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class CoachingClass
	{
		
		@GraphId
		private Long	id;
		
		String			name;
		
		/**
		 * Comma separated
		 */
		String			cStreams					= "";
		
		/**
		 * Comma sepaarted
		 */
		String			rExams						= "";
		
		String			city						= "";
		
		String			yearFounded					= "";
		
		/**
		 * Comma sepaarted
		 */
		String			typesOfCoursesOffered		= "";
		
		String			typeOfProgram				= "";
		
		String			website						= "";
		
		String			phoneNumber					= "";
		
		Integer			averageBatchSize;
		
		/**
		 * Comma sepaarted
		 */
		String			juniorCollegesPartnerShip	= "";
		
		String			location					= "";
		
		String			address						= "";
		
		String			schedule					= "";
		
		byte[]			image;
		
		String			summary						= "";
		
		String			branch						= "";
		
		String			zip							= "";
		
		String			courses						= "";
		
		String			targetStudents				= "";
		
		String			duration					= "";
		
		String			description					= "";
		
		String			addedBy						= "";
		
		int				batchSize;
		
		String			courseMaterial				= "";
		
		String			medium						= "";
		
		String			photoDetail					= "";
		
		String			questions					= "";
		
		String			keyword						= "";
		
		@Transient
		String			bSize						= "";
		
		String			searchable					= "yes";
		
		private Double	averageRating				= 1.0;
		
		private Integer	rateCount					= 1;
		
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
			
		public String getcStreams()
			{
				if (cStreams == null)
					{
						cStreams = "";
					}
				return cStreams;
			}
			
		public void setcStreams(String cStreams)
			{
				this.cStreams = cStreams;
			}
			
		public String getrExams()
			{
				if (rExams == null)
					{
						rExams = "";
					}
				return rExams;
			}
			
		public void setrExams(String rExams)
			{
				this.rExams = rExams;
			}
			
		public String getCity()
			{
				return city;
			}
			
		public void setCity(String city)
			{
				this.city = city;
			}
			
		public String getYearFounded()
			{
				return yearFounded;
			}
			
		public void setYearFounded(String yearFounded)
			{
				this.yearFounded = yearFounded;
			}
			
		public String getTypesOfCoursesOffered()
			{
				if (typesOfCoursesOffered == null)
					{
						typesOfCoursesOffered = "";
					}
				return typesOfCoursesOffered;
			}
			
		public void setTypesOfCoursesOffered(String typesOfCoursesOffered)
			{
				this.typesOfCoursesOffered = typesOfCoursesOffered;
			}
			
		public String getTypeOfProgram()
			{
				return typeOfProgram;
			}
			
		public void setTypeOfProgram(String typeOfProgram)
			{
				this.typeOfProgram = typeOfProgram;
			}
			
		public String getWebsite()
			{
				return website;
			}
			
		public void setWebsite(String website)
			{
				this.website = website;
			}
			
		public String getPhoneNumber()
			{
				return phoneNumber;
			}
			
		public void setPhoneNumber(String phoneNumber)
			{
				this.phoneNumber = phoneNumber;
			}
			
		public Integer getAverageBatchSize()
			{
				return averageBatchSize;
			}
			
		public void setAverageBatchSize(Integer averageBatchSize)
			{
				this.averageBatchSize = averageBatchSize;
			}
			
		public String getJuniorCollegesPartnerShip()
			{
				return juniorCollegesPartnerShip;
			}
			
		public void setJuniorCollegesPartnerShip(String juniorCollegesPartnerShip)
			{
				this.juniorCollegesPartnerShip = juniorCollegesPartnerShip;
			}
			
		public String getLocation()
			{
				return location;
			}
			
		public void setLocation(String location)
			{
				this.location = location;
			}
			
		public String getAddress()
			{
				return address;
			}
			
		public void setAddress(String address)
			{
				this.address = address;
			}
			
		public String getSchedule()
			{
				return schedule;
			}
			
		public void setSchedule(String schedule)
			{
				this.schedule = schedule;
			}
			
		public byte[] getImage()
			{
				return image;
			}
			
		public void setImage(byte[] image)
			{
				this.image = image;
			}
			
		public String getSummary()
			{
				return summary;
			}
			
		public void setSummary(String summary)
			{
				this.summary = summary;
			}
			
		public String getBranch()
			{
				return branch;
			}
			
		public void setBranch(String branch)
			{
				this.branch = branch;
			}
			
		public String getZip()
			{
				return zip;
			}
			
		public void setZip(String zip)
			{
				this.zip = zip;
			}
			
		public String getCourses()
			{
				if (courses == null)
					{
						courses = "";
					}
				return courses;
			}
			
		public void setCourses(String courses)
			{
				this.courses = courses;
			}
			
		public String getTargetStudents()
			{
				if (targetStudents == null)
					{
						targetStudents = "";
					}
				return targetStudents;
			}
			
		public void setTargetStudents(String targetStudents)
			{
				this.targetStudents = targetStudents;
			}
			
		public String getDuration()
			{
				return duration;
			}
			
		public void setDuration(String duration)
			{
				this.duration = duration;
			}
			
		public String getDescription()
			{
				return description;
			}
			
		public void setDescription(String description)
			{
				this.description = description;
			}
			
		public String getAddedBy()
			{
				return addedBy;
			}
			
		public void setAddedBy(String addedBy)
			{
				this.addedBy = addedBy;
			}
			
		public int getBatchSize()
			{
				return batchSize;
			}
			
		public void setBatchSize(int batchSize)
			{
				this.batchSize = batchSize;
			}
			
		public String getCourseMaterial()
			{
				return courseMaterial;
			}
			
		public void setCourseMaterial(String courseMaterial)
			{
				this.courseMaterial = courseMaterial;
			}
			
		public String getMedium()
			{
				return medium;
			}
			
		public void setMedium(String medium)
			{
				this.medium = medium;
			}
			
		public String getPhotoDetail()
			{
				return photoDetail;
			}
			
		public void setPhotoDetail(String photoDetail)
			{
				this.photoDetail = photoDetail;
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
			
		public String getbSize()
			{
				return bSize;
			}
			
		public void setbSize(String bSize)
			{
				if (bSize == null)
					{
						try
							{
								int size = Integer.parseInt(bSize);
								setBatchSize(size);
							}
						catch (NumberFormatException e)
							{
								
							}
					}
				this.bSize = bSize;
			}
			
		public String getSearchable()
			{
				return searchable;
			}
			
		public void setSearchable(String searchable)
			{
				this.searchable = searchable;
			}
			
		public Double getAverageRating()
			{
				return averageRating;
			}
			
		public void setAverageRating(Double averageRating)
			{
				averageRating = averageRating % 5;
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
