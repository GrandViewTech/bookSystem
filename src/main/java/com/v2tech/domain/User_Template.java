package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class User_Template {
	
	@GraphId
	private Long id;	
	
	String user;
	
	String socialMediaType;
	
	String temlplateType = "CV";//other option is COVER
	
	private String dateLastUpdated = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSocialMediaType() {
		return socialMediaType;
	}

	public void setSocialMediaType(String socialMediaType) {
		this.socialMediaType = socialMediaType;
	}

	public String getTemlplateType() {
		return temlplateType;
	}

	public void setTemlplateType(String temlplateType) {
		this.temlplateType = temlplateType;
	}

	public String getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(String dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	
	
	

}
