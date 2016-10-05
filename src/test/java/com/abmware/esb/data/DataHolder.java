package com.abmware.esb.data;

public class DataHolder {
	
	String orgId;
	
	String endPointUrl;
	
	/**
	 * 1 of GET/POST/PUT/DELETE/Trace
	 */
	String endPointMethod = HttpMethodType.GET.method;
	
	Object dataToPost;
	
	boolean responseExpected = false;
	
	String contentType = "application/json";

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEndPointUrl() {
		return endPointUrl;
	}

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public String getEndPointMethod() {
		return endPointMethod;
	}

	public void setEndPointMethod(String endPointMethod) {
		this.endPointMethod = endPointMethod;
	}

	public Object getDataToPost() {
		return dataToPost;
	}

	public void setDataToPost(Object dataToPost) {
		this.dataToPost = dataToPost;
	}

	public boolean isResponseExpected() {
		return responseExpected;
	}

	public void setResponseExpected(boolean responseExpected) {
		this.responseExpected = responseExpected;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	

}
