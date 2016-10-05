package com.v2tech.domain;

/**
 * @author brij
 *
 */
public enum ExamType {
	
	YES("YES"),
	NO("NO");
	
	private String value;
	
	private ExamType(String typeVal){
		this.value = typeVal;
	}
	
	public String getValue(){
		return value;
	}
}
