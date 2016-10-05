package com.v2tech.template.domain;

import java.util.ArrayList;
import java.util.List;

public class Template1School {
	
	String id;
	
	String schoolName;
	
	String schoolLocation;
	
	String monthAndYear;
	
	String degree;
	
	List<String> schoolComments = new ArrayList<String>();

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<String> getSchoolComments() {
		return schoolComments;
	}

	public void setSchoolComments(List<String> schoolComments) {
		this.schoolComments = schoolComments;
	}

	public String getSchoolLocation() {
		return schoolLocation;
	}

	public void setSchoolLocation(String schoolLocation) {
		this.schoolLocation = schoolLocation;
	}

	public String getMonthAndYear() {
		return monthAndYear;
	}

	public void setMonthAndYear(String monthAndYear) {
		this.monthAndYear = monthAndYear;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
