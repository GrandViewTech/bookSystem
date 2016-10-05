package com.test;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.Course;
import com.v2tech.domain.CourseType;
import com.v2tech.domain.RESOURCE_TYPE;
import com.v2tech.domain.ResourceUnderReview;
import com.v2tech.domain.Review;
import com.v2tech.repository.ResourceUnderReviewRepository;
import com.v2tech.services.CourseService;
import com.v2tech.services.ReviewService;
import com.v2tech.webservices.ReviewRelatedWebService;
import com.v2tech.webservices.UtilWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestUtil {
	
	

	@Autowired
	UtilWebService utilWebService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	ReviewRelatedWebService reviewRelatedWebService;
	
	@Autowired
	ResourceUnderReviewRepository resourceUnderReviewRepository;
	
	@Test
	public void testTopicsBySubject(){
		List<String> topics = (List<String>) utilWebService.getTopicsForSubject("maths", "").getEntity();
		for(String topic: topics){
			System.out.println(topic);
		}
	}
	
	@Test
	public void testGetResourceReviewWithSpecialChar(){
		String name = "Title-15 JEE (Main) Physics/Chemistry/Maths Practice Sets (Practice Work Book)";
		name = "Title-15 JEE (Main) Physics/Chemistry/Maths Practice Sets (Practice Work Book)";
		//name = ReviewService.escapeSpecialCharacters(name);
		
		//String n = "Title-15 JEE \\(Main\\) Physics/Chemistry/Maths Practice Sets \\(Practice Work Book\\).*";
		ResourceUnderReview resourceUnderReview = resourceUnderReviewRepository.getResourceUnderReviewByResourceName(name);
		//resourceUnderReview = resourceUnderReviewRepository.getResourceUnderReviewByResourceName(n);
		System.out.println(resourceUnderReview);
	}
	
	@Test
	public void testCreateCourse(){
		Course c1 = new Course("MBA/PGDM", CourseType.MANAGEMENT.toString());
		Course c2 = new Course("Exceutive MBA", CourseType.MANAGEMENT.toString());
		Course c3 = new Course("MHA", CourseType.MANAGEMENT.toString());
		Course c4 = new Course("M.Phil", CourseType.MANAGEMENT.toString());
		Course c5 = new Course("PHD", CourseType.MANAGEMENT.toString());
		Course c6 = new Course("BBM/BMS", CourseType.MANAGEMENT.toString());
		
		courseService.saveOrUpdate(c1);
		courseService.saveOrUpdate(c2);
		courseService.saveOrUpdate(c3);
		courseService.saveOrUpdate(c4);
		courseService.saveOrUpdate(c5);
		courseService.saveOrUpdate(c6);
	}
	
	@Test
	public void testGetAllCourseTypes(){
		Response res = utilWebService.fetchAllCourseTypes();
		Object obj = res.getEntity();
	}
	
	@Test
	public void testGetAllCoursesForType(){
		Response res = utilWebService.fetchAllCoursesForType( CourseType.MANAGEMENT.toString());
		Object obj = res.getEntity();
	}
	
	@Test
	public void testCreateOrUpdateReviewForCoachingClass(){
		Review review = new Review();
//		review.setUserName("sagar@yahoo.com");
//		review.setResourceName("Mahesh Tutorials");
//		review.setResourceTitle("Gmat Cat Success: 2001 (Peterson's Gmat Cat Success (Book and CD Rom), 2001)");
//		review.setResourceLink("http://ecx.images-amazon.com/images/I/514YPKVZXFL._SL160_.jpg");
//		review.setCriteria("SSC");
//		review.setResourceType(RESOURCE_TYPE.COACHING_CLASS.getType());
//		review.setChapterNotes(5);
//		review.setCheatSheet(4);
//		//review.setChemistry(5);
//		review.setEffectivenessAndEaseOfCommunication(5);
		reviewRelatedWebService.addOrUpdateReviewRating(review, "");
		//reviewService.saveOrUpdate(review);
	}
	
	@Test
	public void testCreateOrUpdateReviewForBook(){
		Review review = new Review();
//		review.setUserName("sagar@yahoo.com");
//		review.setResourceName("ISBN-0768904099");
//		review.setResourceTitle("Gmat Cat Success: 2001 (Peterson's Gmat Cat Success (Book and CD Rom), 2001)");
//		review.setResourceLink("http://ecx.images-amazon.com/images/I/514YPKVZXFL._SL160_.jpg");
//		review.setCriteria("MBA CAT");
//		review.setResourceType(RESOURCE_TYPE.BOOK.getType());
//		review.setChapterNotes(3);
//		review.setCheatSheet(2);
//		//review.setChemistry(5);
//		review.setEffectivenessAndEaseOfCommunication(3);
		reviewRelatedWebService.addOrUpdateReviewRating(review, "");
		//reviewService.saveOrUpdate(review);
	}
	
	@Test
	public void testCreateOrUpdateReviewForCollege(){
		Review review = new Review();
//		review.setUserName("sagar@yahoo.com");
//		review.setResourceName("Sydenham Institute of Management Studies, Research and Entrepreneurship Education SIMSREE");
//		review.setResourceLink("http://www.shiksha.com/getListingDetail/26930/institute/college-Thadomal-Shahani-Centre-For-Management-Tscfm-Mumbai-India");
//		review.setCriteria("Marketing");
//		review.setResourceType(RESOURCE_TYPE.COLLEGE.getType());
//		review.setChapterNotes(3);
//		review.setCheatSheet(2);
//		//review.setChemistry(5);
//		review.setEffectivenessAndEaseOfCommunication(4);
		reviewRelatedWebService.addOrUpdateReviewRating(review, "");
		//reviewService.saveOrUpdate(review);
	}
	
	@Test
	public void testGetTopResources(){
		Set<ResourceUnderReview> resources = reviewRelatedWebService.getTopRatedResources("4", "");
		for(ResourceUnderReview resource : resources){
			System.out.println("Resource is "+resource.getResourceName()+" rating avg "+resource.getAverageRatingForResource());
		}
	}
	
	@Test
	public void testGetTopBooks(){
		Set<ResourceUnderReview> resources = reviewRelatedWebService.getTopRatedBooks("4", "");
		for(ResourceUnderReview resource : resources){
			System.out.println("Resource is "+resource.getResourceName()+" rating avg "+resource.getAverageRatingForResource());
		}
	}
	
	@Test
	public void testGetTopCoachingClasses(){
		Set<ResourceUnderReview> resources = reviewRelatedWebService.getTopRatedCoachingClasses("4","");
		for(ResourceUnderReview resource : resources){
			System.out.println("Resource is "+resource.getResourceName()+" rating avg "+resource.getAverageRatingForResource());
		}
	}
	
	@Test
	public void testGetTopCollegesByCriteria(){
		Set<ResourceUnderReview> resources = reviewRelatedWebService.getTopRatedCollegesByCriteria("Marketing", "4", "");
		for(ResourceUnderReview resource : resources){
			System.out.println("Resource is "+resource.getResourceName()+" rating avg "+resource.getAverageRatingForResource());
		}
	}
	
	@Test
	public void testGetTopBooksByCriteria(){
		Set<ResourceUnderReview> resources = reviewRelatedWebService.getTopRatedBooksByCriteria("MBA CAT", "4", "");
		for(ResourceUnderReview resource : resources){
			System.out.println("Resource is "+resource.getResourceTitle()+" rating avg "+resource.getResourceName());
		}
	}
}
