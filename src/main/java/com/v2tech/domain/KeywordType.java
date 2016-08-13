package com.v2tech.domain;

public enum KeywordType {

BOOK_TITLE("BOOK_TITLE"), AUTHOR("AUTHOR"), EXAM("EXAM"), SUBJECT("SUBJECT"), TOPIC("TOPIC"), 
CAREERSTREAM("CAREERSTREAM"), PUBLISHER("PUBLISHER"), COACHING_CLASS_NAME("COACHING_CLASS_NAME"), 
TUTOR("TUTOR"), DIGITAL_RESOURCE("DIGITAL_RESOURCE");
	
	String type;
	
	private KeywordType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
