package com.v2tech.webservices;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2.booksys.common.util.EmailHtmlThread;
import com.v2.booksys.common.util.EmailThread;
import com.v2.booksys.common.util.UtilService;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Book;
import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.DigitalTool;
import com.v2tech.domain.Keyword;
import com.v2tech.domain.KeywordEntity;
import com.v2tech.domain.RESOURCE_TYPE;
import com.v2tech.domain.Review;
import com.v2tech.domain.SocialMediaType;
import com.v2tech.domain.User;
import com.v2tech.domain.UserType;
import com.v2tech.domain.util.ResourceEntity;
import com.v2tech.domain.util.ResultRow;
import com.v2tech.domain.util.SearchList;
import com.v2tech.domain.util.ServiceResponse;
import com.v2tech.services.BookService;
import com.v2tech.services.CoachingClassService1;
import com.v2tech.services.CountryStateCityService;
import com.v2tech.services.DigitalToolService;
import com.v2tech.services.KeywordService;
import com.v2tech.services.ReviewService;
import com.v2tech.services.UserKeywordRelationService;
import com.v2tech.services.UserService;

@Path("/resourceService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ResourceWebService
	{
		@Autowired
		UserService					userService;
		
		@Autowired
		ReviewService				reviewService;
		
		@Autowired
		KeywordService				keywordService;
		
		@Autowired
		BookService					bookService;
		
		@Autowired
		CoachingClassService1		coachingClassService;
		
		@Autowired
		UserKeywordRelationService	userKeywordRelationService;
		
		@Autowired
		CountryStateCityService		countryStateCityService;
		
		DateFormat					dateFormat	= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		@Autowired
		DigitalToolService			digitalToolService;
		
		@PostConstruct
		public void init()
			{
				User user = new User();
				user.setUserType(UserType.ADMIN);
				user.setUser("grovenue.user@gmail.com");
				user.setPassword("grovenue@123");
				user.setValidated(true);
				userService.saveOrUpdate(user);
			}
			
		@Autowired
		public com.v2.booksys.common.util.InviteFriendsEmailService inviteFriendsEmailService;
		
		@GET
		@Path("/deleteUser")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteUser(@QueryParam("userId") String userId)
			{
				System.out.println("userId " + userId);
				userService.deleteUser(userId);
				return Response.ok().build();
			}
			
		@POST
		@Path("/updatePassword/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response changePAssword(User user, @PathParam("token") String token)
			{
				userService.updatePassword(user);
				return Response.ok().build();
			}
			
		@POST
		@Path("/saveOrUpdateUser/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response saveOrUpdateUser(User user, @PathParam("token") String token)
			{
				ServiceResponse response = new ServiceResponse();
				if (user == null)
					{
						throw new V2GenericException("Code-NullUserPassed,Msg-User can not be null");
					}
					
				if ((user.getUser() != null) && (user.getUser().trim().length() == 0))
					{
						throw new V2GenericException("Code-InvalidUserNamePassed,Msg-User can not be blank");
					}
					
				if ((user.getBirthDate() != null) && (user.getBirthDate().trim().length() > 8))
					{
						DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
						try
							{
								Date date = format.parse(user.getBirthDate());
								user.setBirthday(date);
							}
						catch (ParseException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new V2GenericException("Code-InvalidBirthDate,Msg-User Birth Date is invalid. Use dd/MM/yyyy format to enter date value");
							}
					}
				User user1 = userService.getSingleUserBySocialMediaType(user);
				if (user1 != null)
					{
						//throw new V2GenericException("Code-UserAlreadyExists");
						//verify the token to see if it is indeed that user who wants to update his personal info
						
						//user.setLastModifiedDate(updatedDate);
						
						if (user1.getSocialMediaType().getType().equalsIgnoreCase(SocialMediaType.NONE.getType()))
							{
								//Date updatedDate = new Date();
								userService.saveOrUpdate(user);
								response.setRequestType("User_Save_Request");
								response.setResponseStatus("User_Exists_Normal");
								return Response.ok().entity(response).build();
							}
						else
							{
								/*
								 * User already exists as a result of social media login earlier.
								 */
								/**
								 * Am commenting this on 14 june because i don't think there should be 2 users corresponding to same person - 1 created through Social media login and another created through user registration.
								 */
								response.setRequestType("User_Save_Request");
								response.setResponseStatus("User_Exists_Social_Media_" + user.getSocialMediaType().getType());
								return Response.ok().entity(response).build();
							}
							
					}
				else
					{
						boolean isSocialMediaUser = user.isSocialMedia();
						Date createdDate = new Date();
						user.setCreatedDate(createdDate);
						user.setValidated((isSocialMediaUser == true) ? true : false);
						userService.saveOrUpdate(user);
						if (isSocialMediaUser == false)
							{
								Thread thread = new Thread(new EmailHtmlThread(user));
								thread.start();
							}
					}
				response.setRequestType("User_Save_Request");
				response.setResponseStatus("User_Saved");
				//Response.ok().e
				return Response.ok().entity(response).build();
			}
			
		@GET
		@Path("/user/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public User getUserFormat()
			{
				User user = new User();
				return user;
			}
			
		@GET
		@Path("/careerStreams/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public String[] getCareerStreams(@PathParam("token") String token)
			{
				String carStreams[] = new String[] { "Engineering", "Medical (MBBS)", "Pharmacy", "Nursing", "Architecture", "Law", "Design", "Hospitality", "Management / Business", "Journalism / Media", "Other", "Other Commerce", "Other Science", "Other Medical", "Other" };
				return carStreams;
			}
			
		@POST
		@Path("/authenticate/user/{user}/password/{password}/socialMediaType/{socialMediaType}")
		public Response authenticate(@PathParam("user") String user, @PathParam("password") String password, @PathParam("socialMediaType") String socialMediaType)
			{
				try
					{
						SocialMediaType mediaType = SocialMediaType.valueOf(socialMediaType);
						if (mediaType == null)
							{
								return Response.status(Status.BAD_REQUEST).entity("Invalid Social Media Type " + socialMediaType).build();
							}
							
						User usr = userService.getSingleUserBySocialMediaType(new User(user, SocialMediaType.valueOf(socialMediaType)));
						if ((usr != null) && usr.getPassword().equals(password))
							{
								//return Response.ok().entity("Token-"+System.currentTimeMillis()).build();
								return Response.ok().entity(usr).build();
							}
						return Response.status(Status.BAD_REQUEST).entity("Invalid Credentials").build();
					}
				catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return Response.status(Status.SERVICE_UNAVAILABLE).entity("Invalid Credentials").build();
					}
			}
			
		@POST
		@Path("/verifyUser/user/{user}/socialMediaType/{socialMediaType}/token/{token}")
		public String verifyUser(@PathParam("user") String user, @PathParam("socialMediaType") String socialMediaType, @PathParam("token") String token)
			{
				try
					{
						SocialMediaType mediaType = SocialMediaType.valueOf(socialMediaType);
						if (mediaType == null)
							{
								return "Code-InvalidMediaTypeSent";
							}
							
						byte[] verCode = Base64.decodeBase64(user.getBytes());
						String actualUser = new String(verCode);
						
						User usr = userService.getSingleUserBySocialMediaType(new User(user, SocialMediaType.valueOf(socialMediaType)));
						if (usr.isValidated())
							{
								return "Code-UserAlreadyValidated";
							}
						usr.setValidated(true);
						userService.saveOrUpdate(usr);
						return "Code-Success";
					}
				catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "Code-UserValidationFailed";
					}
			}
			
		@POST
		@Path("/addFriendsToUser/user/{user}/socialMediaType/{socialMediaType}/token/{token}")
		public String addFriendsToUser(Set<User> users, @PathParam("user") String user, @PathParam("socialMediaType") String socialMediaType, @PathParam("token") String token)
			{
				try
					{
						SocialMediaType mediaType = SocialMediaType.valueOf(socialMediaType);
						if (mediaType == null)
							{
								return "Code-InvalidMediaTypeSent";
							}
						User user1 = new User(user, mediaType);
						userService.addFriendsToUser(user1, users);
						
						return "Code-Success";
					}
				catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "Code-AddFriendsFailed";
					}
			}
			
		@GET
		@Path("/retrievePassword/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public boolean retrievePassword(@PathParam("user") String user, @PathParam("token") String token)
			{
				if (user == null)
					{
						throw new V2GenericException("Code-NullUserPassed,Msg-User can not be null");
					}
					
				if (user.trim().length() == 0)
					{
						throw new V2GenericException("Code-InvalidUserNamePassed,Msg-User can not be blank");
					}
					
				User user1 = userService.getSingleUserBySocialMediaType(new User(user, SocialMediaType.NONE));
				if (user1 == null)
					{
						throw new V2GenericException("Code-UserNotExisting,Msg-User does not exist");
					}
					
				String sentMail = UtilService.getValue("sentMail");
				if ((sentMail != null) && sentMail.equalsIgnoreCase("true"))
					{
						Date createdDate = user1.getCreatedDate();
						String dateTime = dateFormat.format(createdDate);
						Thread thread = new Thread(new EmailThread(user, dateTime, true, user1.getPassword(), null));
						thread.start();
					}
				return true;
			}
			
		@GET
		@Path("/autoComplete/start/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<Keyword> getBooksStartsWith(@PathParam("user") String user, @PathParam("token") String token)
			{
				return keywordService.getAutowireKeywords("a");
			}
			
		@GET
		@Path("/autoComplete/contains/text/{text}/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<Keyword> getBooksContains(@PathParam("text") String text, @PathParam("user") String user, @PathParam("token") String token)
			{
				return keywordService.getAutowireKeywordsContains(text);
			}
			
		@GET
		@Path("/autoCompleteText/start/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<Keyword> getBooksTextStartsWith(@PathParam("user") String user, @PathParam("token") String token)
			{
				return keywordService.getAutowireKeywords("a");
			}
			
		@GET
		@Path("/autoCompleteText/contains/text/{text}/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<Keyword> getBooksTextContains(@PathParam("text") String text, @PathParam("user") String user, @PathParam("token") String token)
			{
				return keywordService.getAutowireKeywordsContains(text);
			}
			
		/**
		 * <br>
		 *
		 * @Author : puneetsharma <br>
		 * @createdDate : 05-Jul-2016 <br>
		 * @createdTime : 12:37:01 pm <br>
		 * @methodName : getUserByUserNameAndSocialMediaType <br>
		 * @methodPackage : com.v2tech.webservices <br>
		 * @Description : <br>
		 * @StepsWithExplanation : <br>
		 * @TODO : <br>
		 * @param socialMediaType
		 *            <br>
		 * @param token
		 *            <br>
		 * @param emailAddress
		 *            <br>
		 * @return
		 */
		@GET
		@Path("/getUserByEmailAddressAndSocialMediaType/socialMediaType/{socialMediaType}/token/{token}")
		public User getUserByUserNameAndSocialMediaType(@PathParam("socialMediaType") String socialMediaType, @PathParam("token") String token, @QueryParam("emailAddress") String emailAddress)
			{
				User user = userService.getUserByUserNameAndSocialMediaType(emailAddress, socialMediaType);
				if (user != null)
					{
						user.setFriends(new HashSet<User>());
					}
				return user;
			}
			
		/**
		 * <br>
		 *
		 * @Author : puneetsharma <br>
		 * @createdDate : 05-Jul-2016 <br>
		 * @createdTime : 12:37:08 pm <br>
		 * @methodName : inviteFriendThroughSocialMedia <br>
		 * @methodPackage : com.v2tech.webservices <br>
		 * @Description : <br>
		 * @StepsWithExplanation : <br>
		 * @TODO : <br>
		 * @param socialMediaType
		 *            <br>
		 * @param userId
		 *            <br>
		 * @param emailObject
		 */
		@POST
		@Path("inviteFriendThroughSocialMedia/socialMediaType/{socialMediaType}/token/{token}")
		public void inviteFriendThroughSocialMedia(@PathParam("socialMediaType") String socialMediaType, @QueryParam("userId") String userId, Map<String, Object> emailObject)
			{
				if ((emailObject == null) || (emailObject.size() < 2))
					{
						return;
					}
				List<String> emailAddresses = (ArrayList<String>) emailObject.get("emailAddresses");
				String invitationMessage = (String) emailObject.get("invitationMessage");
				User user = userService.getUserByUserNameAndSocialMediaType(userId, socialMediaType);
				inviteFriendsEmailService.inviteFriendsViaEmail(user.getFullName(), invitationMessage, emailAddresses);
			}
			
		/**
		 * <br>
		 *
		 * @Author : puneetsharma <br>
		 * @createdDate : 05-Jul-2016 <br>
		 * @createdTime : 12:37:17 pm <br>
		 * @methodName : addFriendsFromSocialMedia <br>
		 * @methodPackage : com.v2tech.webservices <br>
		 * @Description : <br>
		 * @StepsWithExplanation : <br>
		 * @TODO : <br>
		 * @param socialMediaType
		 *            <br>
		 * @param userId
		 *            <br>
		 * @param socialMediaFriends
		 *            <br>
		 * @return
		 */
		@POST
		@Path("addFriendsFromSocialMedia/socialMediaType/{socialMediaType}/token/{token}")
		public List<Map<String, String>> addFriendsFromSocialMedia(@PathParam("socialMediaType") String socialMediaType, @QueryParam("userId") String userId, List<Map<String, String>> socialMediaFriends)
			{
				List<Map<String, String>> nonSystemUser = new ArrayList<Map<String, String>>();
				if ((socialMediaFriends == null) || (socialMediaFriends.size() < 2))
					{
						return nonSystemUser;
					}
				User user = userService.getUserByUserNameAndSocialMediaType(userId, socialMediaType);
				Set<User> systemUser = new HashSet<User>();
				List<String> tempEmailIds = new ArrayList<>();
				for (Map<String, String> socialMediaFriend : socialMediaFriends)
					{
						String email = socialMediaFriend.get("email");
						if (email != null)
							{
								if (tempEmailIds.contains(email) == false)
									{
										Set<User> _systemUsers = userService.findUsersByUserName(email);
										if ((_systemUsers != null) && (_systemUsers.size() > 0))
											{
												systemUser.addAll(_systemUsers);
											}
										else
											{
												nonSystemUser.add(socialMediaFriend);
											}
										tempEmailIds.add(email);
									}
							}
					}
				userService.addFriendsToUser(user, systemUser);
				return nonSystemUser;
			}
			
		@GET
		@Consumes("application/json")
		@Produces("application/json")
		@Path("getResourceEntityBasedByUponSearchCriteria/token/{token}")
		public List<ResourceEntity> getResourceEntityBasedByUponSearchCriteria(@DefaultValue("anonymous") @QueryParam("userId") String userId, @DefaultValue("None") @QueryParam("keyword") String keyword, @DefaultValue("generic") @QueryParam("searchType") String searchType, @DefaultValue("book") @QueryParam("searchEntity") String searchEntity, @DefaultValue("location") @QueryParam("location") String location, @DefaultValue("20") @QueryParam("resourceLimit") Integer resourceLimit)
			{
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				if ((userId == null) || (userId.trim().length() == 0))
					{
						searchType = "generic";
						userId = "annonymous";
					}
				switch (searchEntity)
					{
						case "book":
							{
								if (keyword.trim().equalsIgnoreCase("None"))
									{
										if (searchType.equalsIgnoreCase("preference"))
											{
												resourceEntities.addAll(bookService.findBooksForUserPreference(userId));
											}
										else if (searchType.equalsIgnoreCase("friends"))
											{
												resourceEntities.addAll(bookService.findBooksForUserFriends(userId));
											}
										else if (searchType.equalsIgnoreCase("rating"))
											{
												resourceEntities.addAll(bookService.findBooksForTopRating(userId, resourceLimit));
											}
									}
								else
									{
										resourceEntities.addAll(bookService.userDefinedSearch(userId, keyword));
									}
								break;
							}
						case "coachingClass":
							{
								if (keyword.trim().equalsIgnoreCase("None"))
									{
										if (searchType.equalsIgnoreCase("preference"))
											{
												resourceEntities.addAll(coachingClassService.findCoachingClassesForUserPreference(userId, location, 5.0));
											}
										else if (searchType.equalsIgnoreCase("friends"))
											{
												resourceEntities.addAll(coachingClassService.findBooksForUserFriends(userId, location, 5.0));
											}
										else if (searchType.equalsIgnoreCase("rating"))
											{
												resourceEntities.addAll(coachingClassService.findCoachingClassesForRating(userId, location, 5.0));
											}
									}
								else
									{
										Set<String> coachingClassKeywords = new LinkedHashSet<String>();
										coachingClassKeywords.add(keyword);
										resourceEntities.addAll(coachingClassService.findCoachingClassesByCriteria(userId, coachingClassKeywords, "rating", location, 5.0));
										userKeywordRelationService.increaseSearchTermCounterForUser(userId, keyword);
									}
								break;
							}
						case "digitalResource":
							{
								if (keyword.trim().equalsIgnoreCase("None"))
									{
										if (searchType.equalsIgnoreCase("preference"))
											{
												resourceEntities.addAll(digitalToolService.findDigitalResourceForUserPreference(userId));
											}
										else if (searchType.equalsIgnoreCase("friends"))
											{
												resourceEntities.addAll(digitalToolService.findDigitalResourceForFriends(userId));
											}
										else if (searchType.equalsIgnoreCase("rating"))
											{
												resourceEntities.addAll(digitalToolService.searchTopRatedDigitalTool(resourceLimit));
											}
									}
								else
									{
										
										resourceEntities.addAll(digitalToolService.searchTopRatedDigitalToolByKeyword(keyword, resourceLimit));
										userKeywordRelationService.increaseSearchTermCounterForUser(userId, keyword);
									}
									
								break;
							}
					}
					
				if (resourceEntities.size() > resourceLimit)
					{
						return resourceEntities.subList(0, resourceLimit);
					}
				return resourceEntities;
			}
			
		@GET
		@Consumes("application/json")
		@Produces("application/json")
		@Path("findByDistinctStateAndCityForGivenCountry/token/{token}")
		public Map<String, List<String>> findByDistinctStateAndCityForGivenCountry(@PathParam("token") String token, @DefaultValue("India") @QueryParam("country") String country)
			{
				return countryStateCityService.findByDistinctStateAndCityForGivenCountry(country);
			}
			
	}
