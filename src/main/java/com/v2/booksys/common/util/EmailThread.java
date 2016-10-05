package com.v2.booksys.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.v2tech.base.V2GenericException;

public class EmailThread implements Runnable{
	private String email;
	
	private String time;
	
	private String socialMediaType;
	
	private boolean isThisForPasswordRetrieval = false;
	static String str = null;
	
	static String strPlain;
	
	/**
	 * Password retrieval email template
	 */
	static String pwdStr;
	
	String password;
	static{
		
		try {
			str = FileUtils.readFileToString(new File("templates/emailRegistration.template"));
			strPlain = FileUtils.readFileToString(new File("templates/emailRegistrationPlain.template"));
			pwdStr = FileUtils.readFileToString(new File("templates/passwordRetrievalPlain.template"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Serious Problem");
			e.printStackTrace();
		}
	}
	
	
	public EmailThread(String email, String dateTime, boolean passwordRetrieval, String passwd, String socialMediaType1){
		this.email = email;
		this.time = dateTime;
		this.isThisForPasswordRetrieval = passwordRetrieval;
		this.password = passwd;
		this.socialMediaType = socialMediaType1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			StringTemplate template = new StringTemplate(str, DefaultTemplateLexer.class);
//			template.setAttribute("email", this.email);
//			template.setAttribute("dateTime", this.time);
//			str = template.toString();
			
			
			// Create the email message
			  Email email = new SimpleEmail();
			  String host = UtilService.getValue("hostName");
			  String from = UtilService.getValue("sendFrom");
			  String fromName = UtilService.getValue("sendFromName");
			  String pass = UtilService.getValue("sendFromPwd");
			  String smtpPort = UtilService.getValue("smtpPort");
			  email.setHostName(host);
			  email.addTo(this.email);
			  email.setFrom(from, fromName);
			  
			  if(!isThisForPasswordRetrieval){
				  template = new StringTemplate(strPlain, DefaultTemplateLexer.class);
				  template.setAttribute("email", this.email);
				  template.setAttribute("dateTime", this.time);
				  String verLink = UtilService.getValue("baseUrl");
				  byte[] data = Base64.encodeBase64(this.email.getBytes());
				  String encoded = new String(data);
				  encoded = URLEncoder.encode(encoded);
//				  URLEncoder.encode(s)
				  verLink = verLink +"/verifyPwd.jsp?verificationString="+encoded+"&socialMediaType="+this.socialMediaType;
				  template.setAttribute("verificationLink", verLink);
				  strPlain = template.toString();
				  email.setSubject("Verify your Email Address");
				  email.setMsg(strPlain);
			  }
			  else{
				  template = new StringTemplate(pwdStr, DefaultTemplateLexer.class);
				  template.setAttribute("email", this.email);
				  template.setAttribute("dateTime", this.time);
				  template.setAttribute("password", this.password);
				  pwdStr = template.toString();
				  email.setSubject("Password Retrieval Request from YetToBeIncorporated Inc");
				  email.setMsg(pwdStr);
			  }
			  
			  
			 
			  
			  // set the html message
			 // email.setHtmlMsg(str);

			  // set the alternative message
			  //email.setTextMsg("Your email client does not support HTML messages");
			 
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
