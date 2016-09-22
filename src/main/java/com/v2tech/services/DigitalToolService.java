package com.v2tech.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Book;
import com.v2tech.domain.DigitalTool;
import com.v2tech.domain.KeywordEntity;
import com.v2tech.domain.RESOURCE_TYPE;
import com.v2tech.domain.Review;
import com.v2tech.domain.util.ResourceEntity;
import com.v2tech.domain.util.SearchList;
import com.v2tech.repository.DigitalToolRepository;

@Service
public class DigitalToolService
	{
		
		@Autowired
		DigitalToolRepository digitalToolRepository;
		
		public DigitalTool getDigitalToolByName(String name)
			{
				Set<DigitalTool> tools = digitalToolRepository.findDigitalToolByName(name);
				if (tools.size() == 0)
					{
						return null;
					}
				else if (tools.size() > 1)
					{
						throw new V2GenericException("2 tools with same name found");
					}
				else
					{
						DigitalTool[] tools2 = new DigitalTool[tools.size()];
						tools2 = (DigitalTool[]) tools.toArray(tools2);
						return tools2[0];
					}
					
			}
			
		public void deleteTool(String name)
			{
				DigitalTool digitalTool = getDigitalToolByName(name);
				if (digitalTool != null)
					{
						digitalToolRepository.delete(digitalTool.getId());
					}
					
			}
			
		/**
		 * also set subject in this.
		 * 
		 * @param digitalTool
		 */
		public void markKeywordAndSearchParams(DigitalTool digitalTool)
			{
				DigitalTool digitalTool2 = getDigitalToolByName(digitalTool.getName());
				if (digitalTool2.getName() == null)
					{
						//do nothing
						return;
					}
					
				digitalTool2.setKeyword(digitalTool.getKeyword());
				digitalTool2.setSearchable(digitalTool.getSearchable());
				digitalTool2.setSubject(digitalTool.getSubject());
				digitalToolRepository.save(digitalTool2);
			}
			
		public DigitalTool saveOrUpdate(DigitalTool digitalTool)
			{
				if (digitalTool.getName() == null || digitalTool.getName().trim().length() == 0)
					{
						throw new V2GenericException("Tool Name can not be null/empty");
					}
					
				DigitalTool digitalTool2 = getDigitalToolByName(digitalTool.getName());
				if (digitalTool2 == null)
					{
						//create
						digitalTool.setKeyword(digitalTool.getName() + "," + digitalTool.getCareerStreams() + "," + digitalTool.getExams() + "," + digitalTool.getYearFounded());
						Double averageRating = digitalTool.getAverageRating();
						Integer rateCount = digitalTool.getRateCount();
						digitalTool.setAverageRating((averageRating == null) ? 2.5 : averageRating);
						digitalTool.setRateCount((rateCount == null) ? 1 : rateCount);
						digitalTool = digitalToolRepository.save(digitalTool);
						digitalTool2 = digitalTool;
					}
				else
					{
						//update
						digitalTool.setId(digitalTool2.getId());
						digitalTool.setKeyword(digitalTool.getName() + "," + digitalTool.getCareerStreams() + "," + digitalTool.getExams() + "," + digitalTool.getYearFounded());
						DozerBeanMapper beanMapper = new DozerBeanMapper();
						beanMapper.map(digitalTool, digitalTool2);
						Double averageRating = digitalTool2.getAverageRating();
						Integer rateCount = digitalTool2.getRateCount();
						digitalTool2.setAverageRating((averageRating == null) ? 2.5 : averageRating);
						digitalTool2.setRateCount((rateCount == null) ? 1 : rateCount);
						digitalTool2 = digitalToolRepository.save(digitalTool2);
					}
				return digitalTool2;
			}
			
		public Set<DigitalTool> searchDigitalToolByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return digitalToolRepository.searchDigitalToolByGenericKeyword(keyword, limit);
			}
			
		public Set<DigitalTool> searchTopRatedDigitalToolByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return digitalToolRepository.searchTopRatedDigitalToolByGenericKeyword(keyword, limit);
			}
			
		@Autowired
		private UserKeywordRelationService	userKeywordRelationService;
		@Autowired
		private ReviewService				reviewService;
		@Autowired
		private UserService					userService;
		
		public List<ResourceEntity> findDigitalResourceForUserPreference(String userId)
			{
				Set<String> uniqueIdentifiers = new HashSet<String>();
				List<ResourceEntity> resourceEntities = new LinkedList<ResourceEntity>();
				for (String keyword : userService.getKeywordFromUserProfile(userId))
					{
						for (DigitalTool digitalTool : searchDigitalToolByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = digitalTool.getName().trim().toLowerCase();
								if (uniqueIdentifiers.contains(resourceIdentity) == false)
									{
										uniqueIdentifiers.add(resourceIdentity);
										List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(RESOURCE_TYPE.DIGITAL_RESOURCE.getType(), resourceIdentity, 5);
										ResourceEntity resourceEntity = new ResourceEntity(digitalTool, reviews);
										List<String> resultCriterias = new ArrayList<String>();
										resultCriterias.add("friends");
										resourceEntity.setResultCriterias(resultCriterias);
										resourceEntities.add(resourceEntity);
									}
							}
					}
				return resourceEntities;
			}
			
		public List<ResourceEntity> searchTopRatedDigitalTool(Integer limit)
			{
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				Set<String> uniqueIdentifiers = new HashSet<String>();
				for (DigitalTool digitalTool : digitalToolRepository.searchTopRatedDigitalTool(limit))
					{
						String resourceIdentity = digitalTool.getName().trim().toLowerCase();
						if (uniqueIdentifiers.contains(resourceIdentity) == false)
							{
								uniqueIdentifiers.add(resourceIdentity);
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(RESOURCE_TYPE.DIGITAL_RESOURCE.getType(), resourceIdentity, 5);
								ResourceEntity resourceEntity = new ResourceEntity(digitalTool, reviews);
								List<String> resultCriterias = new ArrayList<String>();
								resultCriterias.add("rating");
								resourceEntity.setResultCriterias(resultCriterias);
								resourceEntities.add(resourceEntity);
							}
					}
				return resourceEntities;
			}
			
		public List<ResourceEntity> searchTopRatedDigitalToolByKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*"+keyword+".*";
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				Set<String> uniqueIdentifiers = new HashSet<String>();
				for (DigitalTool digitalTool : digitalToolRepository.searchTopRatedDigitalToolByGenericKeyword(keyword, limit))
					{
						String resourceIdentity = digitalTool.getName().trim().toLowerCase();
						if (uniqueIdentifiers.contains(resourceIdentity) == false)
							{
								uniqueIdentifiers.add(resourceIdentity);
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(RESOURCE_TYPE.DIGITAL_RESOURCE.getType(), resourceIdentity, 5);
								ResourceEntity resourceEntity = new ResourceEntity(digitalTool, reviews);
								List<String> resultCriterias = new ArrayList<String>();
								resultCriterias.add("searched");
								resultCriterias.add("rating");
								resourceEntity.setResultCriterias(resultCriterias);
								resourceEntities.add(resourceEntity);
							}
					}
				return resourceEntities;
			}
			
		public List<ResourceEntity> findDigitalResourceForFriends(String userId)
			{
				String keywordEntity = KeywordEntity.DIGITAL_RESOURCES.getEntity();
				String resourceReviewedType = RESOURCE_TYPE.DIGITAL_RESOURCE.getType();
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				Set<String> friendsRecommendationKeywords = new HashSet<String>();
				List<DigitalTool> digitalTools = new ArrayList<DigitalTool>();
				for (String keyword : userKeywordRelationService.getSearchedTermByFriendsAndKeyWordEntityType(userId, keywordEntity))
					{
						for (DigitalTool digitalTool : searchTopRatedDigitalToolByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = digitalTool.getName().trim().toLowerCase();
								friendsRecommendationKeywords.add(resourceIdentity);
								digitalTools.add(digitalTool);
							}
					}
				Set<String> systemsRecommendationKeywords = new HashSet<String>();
				for (String keyword : userKeywordRelationService.getRecommendedSearchTeamBySystemForEntityType(userId, keywordEntity))
					{
						for (DigitalTool digitalTool : searchTopRatedDigitalToolByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = digitalTool.getName().trim().toLowerCase();
								systemsRecommendationKeywords.add(resourceIdentity);
								digitalTools.add(digitalTool);
							}
					}
				Set<String> systemsRecommendationFromProfileKeywords = new HashSet<String>();
				for (String keyword : userService.getKeywordFromUserProfile(userId))
					{
						for (DigitalTool digitalTool : searchTopRatedDigitalToolByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = digitalTool.getName().trim().toLowerCase();
								systemsRecommendationFromProfileKeywords.add(resourceIdentity);
								digitalTools.add(digitalTool);
							}
					}
					
				Set<String> bookKeywords = new HashSet<String>();
				bookKeywords.add("Pratice");
				bookKeywords.add("solved");
				bookKeywords.add("paper");
				Set<String> relativeKeyword = new HashSet<String>();
				for (String keyword : bookKeywords)
					{
						for (DigitalTool digitalTool : searchTopRatedDigitalToolByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = digitalTool.getName().trim().toLowerCase();
								relativeKeyword.add(resourceIdentity);
								digitalTools.add(digitalTool);
							}
					}
				Set<String> uniqueKeys = new LinkedHashSet<String>();
				Integer reviewLimit = 5;
				for (DigitalTool digitalTool : digitalTools)
					{
						String resourceIdentity = digitalTool.getName().trim().toLowerCase();
						if (uniqueKeys.contains(resourceIdentity) == false)
							{
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(resourceReviewedType, resourceIdentity, reviewLimit);
								ResourceEntity resourceEntity = new ResourceEntity(digitalTool, reviews);
								List<String> resultCriterias = new ArrayList<String>();
								if (friendsRecommendationKeywords.contains(resourceIdentity))
									{
										resultCriterias.add("friends");
									}
								if (systemsRecommendationKeywords.contains(resourceIdentity))
									{
										resultCriterias.add("system");
									}
								if (systemsRecommendationFromProfileKeywords.contains(resourceIdentity))
									{
										resultCriterias.add("profile");
									}
								if (relativeKeyword.contains(resourceIdentity))
									{
										resultCriterias.add("relativeKeyword");
									}
								resourceEntity.setResultCriterias(resultCriterias);
								resourceEntities.add(resourceEntity);
								uniqueKeys.add(resourceIdentity);
							}
					}
				return resourceEntities;
			}
	}
