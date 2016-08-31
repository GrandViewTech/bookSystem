package com.v2.booksys.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2tech.domain.SocialMediaType;
import com.v2tech.domain.User;
import com.v2tech.services.UserService;

public class UserVerficationClient {
	static List<Object> providers = new ArrayList<Object>();
	//static WebClient webClient = null;
	static ClassPathXmlApplicationContext ctx;
	
	static{
		ctx = new ClassPathXmlApplicationContext("appContext.xml");
	}
	
	public static String verifyUser(String userName, String socialMediaType){
		try {
			UserService userService = (UserService) ctx.getBean("userService");
			SocialMediaType mediaType = SocialMediaType.valueOf(socialMediaType);
			if(mediaType == null){
				return "Msg-Invalid MediaType Sent";
			}
		
			byte[] verCode = Base64.decodeBase64(userName.getBytes());
			String actualUser = new String(verCode);
		User user = new User();
		user.setUser(actualUser);
		user.setSocialMediaType(SocialMediaType.valueOf(socialMediaType));
		User usr = userService.getSingleUserBySocialMediaType(user);
			if(usr == null){
				return "Msg-User Does not exist!";
			}
		
			if(usr.isValidated()){
				return "Msg-User Already Validated";
			}
		usr.setValidated(true);
		userService.saveOrUpdate(usr);
		ObjectMapper mapper = new ObjectMapper();
		String u = mapper.writeValueAsString(usr);
		//boolean res = userService.markUserAsValidated(actualUser, mediaType);
		return "Msg-Success-"+u;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Msg-Verification Failed. Contact Admin";
		}
		 
	}
	
//	public static String verifyUser(String userName, String socialMediaType){
//		try {
//			providers.add(new JacksonJsonProvider() );
//			String baseUrl = UtilService.getValue("baseUrl");
//			baseUrl = (baseUrl + "resourceService/verifyUser/user/"+userName+"/socialMediaType/"+socialMediaType+"/token/temp");
//			//webClient = WebClient.create("http://utilityapplications-socialapp.rhcloud.com/ws/rest/pdfBoxService/pdfToJavaOutput", providers);
//			webClient = WebClient.create(baseUrl, providers);
//			webClient.header("Content-Type", "application/json");
//			webClient.type(MediaType.APPLICATION_JSON);
//			HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
//			conduit.getClient().setReceiveTimeout(5000);
//			webClient.accept("application/json").type("multipart/form-data");
//			String res =  webClient.post(null, String.class);
//			return res;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "Verification Failed";
//		}
//		 
//	}

}
