package com.test;

import java.io.File;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;

import com.v2.booksys.common.util.UtilService;
import com.v2tech.domain.SocialMediaType;

public class TestHtmlEmail {
	@Test
	public void testSendHtmlEmail() throws Exception{
		 HtmlEmail he = new HtmlEmail();
		
//		 StringBuffer msg = new StringBuffer();
//		 msg.append("<html><body>");
//		 msg.append("<h1>hello</h1>");
//		 msg.append("<h2>world</h2>");
//		 msg.append("</body></html>");
		 
		 String msg = FileUtils.readFileToString(new File("userRegistrationAcknHtmlMessage.txt"));
		 msg = msg.replaceAll(Pattern.quote("$Name$"), "jatin sutaria");
		 
		 String verLink = UtilService.getValue("baseUrl");
		 byte[] data = Base64.encodeBase64("jatin.sutaria@thev2technologies.com".getBytes());
		 String encoded = new String(data);
		 encoded = URLEncoder.encode(encoded);
//		  URLEncoder.encode(s)
		 verLink = verLink +"/verifyPwd.jsp?verificationString="+encoded+"&socialMediaType="+SocialMediaType.NONE.getType();
		 msg = msg.replaceAll(Pattern.quote("$Ver_Link$"), verLink);
		 he.setHtmlMsg(msg);
		 String host = UtilService.getValue("hostName");
		 String from = UtilService.getValue("sendFrom");
		 String fromName = UtilService.getValue("sendFromName");
		 String pass = UtilService.getValue("sendFromPwd");
		 String smtpPort = UtilService.getValue("smtpPort");
		 he.setHostName(host);
		 he.addTo("jatin.sutaria@thev2technologies.com");
		  he.setFrom(from, fromName);
		  he.setSubject("test");
		  he.setAuthenticator(new DefaultAuthenticator(from, pass)	);
			he.setTLS(true);
			//he.setSmtpPort(Integer.parseInt(smtpPort));
			he.setSSL(true);

		  // send the he
		  he.send();
		  System.out.println("Email Sent");
	}

}
