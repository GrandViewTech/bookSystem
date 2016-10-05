package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class ResourceUnderReview {
	

	@GraphId
	private Long id;
	
	String resourceType = RESOURCE_TYPE.BOOK.getType();
	/**
	 * Unique Name
	 */
	String resourceName;
	
	/**
	 * Incase of books
	 */
	String resourceTitle = "";
	
	String linkURLToResource = "";
	
	Float averageRatingForResource = 0f;
	
	Integer numberOfTimesReviewed = 0;
	
	Integer numberOfTimesFavourited = 0;

	Integer numberOfTimesDiscussed = 0;
	
	String criteria="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	

	public Float getAverageRatingForResource() {
		return averageRatingForResource;
	}

	public void setAverageRatingForResource(Float averageRatingForResource) {
		this.averageRatingForResource = averageRatingForResource;
	}

	public Integer getNumberOfTimesReviewed() {
		return numberOfTimesReviewed;
	}

	public void setNumberOfTimesReviewed(Integer numberOfTimesReviewed) {
		this.numberOfTimesReviewed = numberOfTimesReviewed;
	}

	public Integer getNumberOfTimesFavourited() {
		return numberOfTimesFavourited;
	}

	public void setNumberOfTimesFavourited(Integer numberOfTimesFavourited) {
		this.numberOfTimesFavourited = numberOfTimesFavourited;
	}

	public Integer getNumberOfTimesDiscussed() {
		return numberOfTimesDiscussed;
	}

	public void setNumberOfTimesDiscussed(Integer numberOfTimesDiscussed) {
		this.numberOfTimesDiscussed = numberOfTimesDiscussed;
	}

	public String getLinkURLToResource() {
		return linkURLToResource;
	}

	public void setLinkURLToResource(String linkURLToResource) {
		this.linkURLToResource = linkURLToResource;
	}
	
	@Override
	public int hashCode(){
		return averageRatingForResource.hashCode();
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	
	
	
}
