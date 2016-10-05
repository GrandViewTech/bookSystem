package com.v2tech.webservices;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2.booksys.common.util.AmazonClient;
import com.v2tech.domain.Order;
import com.v2tech.domain.SocialMediaType;
import com.v2tech.domain.User;
import com.v2tech.domain.User_Template;
import com.v2tech.repository.User_TemplateRepository;
import com.v2tech.services.OrderService;
import com.v2tech.services.User_TemplateService;

@Path("/beJobReadyService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class BeJobReadyWebService {
	static org.slf4j.Logger logger = LoggerFactory.getLogger(BeJobReadyWebService.class);
	@Autowired
	User_TemplateService user_TemplateService;
	
	@Autowired
	User_TemplateRepository user_TemplateRepository;
	

	@Autowired
	OrderService orderService;
	
	
	@POST
	@Path("/sendAcknlMailForJobReadyService/templateType/{templateType}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response sendAcknlMailForJobReadyService(User user, @PathParam("templateType") String templateType, @PathParam("token") String token){
		logger.info("In sendAcknlMailForJobReadyService");
		if(user.getUser() == null || user.getUser().trim().length() == 0){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		//Send Email to User about services bought. - later
		//Create the request for be job ready service in database
		User_Template template = new User_Template();
		template.setUser(user.getUser());
		template.setSocialMediaType(user.getSocialMediaType().getType());
		template.setTemlplateType(templateType);
		template.setDateLastUpdated(new Date().toString());
		user_TemplateService.saveOrUpdate(template);
		logger.info("In sendAcknlMailForJobReadyService complete");
		return Response.ok().build();
	}
	
	@GET
	@Path("/getAllUserTemplates/user/{user}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response fetchAllUserTemplatesByUserName(@PathParam("user") String user, @PathParam("token") String token){
		List<User_Template> user_Templates = user_TemplateRepository.searchAllUserTemplatesByName(user);
		return Response.ok().entity(user_Templates).build();
	}
	
	@GET
	@Path("/getOrderuniqueId/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response getOrderUUID(@PathParam("user") String user, @PathParam("token") String token){
		System.out.println("Generating uuid .........**************************************************");
		UUID uuid = null;
		synchronized(this){
			uuid = UUID.randomUUID();
		}
		
		return Response.ok().entity(uuid.toString()).build();
	}
	@POST
	@Path("/createOrder/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createOrder(Order order , @PathParam("token") String token){
		System.out.println("Creating order .........**************************************************");
		orderService.saveOrUpdate(order);
		return Response.ok().build();
	}
	
	/**
	 * this works
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	@POST
	@Path("/webHookConfirm1/token/{token}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Transactional
	public Response webHookConfirm1(String data) {
		try {
			logger.info("In webHookConfirm1");
			String decodedData = URLDecoder.decode(data);
			System.out.println(" data from insta mojo "+decodedData);
			//FileUtils.write(new File("insta.txt"), decodedData);
			StringTokenizer stk = new StringTokenizer(decodedData, "&");
			Order order = new Order();
			while(stk.hasMoreTokens()){
				String token = stk.nextToken();
				if(token.startsWith("amount")){
					String val = token.substring("amount=".length(), token.length());
					order.setPrice(Float.parseFloat(val));
				}
				else if(token.startsWith("buyer=")){
					String val = token.substring("buyer=".length(), token.length());
					order.setUser(val);
				}
				else if(token.startsWith("payment_id")){
					String val = token.substring("payment_id=".length(), token.length());
					order.setOrderId(val);
				}
				else if(token.startsWith("custom_fields")){
					String val = token.substring("custom_fields=".length(), token.length());
					ObjectMapper mapper = new ObjectMapper();
					Map<String,Map<String,String>> customFields = new HashMap<String,Map<String,String>>();
					customFields = mapper.readValue(val, customFields.getClass());
					Map<String,String> socialMedia = customFields.get("Field_65046");
					order.setSocialMedia(socialMedia.get("value"));
					
					Map<String,String> orderDetails = customFields.get("Field_53399");
					String value = orderDetails.get("value");
					value = URLDecoder.decode(value);
					System.out.println("value is ********** ");
						StringTokenizer tokenizer = new StringTokenizer(value, "#");
						while(tokenizer.hasMoreTokens()){
							String tok = tokenizer.nextToken();
							if(tok.startsWith("sT=")){
								String serType = tok.substring("sT=".length(), tok.length());
								order.setServiceType(serType);
							}
							else if(tok.startsWith("pkg=")){
								String packageT = tok.substring("pkg=".length(), tok.length());
								order.setPackageDetails(packageT);
							}
							else if(tok.startsWith("bP=")){
								String basePrice = tok.substring("bP=".length(), tok.length());
								order.setPrice(Float.valueOf(basePrice));
							}
							else if(tok.startsWith("tUO=")){
								String templateUseOnly = tok.substring("tUO=".length(), tok.length());
								order.setTemplateUseOnly(Boolean.valueOf(templateUseOnly));
							}
							else if(tok.startsWith("eS=")){
								String expressService = tok.substring("eS=".length(), tok.length());
								order.setExpressService(Boolean.valueOf(expressService));
							}
							else if(tok.startsWith("sES=")){
								String superExpressService = tok.substring("sES=".length(), tok.length());
								order.setSuperExpressService(Boolean.valueOf(superExpressService));
							}
							else if(tok.startsWith("eAI=")){
								String eachAdditionIteration = tok.substring("eAI=".length(), tok.length());
								order.setEachAdditionIteration(Boolean.valueOf(eachAdditionIteration));
							}
							else if(tok.startsWith("cL=")){
								String coverLetter = tok.substring("cL=".length(), tok.length());
								order.setCoverLetter(Boolean.valueOf(coverLetter));
							}//start with amts
							else if(tok.startsWith("tuAmount=")){
								String tuAmount = tok.substring("tuAmount=".length(), tok.length());
								order.setTemplateUseOnlyAmount(Float.valueOf(tuAmount));
							}
							else if(tok.startsWith("eSAmt=")){
								String eSAmt = tok.substring("eSAmt=".length(), tok.length());
								order.setExpressServiceAmount(Float.valueOf(eSAmt));
							}
							else if(tok.startsWith("sESAmt=")){
								String sESAmt = tok.substring("sESAmt=".length(), tok.length());
								order.setSuperExpressServiceAmount(Float.valueOf(sESAmt));
							}
							else if(tok.startsWith("eAIAmt=")){
								String eAIAmt = tok.substring("eAIAmt=".length(), tok.length());
								order.setEachAdditionIterationAmount(Float.valueOf(eAIAmt));
							}
							else if(tok.startsWith("covAmt=")){
								String covAmt = tok.substring("covAmt=".length(), tok.length());
								order.setCoverLetterAmount(Float.valueOf(covAmt));
							}
							else if(tok.startsWith("tPrice=")){
								String totalPrice = tok.substring("tPrice=".length(), tok.length());
								order.setTotalPrice(Float.valueOf(totalPrice));
							}
						}
				}
				
			}
			System.out.println(order);
			ObjectMapper mapper = new ObjectMapper();
			
			String orderJson = mapper.writeValueAsString(order);
			System.out.println("service type "+order.getServiceType());
			FileUtils.write(new File("orders"+File.separator+"Order_"+order.getOrderId()+".json"), orderJson);
			order.setCreateDate(new Date().toString());
			orderService.saveOrUpdate(order);
			System.out.println("Order saved***************************************************************************** ");
			logger.info("Order saved***************************************************************************** ");
				if(order.getServiceType().equalsIgnoreCase("CV")){
					
					User user = new User();
					user.setUser(order.getUser());
					user.setSocialMediaType(SocialMediaType.valueOf(order.getSocialMedia()));
					sendAcknlMailForJobReadyService(user, "CV", "test");
					System.out.println("User Template saved CV***************************************************************************** 1");
					logger.info("User Template saved CV***************************************************************************** 1");
						if(order.isCoverLetter()){
							sendAcknlMailForJobReadyService(user, "COVER", "test");
							System.out.println("User Template saved COVER***************************************************************************** 1.1");
							logger.info("User Template saved COVER***************************************************************************** 1.1");
							
						}
				}
				else if(order.getServiceType().equalsIgnoreCase("COVER")){
					User user = new User();
					user.setUser(order.getUser());
					user.setSocialMediaType(SocialMediaType.valueOf(order.getSocialMedia()));
					sendAcknlMailForJobReadyService(user, "COVER", "test");
					System.out.println("User Template saved COVER***************************************************************************** 2");
					logger.info("User Template saved COVER***************************************************************************** 2");
				}
				logger.info("In webHookConfirm1 complete");
			return Response.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("IMPORTANT - PROBLEM IN POST PAYMENT PROCESSING DESPITE PAYMENT RECIEVED", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
}
