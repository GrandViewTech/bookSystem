package com.v2tech.domain;

public enum SocialMediaType {
	NONE("NONE"), FACEBOOK("FACEBOOK"), TWITTER("TWITTER"), GOOGLE("GOOGLE"), LINKEDLN("LINKEDLN"), INSTAGRAM("INSTAGRAM");;
	
	String type;
	private SocialMediaType(String type){
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
}
