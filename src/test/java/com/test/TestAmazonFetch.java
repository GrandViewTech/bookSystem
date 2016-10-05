package com.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2.booksys.common.util.AmazonClient;
import com.v2.booksys.common.util.AmazonClient1;
import com.v2.booksys.common.util.SignedRequestsHelper;
import com.v2.booksys.common.util.UtilService;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.Subject;
import com.v2tech.services.BookService;
import com.v2tech.webservices.AmazonWebService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestAmazonFetch {
	static org.slf4j.Logger logger = LoggerFactory.getLogger(AmazonClient.class);
	private static final String SECRET_KEY = "4QhAQO5rDHqjYQRDjvgDg/GR7r038zpX3d2ZngvW";
    private static final String AWS_KEY = "AKIAIHYXAS2JB4ND2N6A";
    private static final String ASSOCIATE_TAG = "academ058-20";
    static SignedRequestsHelper helper = null;
    static BookService bookService = null;
    static int count = 0;
    
	@Autowired
	AmazonWebService amazonWebService;
    
	@Test
	public void testITypeSubjectDataFromAmazon() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:appContext.xml");
    	bookService = (BookService) ctx.getBean("bookService");
    	helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
        Map<String, String> params = new HashMap<String, String>();
        params.put("Version", "2011-08-01");
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", AWS_KEY);
        params.put("AssociateTag", ASSOCIATE_TAG);
        params.put("SearchIndex", "Books");
        params.put("Sort", "price");
        params.put("ResponseGroup", "ItemAttributes, Images");
        params.put("Availability", "Available");
        params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
        Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
        AmazonClient1.fetchByInstitutionTypeSubject(map2, params);
	}
	
	@Test
	public void testCareerStreamSubjectDataFromAmazon() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:appContext.xml");
    	bookService = (BookService) ctx.getBean("bookService");
    	helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
        Map<String, String> params = new HashMap<String, String>();
        params.put("Version", "2011-08-01");
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", AWS_KEY);
        params.put("AssociateTag", ASSOCIATE_TAG);
        params.put("SearchIndex", "Books");
        params.put("Sort", "price");
        params.put("ResponseGroup", "ItemAttributes, Images");
        params.put("Availability", "Available");
        params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
       // Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
        Map<CareerStream,Subject> map = UtilService.getCareerStreamSubjectKeywords();
        AmazonClient1.fetchByCareerStreamSubject(map, params);
        
	}
	
	//getSubjectExamKeywords
	@Test
	public void testSubjectExamDataFromAmazon() throws Exception{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:appContext.xml");
    	bookService = (BookService) ctx.getBean("bookService");
    	helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
        Map<String, String> params = new HashMap<String, String>();
        params.put("Version", "2011-08-01");
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", AWS_KEY);
        params.put("AssociateTag", ASSOCIATE_TAG);
        params.put("SearchIndex", "Books");
        params.put("Sort", "price");
        params.put("ResponseGroup", "ItemAttributes, Images");
        params.put("Availability", "Available");
        params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
       // Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
        Map<Subject, Exam> map = UtilService.getSubjectExamKeywords();
        AmazonClient1.fetchBySubjectExam(map, params);
        
	}
	


	@Test
	public void testFetchDataFromAmazon(){
		amazonWebService.fetchDataFromAmazon("test");
	}
}
