package com.v2tech.domain;

/**
 * @author brij
 *
 */
public enum SourceType {
	NONE("NONE"),
	AMAZON("AMAZON"),
	FLIPCART("FLIPCART");
	
	private String value;
	
	private SourceType(String typeVal){
		this.value = typeVal;
	}
	
	public String getValue(){
		return value;
	}
}
