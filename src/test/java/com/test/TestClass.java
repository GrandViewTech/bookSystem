package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.Class1;
import com.v2tech.domain.Class2;
import com.v2tech.repository.Class1Repository;
import com.v2tech.repository.Class2Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestClass {
	@Autowired
	Class1Repository class1Repository;
	
	@Autowired
	Class2Repository class2Repository;
	
	@Test
	public void testRel2(){
		Set<Class1> cs = class1Repository.findUserByUserName("tt");
		
		Set<Class2> cs2 = class2Repository.findUserByUserName("rr");
		
		System.out.println("rel id "+cs2.size());
	}
	
	@Autowired
	Neo4jTemplate template;
	
	@Test
	public void testRel(){
		Class1 c1 = new Class1();
		c1.setTest("tt");
		c1 = class1Repository.save(c1);
		
		Class2 c2 = new Class2();
		c2.setTest("rr");
		c2 = class2Repository.save(c2);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("count", 1l);
		properties.put("__type__", "HAVE_SEARCHED");
		
		Relationship obj = template.createRelationshipBetween(template.getNode(c1.getId()), template.getNode(c2.getId()), "HAVE_SEARCHED", properties);
		System.out.println("relation "+obj);
		
		c1 = class1Repository.findOne(c1.getId());
		c2 = class2Repository.findOne(c2.getId());
		
		obj = template.getRelationshipBetween(template.getNode(c1.getId()), template.getNode(c2.getId()), "HAVE_SEARCHED");
		System.out.println("rel id "+obj.getId());
		//class1Repository
	}
	
	@Test
	public void testSearch(){
		Relationship obj = template.getRelationshipBetween(template.getNode(3691l), template.getNode(3692l), "HAVE_SEARCHED");
		System.out.println("rel id "+obj.getId());
	}

}
