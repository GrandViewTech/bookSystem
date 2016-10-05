package com.v2tech.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Book;
import com.v2tech.domain.Keyword;
import com.v2tech.domain.Order;

@Repository
public interface OrderRepository extends GraphRepository<Order> {
	
									
	@Query("MATCH (order:Order) WHERE order.orderId = {0} RETURN order LIMIT 1;")
	public Order findOrderByOrderId(String orderId);
	
	@Query("MATCH (order:Order) WHERE order.user = {0} AND order.socialMedia = {1} RETURN order ;")
	public List<Order> getOrdersByUserNameAndSocialMedia(String user, String socialMedia);

}
