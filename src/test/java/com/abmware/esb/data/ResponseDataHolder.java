package com.abmware.esb.data;
/**
 * Optional
 * @author jsutaria
 *
 */
public class ResponseDataHolder {

	String requestId;
	
	Object responseFromRemoteApp;
	
	boolean responseGot;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getResponseFromRemoteApp() {
		return responseFromRemoteApp;
	}

	public void setResponseFromRemoteApp(Object responseFromRemoteApp) {
		this.responseFromRemoteApp = responseFromRemoteApp;
	}

	public boolean isResponseGot() {
		return responseGot;
	}

	public void setResponseGot(boolean responseGot) {
		this.responseGot = responseGot;
	}
	
	
}
