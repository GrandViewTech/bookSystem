package com.v2tech.domain;

public enum CourseType {
MANAGEMENT("MANAGEMENT"), MEDICAL("MEDICAL"), LAW("LAW"), ARCHITECTURE("ARCHITECTURE"), ENGINEERING("ENGINEERING"), ARTS("ARTS"), SCIENCE("SCIENCE"),
COMMERCE("COMMERCE"), PHARMACY("PHARMACY"), COMPUTER("COMPUTER"), APPLICATION("APPLICATION"), MASSMEDIA("MASS MEDIA"), OTHERS("OTHERS");	
	String course;
	private CourseType(String course){
		this.course = course;
	}
	
	public String toString(){
		return this.course;
	}

}
