package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Source {
	@Fetch
	private String source;
	
	public Source(){
		
	}
	
	public Source(String src){
		this.source = src;
	}
	
	@GraphId
	private Long id;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Override
	public int hashCode(){
		return this.source.hashCode();
	}
	
	@Override
	public boolean equals(Object object){
		if(! (object instanceof Source)){
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
