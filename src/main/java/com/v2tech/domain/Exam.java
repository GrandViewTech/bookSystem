package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;


@NodeEntity
public class Exam {
	
	@GraphId
	private Long id;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exam(){
		
	}
	
	public Exam(String exm){
		this.exam = exm;
	}
	
	@Fetch
	private String exam;
	

	//private CareerStream stream;

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}


	@Override
	public int hashCode(){
		return this.exam.hashCode();
	}
	
	@Override
	public boolean equals(Object object){
		if(! (object instanceof Exam)){
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
