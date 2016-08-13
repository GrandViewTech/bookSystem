package com.v2.booksys.common.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.v2tech.base.V2GenericException;

public class EmailFeedbackThread implements Runnable{
	private String email;
	
	
	
	String emailText = "";
	
	String name = "";
	
	public EmailFeedbackThread(String email, String name, String message){
		this.email = email;
		this.emailText = message;
		this.name = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			
			  Email email = new SimpleEmail();
			  String host = UtilService.getValue("hostName");
			  String from = UtilService.getValue("sendFrom");
			  String fromName = UtilService.getValue("sendFromName");
			  String pass = UtilService.getValue("sendFromPwd");
			  String smtpPort = UtilService.getValue("smtpPort");
			  email.setHostName(host);
			  email.addTo("jatin.sutaria@thev2technologies.com");
			  email.setFrom(from, fromName);
			  email.setMsg(emailText+"\n\n"+"Send by: "+this.name+"\n"+"Sender: "+this.email);
			  email.setSubject("Message Sent by "+this.email);
			 
			  email.setAuthenticator(new DefaultAuthenticator(from, pass)	);
				email.setTLS(true);
				//email.setSmtpPort(Integer.parseInt(smtpPort));
				email.setSSL(true);

			  // send the email
			  email.send();
			  System.out.println("Email Sent");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new V2GenericException("Can not send Email", e);
		}
	}

}
