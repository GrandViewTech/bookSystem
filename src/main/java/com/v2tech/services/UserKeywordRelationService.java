package com.v2tech.services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Keyword;
import com.v2tech.domain.KeywordEntity;
import com.v2tech.domain.User;
import com.v2tech.domain.UserKeywordRelation;
import com.v2tech.domain.util.KeywordResult;
import com.v2tech.repository.KeywordRepository;
import com.v2tech.repository.UserKeywordRelationRepository;
import com.v2tech.repository.UserRepository;

@Service
@EnableAsync
public class UserKeywordRelationService
	{
		@Autowired
		UserKeywordRelationRepository	searchRepository;
		
		@Autowired
		UserService						userService;
		
		@Autowired
		KeywordRepository				keywordRepository;
		
		@Autowired
		Neo4jTemplate					template;
		
		@Autowired
		UserRepository					userRepository;
		
		@Async
		public void increaseSearchTermCounterForUser(String user, String key)
			{
				
				User user2 = userService.getSingleUser(new User(user));
				if (user2 == null)
					{
						throw new V2GenericException("User can nopt be null");
					}
				Keyword keyword = keywordRepository.findKeywordByText(key);
				if (keyword == null)
					{
						keyword = keywordRepository.save(new Keyword(key));
					}
					
				UserKeywordRelation obj = searchRepository.findUserKeywordRelation(user, key);
				if (obj == null)
					{
						
						UserKeywordRelation keywordRelation = new UserKeywordRelation();
						keywordRelation.setCount(1l);
						keywordRelation.setUser(user2);
						keywordRelation.setKeyword(keyword);
						
						obj = template.save(keywordRelation);
						keywordRelation.setCount(1l);
						obj = template.save(keywordRelation);
					}
				else
					{
						obj.setCount(obj.getCount() + 1);
						obj = template.save(obj);
					}
			}
			
		/**
		 * For the passed user - return the search terms searched most by friends of the user
		 *
		 * @param user
		 * @return
		 */
		public List<String> getSearchedTermByFriends(String user)
			{
				List<KeywordResult> results = userRepository.getMostSearchedKeyword(user);
				List<String> terms = new ArrayList<String>();
				for (KeywordResult keywordResult : results)
					{
						terms.add(keywordResult.getSearchedTerm());
					}
				return terms;
			}
			
		public Set<String> getSearchedTermByFriendsAndKeyWordEntityType(String user, String keywordEntity)
			{
				List<KeywordResult> results = userRepository.getMostSearchedKeywordUsingEntityType(user, keywordEntity);
				Set<String> terms = new LinkedHashSet<String>();
				for (KeywordResult keywordResult : results)
					{
						terms.add(keywordResult.getSearchedTerm());
					}
				if (terms.size() == 0)
					{
						if (keywordEntity.equalsIgnoreCase(KeywordEntity.DIGITAL_RESOURCES.getEntity()))
							{
								terms.add("Engineering");
								terms.add("Medical");
								terms.add("Android");
								terms.add("Business");
								terms.add("JEE");
								terms.add("Marketing");
								terms.add("Computer");
								terms.add("Product design");
								terms.add("MBA");
								terms.add("GMAT");
								terms.add("GATE");
								terms.add("Law");
								terms.add("Management");
								terms.add("CBSE");
								terms.add("Architecture");
							}
						else if (keywordEntity.equalsIgnoreCase(KeywordEntity.COACHING_CLASSES.getEntity()))
							{
								terms.add("Engineering");
								terms.add("Medical");
								terms.add("Engineering,Medical");
								terms.add("JEE");
							}
						else if (keywordEntity.equalsIgnoreCase(KeywordEntity.BOOKS.getEntity()))
							{
								terms.add("Math");
								terms.add("Chemistry");
								terms.add("Biology");
								terms.add("Physic");
							}
					}
				return terms;
			}
			
		public Set<String> getTopRatedKeyWordForAGivenEntity(String keywordEntity)
			{
				List<KeywordResult> results = userRepository.getMostSearchedKeyword(keywordEntity);
				Set<String> terms = new LinkedHashSet<String>();
				for (KeywordResult keywordResult : results)
					{
						terms.add(keywordResult.getSearchedTerm());
					}
				return terms;
			}
	}
