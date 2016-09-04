package com.v2tech.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Review
	{
		
		@GraphId
		private Long	id;
		private String	resourceIdentity					= "";
		private String	comment								= "";
		private String	reviewedBy							= "";
		private Double	reviewScore							= 0.0;
		@Transient
		private Integer	score;
		private Integer	visualTools							= 0;
		private Integer	solvedExamples						= 0;
		private String	resourceReviewedType				= RESOURCE_TYPE.BOOK.getType();
		private Integer	solutionToPracticeProblems			= 0;
		private Integer	effectivenessAndEaseOfCommunication	= 0;
		private Integer	easyOfUse							= 0;
		private Integer	studyMaterial						= 0;
		private Integer	faculty								= 0;
		private Integer	personalization						= 0;
		private Integer	infrastructure						= 0;
		private Integer	interactivity						= 0;
		private String	location;
		
		String topics = "";
		
		public Long getId()
			{
				return id;
			}
			
		public void setId(Long id)
			{
				this.id = id;
			}
			
		public String getResourceIdentity()
			{
				return resourceIdentity;
			}
			
		public void setResourceIdentity(String resourceIdentity)
			{
				this.resourceIdentity = resourceIdentity;
			}
			
		public String getComment()
			{
				return comment;
			}
			
		public void setComment(String comment)
			{
				this.comment = comment;
			}
			
		public String getReviewedBy()
			{
				return reviewedBy;
			}
			
		public void setReviewedBy(String reviewedBy)
			{
				this.reviewedBy = reviewedBy;
			}
			
		public Double getReviewScore()
			{
				return reviewScore;
			}
			
		public void setReviewScore(Double reviewScore)
			{
				this.reviewScore = reviewScore;
			}
			
		public Integer getVisualTools()
			{
				return visualTools;
			}
			
		public void setVisualTools(Integer visualTools)
			{
				this.visualTools = visualTools;
			}
			
		public Integer getSolvedExamples()
			{
				return solvedExamples;
			}
			
		public void setSolvedExamples(Integer solvedExamples)
			{
				this.solvedExamples = solvedExamples;
			}
			
		public String getResourceReviewedType()
			{
				return resourceReviewedType;
			}
			
		public void setResourceReviewedType(String resourceReviewedType)
			{
				this.resourceReviewedType = resourceReviewedType;
			}
			
		public Integer getSolutionToPracticeProblems()
			{
				return solutionToPracticeProblems;
			}
			
		public void setSolutionToPracticeProblems(Integer solutionToPracticeProblems)
			{
				this.solutionToPracticeProblems = solutionToPracticeProblems;
			}
			
		public Integer getEffectivenessAndEaseOfCommunication()
			{
				return effectivenessAndEaseOfCommunication;
			}
			
		public void setEffectivenessAndEaseOfCommunication(Integer effectivenessAndEaseOfCommunication)
			{
				this.effectivenessAndEaseOfCommunication = effectivenessAndEaseOfCommunication;
			}
			
		public Integer getEasyOfUse()
			{
				return easyOfUse;
			}
			
		public void setEasyOfUse(Integer easyOfUse)
			{
				this.easyOfUse = easyOfUse;
			}
			
		public Integer getStudyMaterial()
			{
				return studyMaterial;
			}
			
		public void setStudyMaterial(Integer studyMaterial)
			{
				this.studyMaterial = studyMaterial;
			}
			
		public Integer getFaculty()
			{
				return faculty;
			}
			
		public void setFaculty(Integer faculty)
			{
				this.faculty = faculty;
			}
			
		public Integer getPersonalization()
			{
				return personalization;
			}
			
		public void setPersonalization(Integer personalization)
			{
				this.personalization = personalization;
			}
			
		public Integer getInfrastructure()
			{
				return infrastructure;
			}
			
		public void setInfrastructure(Integer infrastructure)
			{
				this.infrastructure = infrastructure;
			}
			
		public String getLocation()
			{
				return location;
			}
			
		public void setLocation(String location)
			{
				this.location = location;
			}
			
		public Integer getInteractivity()
			{
				return interactivity;
			}
			
		public void setInteractivity(Integer interactivity)
			{
				this.interactivity = interactivity;
			}
			
		public Integer getScore()
			{
				Integer score = reviewScore.intValue();
				if (score > 5)
					{
						score = 5;
					}
				if (score < 1)
					{
						score = 1;
					}
				return score;
			}
			
		@Override
		public String toString()
			{
				return "Review [id=" + id + ", resourceIdentity=" + resourceIdentity + ", comment=" + comment + ", reviewedBy=" + reviewedBy + ", reviewScore=" + reviewScore + ", score=" + score + ", visualTools=" + visualTools + ", solvedExamples=" + solvedExamples + ", resourceReviewedType=" + resourceReviewedType + ", solutionToPracticeProblems=" + solutionToPracticeProblems + ", effectivenessAndEaseOfCommunication=" + effectivenessAndEaseOfCommunication + ", easyOfUse=" + easyOfUse
				        + ", studyMaterial=" + studyMaterial + ", faculty=" + faculty + ", personalization=" + personalization + ", infrastructure=" + infrastructure + ", interactivity=" + interactivity + ", location=" + location + "]";
			}

		public String getTopics() {
			return topics;
		}

		public void setTopics(String topics) {
			this.topics = topics;
		}

		public void setScore(Integer score) {
			this.score = score;
		}
			
		
	}
