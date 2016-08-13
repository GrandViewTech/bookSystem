package com.v2.booksys.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.User;

public class EmailHtmlThread implements Runnable{
	User user;
	
	//static String htmlUserRegStr = "";
	
	HtmlEmail he = new HtmlEmail();
	
	
	public EmailHtmlThread(User user ){
		this.user = user;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			 String msg = FileUtils.readFileToString(new File("userRegistrationAcknHtmlMessage.txt"));
			 String name = user.getFirstName()+" "+user.getLastName();
			 msg = msg.replaceAll(Pattern.quote("$Name$"), name);
			 
			 String verLink = UtilService.getValue("baseUrl");
			 byte[] data = Base64.encodeBase64(this.user.getUser().getBytes());
			 String encoded = new String(data);
			 encoded = URLEncoder.encode(encoded);
//			  URLEncoder.encode(s)
			 verLink = verLink +"/verifyPwd.jsp?verificationString="+encoded+"&socialMediaType="+this.user.getSocialMediaType().getType();
			 msg = msg.replaceAll(Pattern.quote("$Ver_Link$"), verLink);
			 
			 he.setHtmlMsg(msg);
			 String host = UtilService.getValue("hostName");
			  String from = UtilService.getValue("sendFrom");
			  String fromName = UtilService.getValue("sendFromName");
			  String pass = UtilService.getValue("sendFromPwd");
			  String smtpPort = UtilService.getValue("smtpPort");
			  he.setHostName(host);
			  he.addTo(user.getUser());
			  he.setFrom(from, fromName);
			  he.setSubject("Verification Email from Grovenue");
			  he.setAuthenticator(new DefaultAuthenticator(from, pass)	);
				he.setTLS(true);
				//he.setSmtpPort(Integer.parseInt(smtpPort));
				he.setSSL(true);

			  // send the he
			  he.send();
			  System.out.println("Email Sent");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new V2GenericException("Can not send Email", e);
		}
	}

}
