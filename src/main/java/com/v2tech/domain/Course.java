package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Course {

	@GraphId
	private Long id;
	
	String courseType= CourseType.MANAGEMENT.toString();
	
	String courseName;
	
	public Course(){
		
	}
	
	public Course(String name, String type){
		this.courseName = name;
		this.courseType = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Override
	public int hashCode(){
		return this.courseName.hashCode();
	}
}
