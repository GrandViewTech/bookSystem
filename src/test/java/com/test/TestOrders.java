package com.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.Order;
import com.v2tech.webservices.OrderWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestOrders {
	@Autowired
	OrderWebService orderWebService;
	
	@Test
	public void testGetOrders(){
		List<Order> orders = (List<Order>) orderWebService.getOrders("jatin.sutaria@thev2technologies.com", "NONE", "test").getEntity();
		for(Order ord: orders){
			System.out.println("Package Details "+ord.getPackageDetails()+" service type "+ord.getServiceType());
		}
	}

}
