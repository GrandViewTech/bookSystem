package com.v2tech.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Book;
import com.v2tech.domain.Rating;
import com.v2tech.domain.User;
import com.v2tech.repository.BookRepository;
import com.v2tech.repository.RatingRepository;
import com.v2tech.repository.UserRepository;

@Service
@Transactional
public class UserService
	{
		@Autowired
		UserRepository		userRepository;
		
		@Autowired
		RatingRepository	ratingRepository;
		
		@Autowired
		BookRepository		bookRepository;
		
		@Autowired
		BookService			bookService;
		
		@Autowired
		Neo4jTemplate		template;
		
		public void updatePassword(User user)
			{
				java.util.Set<User> users = userRepository.findUserByNameAndSocialMediaType(user.getUser(), user.getSocialMediaType().getType());
				if (users.size() == 0)
					{
						
					}
				else if (users.size() == 1)
					{
						User usrs[] = new User[users.size()];
						User existingUser = (users.toArray(usrs))[0];
						existingUser.setPassword(user.getNewPassword());
						userRepository.save(existingUser);
					}
			}
			
		/**
		 * This method will do following - a. In create mode - Will update the primary attributes of User object and save only those friends that already exist in the system as users b. In the update mode - Will update only the primary attributes of
		 * User object. So no Rating/Friend collections will be updated.
		 *
		 * @param user
		 * @return
		 */
		@Transactional
		public User saveOrUpdate(User user)
			{
				boolean isSocialMedia = user.isSocialMedia();
				if ((user.getUser() == null))
					{
						throw new V2GenericException("User name cannot be null");
					}
				user.setUser(user.getUser().trim());
				if (isSocialMedia == false)
					{
						if ((user.getPassword() == null))
							{
								throw new V2GenericException("Password cannot be null");
							}
						user.setPassword(user.getPassword().trim());
						if (user.getPassword().length() < 3)
							{
								throw new V2GenericException("Too small Password");
							}
					}
				if (user.getUser().length() < 3)
					{
						throw new V2GenericException("Too small User Name");
					}
					
				java.util.Set<User> users = userRepository.findUserByNameAndSocialMediaType(user.getUser(), user.getSocialMediaType().getType());
				if (users.size() == 0)
					{
						//create mode
						Set<User> friends = user.getFriends();
						friends = findFriendsExistingInSystem(friends);
						user.setCreatedDate(new Date());
						user.setFriends(friends);
						userRepository.save(user);
						return user;
					}
				else if (users.size() > 1)
					{
						throw new V2GenericException("More than 1 user " + user.getUser() + " exists");
					}
				else
					{
						//update mode
						User usrs[] = new User[users.size()];
						User existingUser = (users.toArray(usrs))[0];
						
						existingUser.setBase64Image(user.getBase64Image());
						existingUser.setPassword(user.getPassword());
						existingUser.setAge(user.getAge());
						existingUser.setFullName(user.getFullName());
						existingUser.setAlternativeEmail(user.getAlternativeEmail());
						existingUser.setBirthday(user.getBirthday());
						existingUser.setBirthDate(user.getBirthDate());
						existingUser.setCareerStream(user.getCareerStream());
						existingUser.setContactListForInvitingFriends(user.getContactListForInvitingFriends());
						existingUser.setDreamOccupation(user.getDreamOccupation());
						existingUser.setExamInterestedIn(user.getExamInterestedIn());
						existingUser.setFirstName(user.getFirstName());
						existingUser.setLastName(user.getLastName());
						existingUser.setFunAndRelaxation(user.getFunAndRelaxation());
						existingUser.setGrade(user.getGrade());
						existingUser.setInstitution(user.getInstitution());
						existingUser.setCourseCompletionYear(user.getCourseCompletionYear());
						existingUser.setPicture(user.getPicture());
						existingUser.setSocialMedia(user.isSocialMedia());
						existingUser.setSocialMediaType(user.getSocialMediaType());
						existingUser.setExpectedDateOfExams(user.getExpectedDateOfExams());
						existingUser.setState(user.getState());
						existingUser.setCity(user.getCity());
						existingUser.setHobbies(user.getHobbies());
						existingUser.setIntroduction(user.getIntroduction());
						existingUser.setGraduation(user.getGraduation());
						existingUser.setSpecialization(user.getSpecialization());
						existingUser.setUniversity(user.getUniversity());
						existingUser.setStatus(user.getStatus());
						existingUser.setUserInvolvementType(user.getUserInvolvementType());
						existingUser.setUserType(user.getUserType());
						existingUser.setValidated(user.isValidated());
						userRepository.save(existingUser);
						return existingUser;
					}
			}
			
		private Set<User> findFriendsExistingInSystem(java.util.Set<User> users)
			{
				Set<User> existingFriends = new HashSet<>();
				if (users == null)
					{
						return existingFriends;
					}
				for (User user : users)
					{
						user = getSingleUserBySocialMediaType(user);
						if (user != null)
							{
								existingFriends.add(user);
							}
							
					}
				return existingFriends;
			}
			
		public User getSingleUser(User user)
			{
				java.util.Set<User> users = userRepository.findUserByUserName(user.getUser());
				if (users.size() == 0)
					{
						return null;
					}
				else if (users.size() > 1)
					{
						throw new V2GenericException("More than user exists withsame user name " + user.getUser());
					}
				else
					{
						User usrs[] = new User[users.size()];
						users.toArray(usrs);
						return usrs[0];
					}
					
			}
			
		public User getSingleUserBySocialMediaType(User user)
			{
				java.util.Set<User> users = userRepository.findUserByNameAndSocialMediaType(user.getUser(), user.getSocialMediaType().getType());
				if (users.size() == 0)
					{
						return null;
					}
				else if (users.size() > 1)
					{
						throw new V2GenericException("More than user exists withsame user name " + user.getUser() + " and social media type " + user.getSocialMediaType().getType());
					}
				else
					{
						User usrs[] = new User[users.size()];
						users.toArray(usrs);
						return usrs[0];
					}
					
			}
			
		public boolean markUserAsValidated(User user)
			{
				user = getSingleUserBySocialMediaType(user);
				if (user == null)
					{
						throw new V2GenericException("User does not exists");
					}
				user.setValidated(true);
				saveOrUpdate(user);
				return true;
			}
			
		public void addUserRatingToBook(Rating rating)
			{
				if ((rating.getStars() == null) || (rating.getStars() < 0) || (rating.getStars() > 5))
					{
						throw new V2GenericException("Invalid Rating Stars ");
					}
					
				User user = rating.getUser();
				user = getSingleUserBySocialMediaType(user);
				if (user == null)
					{
						throw new V2GenericException("No user exists ");
					}
					
				Book book = rating.getBook();
				if ((book == null) || (book.getISBN() == null))
					{
						throw new V2GenericException("No book exists ");
					}
				book = bookService.getSingleBook(book.getISBN());
				
				rating.setUser(user);
				rating.setBook(book);
				ratingRepository.save(rating);
			}
			
		/**
		 * The method adds only those users as friends that exist in the system as users.
		 *
		 * @param user
		 * @return
		 */
		public boolean addFriendsToUser(User user, Set<User> users)
			{
				if (user.getUser() == null)
					{
						throw new V2GenericException("User name can not be null ");
					}
					
				users = findFriendsExistingInSystem(users);
				user = getSingleUserBySocialMediaType(user);
				if (user == null)
					{
						throw new V2GenericException("User is null");
					}
					
				if (user.getFriends() == null)
					{
						user.setFriends(new HashSet());
					}
				Set<User> frnds = template.fetch(user.getFriends());
				users.addAll(frnds);
				user.setFriends(users);
				userRepository.save(user);
				return true;
			}
			
		public User getUserByUserNameAndSocialMediaType(String user, String socialMediaType)
			{
				user = "(?i)" + user.trim();
				socialMediaType = socialMediaType.trim().toUpperCase();
				Set<User> users = userRepository.findUserByNameAndSocialMediaType(user, socialMediaType);
				for (User usr : users)
					{
						return usr;
					}
				return null;
			}
			
		public Set<User> findUsersByUserName(String user)
			{
				user = "(?i)" + user.trim();
				Set<User> users = userRepository.findUserByUserName(user);
				return users;
			}
			
		public void deleteUser(String userId)
			{
				Set<User> users = userRepository.findUserByUserName(userId);
				if (users != null && users.size() > 0)
					{
						userRepository.delete(users);
					}
			}
	}
