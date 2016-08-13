package com.v2tech.domain;

public enum RESOURCE_TYPE {
	BOOK("BOOK"), COACHING_CLASS("COACHING_CLASS"), DIGITAL_RESOURCE("DIGITAL_RESOURCE"), COLLEGE("COLLEGE");
	
	String type;
	
	private RESOURCE_TYPE(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	

}
