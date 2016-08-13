package com.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.Keyword;
import com.v2tech.domain.User;
import com.v2tech.domain.UserKeywordRelation;
import com.v2tech.repository.KeywordRepository;
import com.v2tech.repository.UserKeywordRelationRepository;
import com.v2tech.repository.UserRepository;
import com.v2tech.services.KeywordService;
import com.v2tech.services.UserKeywordRelationService;
import com.v2tech.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestKeyword {
	@Autowired
	KeywordRepository keywordRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Neo4jTemplate template;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserKeywordRelationRepository  searchRepository;
	
	@Autowired
	Neo4jOperations operations;
	
	@Autowired
	UserKeywordRelationService keywordService;
	@Autowired
	KeywordService autocompleteService;
	
	@Test
	public void testCreateAllAutoCompleteKeywords(){
		autocompleteService.createAndUpdateKeywordsForSystem();
	}
	
	@Test
	public void testFetchAutoCompleteKeywordsStartsWith(){
		Set<Keyword> keywords = autocompleteService.getAutowireKeywords("a");
			for(Keyword key : keywords){
				System.out.println(key.getText());
			}
	}
	
	@Test
	public void testFetchAutoCompleteKeywordsContains(){
		Set<Keyword> keywords = autocompleteService.getAutowireKeywordsContains("solved");
			for(Keyword key : keywords){
				System.out.println(key.getText());
			}
	}
	
	@Test
	public void testUserKeywordRelationService(){
		keywordService.increaseSearchTermCounterForUser("vraj.sutaria", "Algebra6");
	}
	
	@Test
	public void testgetSearchTermsUsedByFriends(){
		List<String> terms = keywordService.getSearchedTermByFriends("sachin.sutaria");
		for(String term : terms){
			System.out.println(term);
		}
	}
	
	
	@Test
	public void testKeyword(){
		Keyword key = new Keyword("Engineering6");
		keywordRepository.save(key);
		Keyword keyword = keywordRepository.findKeywordByText("Engineering6");
		System.out.println("txt is "+keyword.getText());
		
	}
	
	@Test
	public void testAddFriends(){
		User user = userService.getSingleUser(new User("jatin.sutaria"));
		user.setUser("vraj.sutaria");
		user.setPassword("test");
		user = userService.saveOrUpdate(user);
		
		User user2 = new User();
		user2.setUser("sachin.sutaria");
		user2.setPassword("test");
		user2 = userService.saveOrUpdate(user2);
		
		Set<User> users = new HashSet();
		users.add(user);
		
		userService.addFriendsToUser(user2, users);
	}
	
	@Test
	public void testUserAndKeyword(){
		User user = userService.getSingleUser(new User("vraj.sutaria"));
		
		
		String text = "Algebra4";
		Keyword key = new Keyword(text);
		keywordRepository.save(key);
		

		
		UserKeywordRelation keywordRelation = new UserKeywordRelation();
		keywordRelation.setCount(1l);
		keywordRelation.setUser(user);
		keywordRelation.setKeyword(key);
		
		UserKeywordRelation obj = template.save(keywordRelation);
		//operations.
		
		System.out.println("rel id "+obj.getNodeId());

		UserKeywordRelation obj2 = searchRepository.findUserKeywordRelation("vraj.sutaria", text);
		obj2.setCount(7l);
		template.save(obj2);
	
	}
	
	@Test
	public void testUpdateRelationship(){
		User user = userService.getSingleUser(new User("jatin.sutaria"));
		Keyword key = keywordRepository.findKeywordByText("Engineering6");
		Relationship obj = template.getRelationshipBetween(template.getNode(user.getId()), template.getNode(key.getId()), "HAVE_SEARCHED");
		Integer count = (Integer)obj.getProperty("count");
		obj.setProperty("count", (count++));
		//obj.get
		template.save(obj);
		obj = template.getRelationshipBetween(template.getNode(user.getId()), template.getNode(key.getId()), "HAVE_SEARCHED");
		System.out.println("count is "+(Integer)obj.getProperty("count"));
	}
	
	@Test
	public void testUser(){
		User user = new User();
		user.setUser("jatin.sutaria");
		user.setPassword("test");
		user = userRepository.save(user);
		System.out.println(user);
	}
	
	
	
	@Test
	public void testGetUserKeywords(){
		UserKeywordRelation obj = searchRepository.findUserKeywordRelation("jatin.sutaria", "Medical");
		obj.setCount(6l);
		template.save(obj);
	}
	
	@Test
	public void testDoIt(){
		//searchRepository.
	}

}
