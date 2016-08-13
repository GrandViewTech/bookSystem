package com.test;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2tech.domain.InstaMojo_CustomFields;
import com.v2tech.domain.Order;

import java.util.*;
public class TestReadInstaMojoResponse {
	
	
	String str = "serviceType%3DCV%23package%3DBasic-%200%20to%201%20Exp%23templateUseOnly%3Dtrue%23expressService%3Dtrue%23superExpressService%3Dtrue%23eachAdditionIteration%3Dtrue%23coverLetter%3Dfalse%23tuAmount%3D500%23eSAmt%3D300%23sESAmt%3D750%23eAI%3D1000%23";
	@Test
	public void testDec(){
		String s = URLDecoder.decode(str);
		System.out.println(s);
	}
	@Test
	public void testReadResponse() throws Exception{
		String str = FileUtils.readFileToString(new File("C:\\delete\\classes\\com\\v2tech\\webservices\\insta.txt"));
		StringTokenizer stk = new StringTokenizer(str, "&");
		Order order = new Order();
			while(stk.hasMoreTokens()){
				String token = stk.nextToken();
				if(token.startsWith("amount")){
					String val = token.substring("amount=".length(), token.length());
					order.setPrice(Float.parseFloat(val));
				}
				else if(token.startsWith("buyer")){
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
						StringTokenizer tokenizer = new StringTokenizer(value, "#");
						while(tokenizer.hasMoreTokens()){
							String tok = tokenizer.nextToken();
							if(tok.startsWith("serviceType=")){
								String serType = tok.substring("serviceType=".length(), tok.length());
								order.setServiceType(serType);
							}
							else if(tok.startsWith("package=")){
								String packageT = tok.substring("package=".length(), tok.length());
								order.setPackageDetails(packageT);
							}
							else if(tok.startsWith("templateUseOnly=")){
								String templateUseOnly = tok.substring("templateUseOnly=".length(), tok.length());
								order.setTemplateUseOnly(Boolean.valueOf(templateUseOnly));
							}
							else if(tok.startsWith("expressService=")){
								String expressService = tok.substring("expressService=".length(), tok.length());
								order.setExpressService(Boolean.valueOf(expressService));
							}
							else if(tok.startsWith("superExpressService=")){
								String superExpressService = tok.substring("superExpressService=".length(), tok.length());
								order.setSuperExpressService(Boolean.valueOf(superExpressService));
							}
							else if(tok.startsWith("eachAdditionIteration=")){
								String eachAdditionIteration = tok.substring("eachAdditionIteration=".length(), tok.length());
								order.setEachAdditionIteration(Boolean.valueOf(eachAdditionIteration));
							}
							else if(tok.startsWith("coverLetter=")){
								String coverLetter = tok.substring("coverLetter=".length(), tok.length());
								order.setCoverLetter(Boolean.valueOf(coverLetter));
							}
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
						}
				}
				
			}
			System.out.println(order.getEachAdditionIterationAmount());
	}

}
