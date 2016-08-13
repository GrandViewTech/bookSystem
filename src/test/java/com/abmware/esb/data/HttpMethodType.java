package com.abmware.esb.data;

public enum HttpMethodType {
	GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE");
	String method;
	
	private HttpMethodType(String method){
		this.method = method;
	}
	
	public String value(){
		return this.method;
	}

}
