package com.v2tech.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@NodeEntity
public class User
	{
		public User()
			{
				
			}
			
		public User(String userName, String password)
			{
				this.user = userName;
				this.password = password;
			}
			
		public User(String userName)
			{
				this.user = userName;
			}
			
		public User(String userName, SocialMediaType mediaType)
			{
				this.user = userName;
				this.socialMediaType = mediaType;
			}
			
		@GraphId
		private Long			id;
		private String			fullName;
		private Integer			age;
		private String			status;
		
		private String			user							= "";
		
		private String			contact							= "";
		
		private String			password						= "";
		
		@Transient
		private String			newPassword						= "";
		
		private String			firstName						= "";
		
		private String			lastName						= "";
		
		private String			grade							= "";
		
		private String			state							= "";
		
		private String			city							= "";
		private String			institution						= "";
		
		private String			careerStream					= "";
		
		private String			examInterestedIn				= "";
		
		private String			dreamOccupation					= "";
		
		private String			funAndRelaxation				= "";
		
		private byte[]			picture;
		
		private String			base64Image;
		
		private Date			birthday;
		
		private Date			createdDate;
		
		private String			birthDate						= "";
		
		private String			contactListForInvitingFriends	= "";
		
		private String			alternativeEmail				= "";
		
		/**
		 * Type of user – rockstar, freshie etc - . – an indication of level of involvement with the site
		 */
		private String			userInvolvementType;
		
		private UserType		userType						= UserType.STUDENT;
		
		private boolean			socialMedia						= false;
		
		private SocialMediaType	socialMediaType					= SocialMediaType.NONE;
		
		private boolean			validated						= false;
		
		String					hobbies							= "";
		
		String					introduction					= "";
		
		String					university						= "";
		
		String					courseCompletionYear;
		
		String					expectedDateOfExams				= "";
		
		String					graduation						= "";
		
		String					specialization					= "";
		
		//	@RelatedTo
		//	private Address address;
		
		//	@RelatedToVia(type="Have_Rated")
		//	Set<Rating> ratings = new HashSet<>();
		@RelatedTo(type = "IS_FRIEND_OF", direction = Direction.OUTGOING)
		Set<User>				friends							= new HashSet<>();
		
		//	@RelatedTo(type = "SEARCHED", direction = Direction.OUTGOING)
		//	Set<Keyword> keywords = new HashSet<>();
		
		public String getFullName()
			{
				return getFirstName() + " " + getLastName();
			}
			
		public void setFullName(String fullName)
			{
				this.fullName = fullName;
			}
			
		public String getFirstName()
			{
				return firstName;
			}
			
		public void setFirstName(String firstName)
			{
				this.firstName = firstName;
			}
			
		public String getLastName()
			{
				return lastName;
			}
			
		public void setLastName(String lastName)
			{
				this.lastName = lastName;
			}
			
		public String getGrade()
			{
				return grade;
			}
			
		public void setGrade(String grade)
			{
				this.grade = grade;
			}
			
		public String getState()
			{
				return state;
			}
			
		public void setState(String state)
			{
				this.state = state;
			}
			
		public String getInstitution()
			{
				return institution;
			}
			
		public void setInstitution(String institution)
			{
				this.institution = institution;
			}
			
		public String getCareerStream()
			{
				return careerStream;
			}
			
		public void setCareerStream(String careerStream)
			{
				this.careerStream = careerStream;
			}
			
		public String getExamInterestedIn()
			{
				return examInterestedIn;
			}
			
		public void setExamInterestedIn(String examInterestedIn)
			{
				this.examInterestedIn = examInterestedIn;
			}
			
		public String getDreamOccupation()
			{
				return dreamOccupation;
			}
			
		public void setDreamOccupation(String dreamOccupation)
			{
				this.dreamOccupation = dreamOccupation;
			}
			
		public String getFunAndRelaxation()
			{
				return funAndRelaxation;
			}
			
		public void setFunAndRelaxation(String funAndRelaxation)
			{
				this.funAndRelaxation = funAndRelaxation;
			}
			
		public byte[] getPicture()
			{
				return picture;
			}
			
		public void setPicture(byte[] picture)
			{
				this.picture = picture;
			}
			
		public Date getBirthday()
			{
				return birthday;
			}
			
		public void setBirthday(Date birthday)
			{
				this.birthday = birthday;
			}
			
		public String getContactListForInvitingFriends()
			{
				return contactListForInvitingFriends;
			}
			
		public void setContactListForInvitingFriends(String contactListForInvitingFriends)
			{
				this.contactListForInvitingFriends = contactListForInvitingFriends;
			}
			
		public String getAlternativeEmail()
			{
				return alternativeEmail;
			}
			
		public void setAlternativeEmail(String alternativeEmail)
			{
				this.alternativeEmail = alternativeEmail;
			}
			
		public String getUserInvolvementType()
			{
				return userInvolvementType;
			}
			
		public void setUserInvolvementType(String userInvolvementType)
			{
				this.userInvolvementType = userInvolvementType;
			}
			
		public UserType getUserType()
			{
				return userType;
			}
			
		public void setUserType(UserType userType)
			{
				this.userType = userType;
			}
			
		public boolean isSocialMedia()
			{
				return socialMedia;
			}
			
		public void setSocialMedia(boolean socialMedia)
			{
				this.socialMedia = socialMedia;
			}
			
		public SocialMediaType getSocialMediaType()
			{
				return socialMediaType;
			}
			
		public void setSocialMediaType(SocialMediaType socialMediaType)
			{
				this.socialMediaType = socialMediaType;
			}
			
		public boolean isValidated()
			{
				return validated;
			}
			
		public void setValidated(boolean validated)
			{
				this.validated = validated;
			}
			
		public Integer getAge()
			{
				return age;
			}
			
		public void setAge(Integer age)
			{
				this.age = age;
			}
			
		public String getStatus()
			{
				return status;
			}
			
		public void setStatus(String status)
			{
				this.status = status;
			}
			
		public Long getId()
			{
				return id;
			}
			
		public void setId(Long id)
			{
				this.id = id;
			}
			
		//	public Address getAddress() {
		//		return address;
		//	}
		//	public void setAddress(Address address) {
		//		this.address = address;
		//	}
		public String toString()
			{
				return this.id + "" + this.fullName;
			}
			
		//	public Set<Rating> getRatings() {
		//		return ratings;
		//	}
		//	public void setRatings(Set<Rating> ratings) {
		//		this.ratings = ratings;
		//	}
		public Set<User> getFriends()
			{
				return friends;
			}
			
		public void setFriends(Set<User> friends)
			{
				this.friends = friends;
			}
			
		public String getPassword()
			{
				return password;
			}
			
		public void setPassword(String password)
			{
				this.password = password;
			}
			
		@Override
		public boolean equals(Object obj)
			{
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				if (hashCode() != obj.hashCode())
					{
						return false;
					}
				return true;
			}
			
		@Override
		public int hashCode()
			{
				if (this.user == null)
					{
						return super.hashCode();
					}
					
				if (this.user.trim().length() == 0)
					{
						return super.hashCode();
					}
				return this.user.hashCode();
			}
			
		public String getUser()
			{
				return user;
			}
			
		public void setUser(String user)
			{
				this.user = user;
			}
			
		public String getBirthDate()
			{
				return birthDate;
			}
			
		public void setBirthDate(String birthDate)
			{
				this.birthDate = birthDate;
			}
			
		public Date getCreatedDate()
			{
				return createdDate;
			}
			
		public void setCreatedDate(Date createdDate)
			{
				this.createdDate = createdDate;
			}
			
		public String getContact()
			{
				return contact;
			}
			
		public void setContact(String contact)
			{
				this.contact = contact;
			}
			
		public String getCity()
			{
				return city;
			}
			
		public void setCity(String city)
			{
				this.city = city;
			}
			
		public String getHobbies()
			{
				return hobbies;
			}
			
		public void setHobbies(String hobbies)
			{
				this.hobbies = hobbies;
			}
			
		public String getIntroduction()
			{
				return introduction;
			}
			
		public void setIntroduction(String introduction)
			{
				this.introduction = introduction;
			}
			
		public String getUniversity()
			{
				return university;
			}
			
		public void setUniversity(String university)
			{
				this.university = university;
			}
			
		public String getCourseCompletionYear()
			{
				return courseCompletionYear;
			}
			
		public void setCourseCompletionYear(String courseCompletionYear)
			{
				this.courseCompletionYear = courseCompletionYear;
			}
			
		public String getExpectedDateOfExams()
			{
				return expectedDateOfExams;
			}
			
		public void setExpectedDateOfExams(String expectedDateOfExams)
			{
				this.expectedDateOfExams = expectedDateOfExams;
			}
			
		public String getGraduation()
			{
				return graduation;
			}
			
		public void setGraduation(String graduation)
			{
				this.graduation = graduation;
			}
			
		public String getSpecialization()
			{
				return specialization;
			}
			
		public void setSpecialization(String specialization)
			{
				this.specialization = specialization;
			}
			
		public String getNewPassword()
			{
				return newPassword;
			}
			
		public void setNewPassword(String newPassword)
			{
				this.newPassword = newPassword;
			}
			
		public String getBase64Image()
			{
				return base64Image;
			}
			
		public void setBase64Image(String base64Image)
			{
				this.base64Image = base64Image;
			}
			
	}
