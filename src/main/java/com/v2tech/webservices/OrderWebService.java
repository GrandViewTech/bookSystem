package com.v2tech.webservices;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.Order;
import com.v2tech.repository.OrderRepository;
import com.v2tech.services.OrderService;

@Path("/orderWebService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class OrderWebService {
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@GET
	@Path("/orders/user/{user}/socialMedia/{socialMedia}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public javax.ws.rs.core.Response getOrders(@PathParam("user") String user, @PathParam("socialMedia") String socialMedia, @PathParam("token") String token){
		List<Order> orders = orderRepository.getOrdersByUserNameAndSocialMedia(user, socialMedia);
		return javax.ws.rs.core.Response.ok().entity(orders).build();
	}

}
