package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.v2tech.services.BookService;
import com.v2tech.services.CoachingClassService1;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:appContext.xml" })
public class TestSearch
	{
		@Autowired
		private BookService				bookService;
		
		@Autowired
		private CoachingClassService1	coachingClassService;
		
		@Test
		public void findBookByVariousCriteria()
			{
				Integer limit = 10;
				String userId = "sharmapuneet1510@gmail.com";
				String loaction = "Mulund";
				Double radius = 5.0;
				LoggerUtil.log("testBooksByPublicationYear", bookService.findAllBooksByRecentPublicationYear(limit));
				LoggerUtil.log("findBooksByUserProfile", bookService.findBooksByUserProfile(userId));
				LoggerUtil.log("findCoachingClassesUserProfile", coachingClassService.findCoachingClassesUserProfile(loaction, radius, userId));
				
			}
	}
