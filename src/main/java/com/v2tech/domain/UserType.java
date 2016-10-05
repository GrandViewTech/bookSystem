package com.v2tech.domain;

public enum UserType {
	ADMIN("ADMIN"), STUDENT("STUDENT") ,SYSTEM("SYSTEM");
	
	String type;
	
	private UserType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
