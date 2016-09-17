package com.v2tech.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.KeywordEntity;
import com.v2tech.domain.RESOURCE_TYPE;
import com.v2tech.domain.Review;
import com.v2tech.domain.util.AddNode;
import com.v2tech.domain.util.Neo4jIndexNode;
import com.v2tech.domain.util.Neo4jSearchResult;
import com.v2tech.domain.util.Neo4jSearchStatement;
import com.v2tech.domain.util.Neo4jSearchStatements;
import com.v2tech.domain.util.ResourceEntity;
import com.v2tech.domain.util.ResultRow;
import com.v2tech.repository.CoachingClassRepository;

@Service
@PropertySource("classpath:bookSys.properties")
public class CoachingClassService1
	{
		
		private static ObjectMapper	objectMapper	= null;
		private static ObjectWriter	objectWriter	= null;
		
		static
			{
				try
					{
						objectMapper = new ObjectMapper();
						objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
					}
				catch (Exception exception)
					{
						exception.printStackTrace();
					}
			}
			
		@Value("${addNodeToSpatialPlugin}")
		private String						addNodeToSpatialPlugin;
		
		@Value("${addNodeToSpatialPluginApiUrl}")
		private String						addNodeToSpatialPluginApiUrl;
		
		@Value("${updateNeo4jSpatialIndexes}")
		private String						updateNeo4jSpatialIndexes;
		
		@Autowired
		private UserKeywordRelationService	userKeywordRelationService;
		@Autowired
		private ReviewService				reviewService;
		@Autowired
		private UserService					userService;
		
		@Autowired
		private CoachingClassRepository		coachingClassRepository;
		
		@Autowired
		private CountryStateCityService		countryStateCityService;
		
		public CoachingClass findByNameAndBranchAndZipCode(String name, String branch, String zipCode)
			{
				Set<CoachingClass> classes = coachingClassRepository.findByNameAndBranchAndZipCode(name, branch, zipCode);
				if (classes.size() == 0)
					{
						return null;
					}
				else
					{
						
						CoachingClass classArray[] = new CoachingClass[classes.size()];
						CoachingClass class1 = (classes.toArray(classArray))[0];
						return class1;
					}
			}
			
		public void updateKeyword(CoachingClass coachingClass)
			{
				CoachingClass coachingClass2 = findByNameAndBranchAndZipCode(coachingClass.getName(), coachingClass.getBranch(), coachingClass.getZip());
				if (coachingClass2 == null)
					{
						throw new V2GenericException("No CoachingClass available to update the keyword");
					}
				coachingClass2.setKeyword(coachingClass.getKeyword());
				coachingClass2.setSearchable(coachingClass.getSearchable());
				coachingClassRepository.save(coachingClass2);
			}
			
		public CoachingClass saveOrUpdate(CoachingClass coachngClass)
			{
				if (coachngClass == null)
					{
						throw new V2GenericException("No data");
					}
					
				if (coachngClass.getBranch() == null)
					{
						if (coachngClass.getCity() == null)
							{
								if (coachngClass.getZip() == null)
									{
										throw new V2GenericException("No location info present");
									}
								else
									{
										coachngClass.setCity("NA");
										coachngClass.setBranch("NA");
									}
									
							}
						else
							{
								coachngClass.setBranch(coachngClass.getCity());
							}
					}
					
				if (coachngClass.getZip() == null)
					{
						coachngClass.setZip("NA");
					}
					
				CoachingClass coachingClass = findByNameAndBranchAndZipCode(coachngClass.getName(), coachngClass.getBranch(), coachngClass.getZip());
				if (coachingClass == null)
					{
						//This is a new record
						coachngClass.setKeyword(coachngClass.getZip() + "," + coachngClass.getBranch() + "," + coachngClass.getCity() + "," + coachngClass.getName() + "," + coachngClass.getcStreams() + "," + coachngClass.getAverageBatchSize() + "," + coachngClass.getrExams() + "," + coachngClass.getCourses());
						String pincode = coachngClass.getZip();
						com.v2tech.domain.util.GoogleApiResponse googleApiResponse = countryStateCityService.findGeoLocationByPinCode(pincode, 1);
						if (googleApiResponse != null)
							{
								coachngClass.setLat(googleApiResponse.getlatitude());
								coachngClass.setLon(googleApiResponse.getLongitude());
							}
						coachingClass = coachingClassRepository.save(coachngClass);
						return coachingClass;
					}
				else
					{
						//This is a update operation
						
						if (!coachingClass.getcStreams().contains(coachngClass.getcStreams()))
							{
								coachingClass.setcStreams(coachingClass.getcStreams() + "," + coachngClass.getcStreams());
							}
							
						if (!coachingClass.getCourses().contains(coachngClass.getCourses()))
							{
								coachingClass.setCourses(coachingClass.getCourses() + "," + coachngClass.getCourses());
							}
							
						if (!coachingClass.getrExams().contains(coachngClass.getrExams()))
							{
								coachingClass.setrExams(coachingClass.getrExams() + "," + coachngClass.getrExams());
							}
							
						if (!coachingClass.getTargetStudents().contains(coachngClass.getTargetStudents()))
							{
								coachingClass.setTargetStudents(coachingClass.getTargetStudents() + "," + coachngClass.getTargetStudents());
							}
							
						if (!coachingClass.getTypesOfCoursesOffered().contains(coachngClass.getTypesOfCoursesOffered()))
							{
								coachingClass.setTypesOfCoursesOffered(coachingClass.getTypesOfCoursesOffered() + "," + coachngClass.getTypesOfCoursesOffered());
							}
						coachingClass.setCity(coachngClass.getCity());
						coachingClass.setState(coachngClass.getState());
						coachingClass.setAddedBy(coachngClass.getAddedBy());
						coachingClass.setAddress(coachngClass.getAddress());
						coachingClass.setZip(coachngClass.getZip());
						coachingClass.setAverageBatchSize(coachngClass.getAverageBatchSize());
						coachingClass.setCourseMaterial(coachngClass.getCourseMaterial());
						coachingClass.setDescription(coachngClass.getDescription());
						coachingClass.setDuration(coachngClass.getDuration());
						coachingClass.setJuniorCollegesPartnerShip(coachngClass.getJuniorCollegesPartnerShip());
						coachingClass.setMedium(coachngClass.getMedium());
						coachingClass.setSchedule(coachngClass.getSummary());
						coachingClass.setTypeOfProgram(coachngClass.getTypeOfProgram());
						coachingClass.setWebsite(coachngClass.getWebsite());
						coachingClass.setYearFounded(coachngClass.getYearFounded());
						coachingClass.setKeyword(coachngClass.getState() + "," + coachngClass.getZip() + "," + coachngClass.getBranch() + "," + coachngClass.getCity() + "," + coachngClass.getName() + "," + coachngClass.getcStreams() + "," + coachngClass.getAverageBatchSize() + "," + coachngClass.getrExams() + "," + coachngClass.getCourses());
						// Update Rating if not found
						Double averageRating = coachingClass.getAverageRating();
						Integer rateCount = coachingClass.getRateCount();
						coachingClass.setAverageRating((averageRating == null) ? 2.5 : averageRating);
						coachingClass.setRateCount((rateCount == null) ? 1 : rateCount);
						String pincode = coachngClass.getZip();
						com.v2tech.domain.util.GoogleApiResponse googleApiResponse = countryStateCityService.findGeoLocationByPinCode(pincode, 1);
						if (googleApiResponse != null)
							{
								coachingClass.setLat(googleApiResponse.getlatitude());
								coachingClass.setLon(googleApiResponse.getLongitude());
							}
						coachingClass = coachingClassRepository.save(coachingClass);
						addNodeToIndex(coachingClass.getId());
						return coachingClass;
					}
			}
			
		public Set<CoachingClass> searchCoachingClassByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return coachingClassRepository.searchCoachingClassByGenericKeyword(keyword, limit);
			}
			
		public Set<CoachingClass> searchTopRatedCoachingClassByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return coachingClassRepository.searchTopRatedCoachingClassByGenericKeyword(keyword, limit);
			}
			
		public Set<CoachingClass> findCoachingClassByKeywordAndCity(String keyword, String city, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				city = "(?i)" + city.trim();
				return coachingClassRepository.findCoachingClassByKeywordAndCity(keyword, city, limit);
			}
			
		public Set<CoachingClass> findCoachingClassByCity(String city, Integer limit)
			{
				city = "(?i)" + city.trim();
				return coachingClassRepository.findCoachingClassByCity(city, limit);
			}
			
		public List<ResultRow> findCoachingClassForLocality(String location, String keyword, Double radius)
			{
				List<ResultRow> resultRows = new ArrayList<ResultRow>();
				Neo4jSearchStatement neo4jSearchStatement = new Neo4jSearchStatement();
				com.v2tech.domain.util.GoogleApiResponse googleApiResponse = countryStateCityService.findGeoLocationByPinCode(location, 2);
				Double latitude = googleApiResponse.getlatitude();
				Double longitude = googleApiResponse.getLongitude();
				if (googleApiResponse != null)
					{
						if (keyword != null && keyword.trim().length() > 0)
							{
								if (keyword.equalsIgnoreCase("None") == false)
									{
										keyword = "(?i).*" + keyword + ".*";
										neo4jSearchStatement.setStatement("start coachingClass = node:geom('withinDistance:[" + latitude + "," + longitude + "," + radius + "]') MATCH coachingClass WHERE coachingClass.searchable='yes' AND (coachingClass.keyword=~'" + keyword + "' OR coachingClass.cStreams=~'" + keyword + "' OR coachingClass.courses =~'" + keyword + "' OR coachingClass.typesOfCoursesOffered =~'" + keyword + "'  OR coachingClass.typeOfProgram =~'" + keyword
										        + "' ) return  coachingClass ORDER BY coachingClass.rateCount , coachingClass.averageRating DESC");
									}
								else
									{
										neo4jSearchStatement.setStatement("start coachingClass = node:geom('withinDistance:[" + latitude + "," + longitude + "," + radius + "]') MATCH coachingClass WHERE coachingClass.searchable='yes' return coachingClass ORDER BY coachingClass.rateCount  , coachingClass.averageRating DESC ");
									}
							}
						else
							{
								neo4jSearchStatement.setStatement("start coachingClass = node:geom('withinDistance:[" + latitude + "," + longitude + "," + radius + "]') MATCH coachingClass  WHERE coachingClass.searchable='yes' return coachingClass ORDER BY coachingClass.rateCount , coachingClass.averageRating DESC");
							}
						try
							{
								List<Neo4jSearchStatement> searchStatements = new ArrayList<Neo4jSearchStatement>();
								searchStatements.add(neo4jSearchStatement);
								Neo4jSearchStatements neo4jSearchStatements = new Neo4jSearchStatements();
								neo4jSearchStatements.setStatements(searchStatements);
								String jsonStr = objectWriter.writeValueAsString(neo4jSearchStatements);
								URL obj = new URL(updateNeo4jSpatialIndexes);
								HttpURLConnection con = (HttpURLConnection) obj.openConnection();
								con.setRequestMethod("POST");
								con.setRequestProperty("Content-Type", "application/json");
								con.setDoOutput(true);
								DataOutputStream wr = new DataOutputStream(con.getOutputStream());
								wr.writeBytes(jsonStr);
								wr.flush();
								wr.close();
								int responseCode = con.getResponseCode();
								if (responseCode == 200)
									{
										BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
										String inputLine;
										StringBuffer response = new StringBuffer();
										while ((inputLine = bufferedReader.readLine()) != null)
											{
												response.append(inputLine);
											}
										bufferedReader.close();
										Neo4jSearchResult neo4jSearchResult = objectMapper.readValue(response.toString(), Neo4jSearchResult.class);
										resultRows = neo4jSearchResult.getResultRow();
									}
							}
						catch (Exception exception)
							{
								exception.printStackTrace();
							}
					}
				return resultRows;
			}
			
		private void addNodeToIndex(Long nodeId)
			{
				try
					{
						String url = addNodeToSpatialPlugin;
						url = url.replaceAll("nodeId", nodeId.toString());
						AddNode addNode = new AddNode();
						addNode.setLayer("geom");
						addNode.setNode(url);
						String jsonStr = objectWriter.writeValueAsString(addNode);
						URL obj = new URL(addNodeToSpatialPluginApiUrl);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						con.setRequestMethod("POST");
						con.setRequestProperty("Content-Type", "application/json");
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(jsonStr);
						wr.flush();
						wr.close();
						int responseCode = con.getResponseCode();
						if (responseCode == 200)
							{
								System.out.println("Node Id :" + nodeId + " Successfully Added To Spatial Index");
								Neo4jIndexNode neo4jIndexNode = new Neo4jIndexNode("CoachingClass");
								jsonStr = objectWriter.writeValueAsString(neo4jIndexNode);
								obj = new URL(updateNeo4jSpatialIndexes);
								con = (HttpURLConnection) obj.openConnection();
								con.setRequestMethod("POST");
								con.setRequestProperty("Content-Type", "application/json");
								con.setDoOutput(true);
								wr = new DataOutputStream(con.getOutputStream());
								wr.writeBytes(jsonStr);
								wr.flush();
								wr.close();
								int responseCode1 = con.getResponseCode();
								if (responseCode1 == 200)
									{
										System.out.println("Node Id :" + nodeId + " Successfully Updated The Spatial Indexes");
									}
									
							}
					}
				catch (Exception exception)
					{
						exception.printStackTrace();
					}
			}
			
		public List<ResourceEntity> findCoachingClasessForUserFriends(String userId, String location, Double radius)
			{
				List<ResourceEntity> resourceEntities = findCoachingClassesByCriteria(userId, userKeywordRelationService.getSearchedTermByFriendsAndKeyWordEntityType(userId, KeywordEntity.COACHING_CLASSES.getEntity()), "friends", location, radius);
				Set<String> keywords = new HashSet<String>();
				keywords.add("Regular");
				keywords.add("Distance Learning");
				keywords.add("Test Series");
				keywords.add("Crash Course");
				resourceEntities.addAll(findCoachingClassesByCriteria(userId, keywords, "relativeKeyword", location, radius));
				return resourceEntities;
			}
			
		public List<ResourceEntity> findCoachingClassesByCriteria(String userId, Set<String> keywords, String criteria, String location, Double radius)
			{
				
				Set<String> uniqueIdentifiers = new HashSet<String>();
				List<ResourceEntity> resourceEntities = new LinkedList<ResourceEntity>();
				if (location == null || location.trim().equalsIgnoreCase("location") == true)
					{
						for (String keyword : keywords)
							{
								for (CoachingClass coachingClass : searchTopRatedCoachingClassByGenericKeyword(keyword, 5))
									{
										String resourceIdentity = coachingClass.getName().trim().toLowerCase();
										if (uniqueIdentifiers.contains(resourceIdentity) == false)
											{
												uniqueIdentifiers.add(resourceIdentity);
												List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(RESOURCE_TYPE.COACHING_CLASS.getType(), resourceIdentity, 5);
												ResourceEntity resourceEntity = new ResourceEntity(coachingClass, reviews);
												List<String> resultCriterias = new ArrayList<String>();
												resultCriterias.add("rating");
												resultCriterias.add(criteria);
												resourceEntity.setResultCriterias(resultCriterias);
												resourceEntities.add(resourceEntity);
											}
									}
							}
					}
				else
					{
						for (String keyword : keywords)
							{
								List<ResultRow> resultRows = findCoachingClassForLocality(location, keyword, radius);
								for (ResultRow resultRow : resultRows)
									{
										String uniqueKey = resultRow.getName().trim().toLowerCase();
										if (uniqueIdentifiers.contains(uniqueKey) == false)
											{
												uniqueIdentifiers.add(uniqueKey);
												List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(RESOURCE_TYPE.COACHING_CLASS.getType(), uniqueKey, 5);
												ResourceEntity resourceEntity = new ResourceEntity(resultRow, reviews);
												List<String> resultCriterias = new ArrayList<String>();
												resultCriterias.add(criteria);
												resultCriterias.add("rating");
												resultCriterias.add("location");
												resourceEntity.setResultCriterias(resultCriterias);
												resourceEntities.add(resourceEntity);
											}
									}
							}
					}
				return resourceEntities;
			}
			
		public List<ResourceEntity> findCoachingClassesForUserPreference(String userId, String location, Double radius)
			{
				Set<String> keywords = userService.getKeywordFromUserProfile(userId);
				return findCoachingClassesByCriteria(userId, keywords, "profile", location, radius);
			}
			
		public List<ResourceEntity> findCoachingClassesForRating(String userId, String location, Double radius)
			{
				Set<String> keywords = new HashSet<String>();
				keywords.add("None");
				return findCoachingClassesByCriteria(userId, keywords, "rating", location, radius);
			}
			
	}
