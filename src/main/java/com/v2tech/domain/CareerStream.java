package com.v2tech.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;



@NodeEntity
public class CareerStream {
	@GraphId
	private Long id;
	public CareerStream(){
		
	}
	
	public CareerStream(String strm){
		this.stream = strm;
	}
	
	 @Fetch
	private String stream;
	
	
	private Set<Exam> exams = new HashSet<Exam>();
	
	
	private Set<Subject> subjects = new HashSet<Subject>();
	 @Fetch
	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	

	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode(){
		return this.stream.hashCode();
	}
	
	@Override
	public boolean equals(Object object){
		if(! (object instanceof CareerStream)){
			 return false; 
		}
		
		if(object == null){
			return false;
		}
		
		if(this.hashCode() == object.hashCode()){
			return true;
		}
		
		return false;
		
	}

}
