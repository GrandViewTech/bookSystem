package com.test;

import java.net.URLEncoder;

import org.junit.Test;
public class TestDoc {
String url = "intent=buy&data_readonly=data_email&data_readonly=data_amount&data_readonly=data_phone&data_readonly=data_name&data_name={Jatin Sutaria}&data_email={jatin@abc.com}&data_phone={09930070660}&data_amount={1000}";	
	@Test
	public void testCreateDocFromHtml(){
		String encoded = URLEncoder.encode(url);
		System.out.println(encoded);
	}
	
	
	private String user = "v2_technologies";
	private String pwd = "5946kfe";
	@Test
	public void testUrlEncode(){
		String authString = user + ":" + pwd;
		//String authStringEnc = java.util.Base64.getEncoder().encodeToString(authString.getBytes());
		//System.out.println(authStringEnc);
		//
	}

}
