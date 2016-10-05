package com.v2tech.domain;

/**
 * @author brij
 *
 */
public enum BookType {
	
	FICTION("FICTION"),
	TRAVEL("TRAVEL"),
	COMICS("COMICS"),
	DRAMA("DRAMA");
	
	private String value;
	
	private BookType(String typeVal){
		this.value=typeVal;
	}
	public String getValue(){
		return value;
	}
}
