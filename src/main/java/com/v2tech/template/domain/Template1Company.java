package com.v2tech.template.domain;

import java.util.ArrayList;
import java.util.List;

public class Template1Company {
	
	String id = "";
	
	String companyName;
	
	String companyLocation;
	
	String role;
	
	String duration;
	
	String headerComment = "";
	
	List<String> roleComments = new ArrayList<String>();

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLocation() {
		return companyLocation;
	}

	public void setCompanyLocation(String companyLocation) {
		this.companyLocation = companyLocation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<String> getRoleComments() {
		return roleComments;
	}

	public void setRoleComments(List<String> roleComments) {
		this.roleComments = roleComments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeaderComment() {
		return headerComment;
	}

	public void setHeaderComment(String headerComment) {
		this.headerComment = headerComment;
	}
	
	
	

}
