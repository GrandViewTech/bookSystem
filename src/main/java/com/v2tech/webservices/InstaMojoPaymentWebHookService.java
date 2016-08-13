package com.v2tech.webservices;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;

@Path("/instamojoService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class InstaMojoPaymentWebHookService {
	
	@POST
	@Path("/webhook")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void recievePaymentAcknl(String response) throws IOException{
		System.out.println("Message recieved from InstaMojo "+response);
		FileUtils.write(new File("Payment.txt"), response);
		
	}

}
