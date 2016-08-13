package com.v2tech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.domain.Order;
import com.v2tech.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	
	public Order saveOrUpdate(Order order){
		Order order2 = orderRepository.findOrderByOrderId(order.getOrderId());
			if(order2 == null){
				//create
				order2 = orderRepository.save(order);
			}
			else{
				//update
				//order.setId(order2.getId());
				//Order can not be updated
			}
		return 	order2;
	}

}
