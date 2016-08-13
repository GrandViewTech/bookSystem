package com.v2.booksys.common.util;

import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * <br>
 *
 * @Author : puneetsharma <br>
 * @createdDate : 05-Jul-2016 <br>
 * @createdTime : 9:50:37 am <br>
 * @Description : Used to Send Email In Order To Invite Peoples <br>
 */
@EnableAsync
//@PropertySource("classpath:bookSys.properties")
@Service("inviteFriendsEmailService")
public class InviteFriendsEmailService
	{
		private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InviteFriendsEmailService.class);

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
			{
				return new PropertySourcesPlaceholderConfigurer();
			}

		private String	charset	= "UTF-8";
		
		private String	hostName = UtilService.getValue("hostName");
		private String	sendFrom  = UtilService.getValue("sendFrom");;
		private String	sendFromName = UtilService.getValue("sendFromName");
		private String	sendFromPwd = UtilService.getValue("sendFromPwd");
		private String	smtpPort = UtilService.getValue("smtpPort");
		private String	sentMail = UtilService.getValue("sentMail");
		private String	baseUrl = UtilService.getValue("baseUrl");

		@Async
		public void inviteFriendsViaEmail(String user, String message, List<String> friendList)
			{
				if ((friendList == null) || (friendList.size() == 0))
					{
						return;
					}
				Email email = new SimpleEmail();
				email.setHostName(hostName);
				try
					{
						email.setSubject("Invitation to Join Grovenue On Behalf of " + user);
						email.setFrom(sendFrom, sendFromName);
						for (String friend : friendList)
							{
								email.addBcc(friend);
							}
						email.setMsg(message);
						email.setAuthenticator(new DefaultAuthenticator(sendFrom, sendFromPwd));
						email.setTLS(true);
						email.setSSL(true);
						email.send();
					}
				catch (EmailException exception)
					{
						logger.error("Error Sending Message for User " + user + " Cause :" + exception.getLocalizedMessage(), exception);
					}

			}

	}
