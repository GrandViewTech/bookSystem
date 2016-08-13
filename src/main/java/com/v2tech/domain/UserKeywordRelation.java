package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="HAVE_SEARCHED")  
public class UserKeywordRelation {
	 @GraphId  
	   Long nodeId;  

	   @Fetch	
	   @StartNode  
	   User user;  

	   @Fetch
	   @EndNode  
	   Keyword keyword;  
	   
	  @Fetch
	   Long count = 0l;
	  
	  String role;

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public Long getCount() {
		if(count == null){
			count = 1l;
		}
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	   
	   
}
