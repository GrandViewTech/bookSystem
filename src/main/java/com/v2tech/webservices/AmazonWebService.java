package com.v2tech.webservices;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentFactory;
import org.dom4j.Node;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.v2.booksys.common.util.AmazonClient;
import com.v2.booksys.common.util.AmazonClient1;
import com.v2.booksys.common.util.SignedRequestsHelper;
import com.v2.booksys.common.util.UtilService;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.Subject;
import com.v2tech.services.BookService;

@Path("/amazonService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class AmazonWebService {
static org.slf4j.Logger logger = LoggerFactory.getLogger(AmazonClient.class);
//private static final String SECRET_KEY = "4QhAQO5rDHqjYQRDjvgDg/GR7r038zpX3d2ZngvW";
//private static final String AWS_KEY = "AKIAIHYXAS2JB4ND2N6A";
//private static final String ASSOCIATE_TAG = "academ058-20";

private static final String SECRET_KEY = "JY4SoaN+PGjFOF8fdVPvPmwaZvLEyq0YN4pXDNTb";
private static final String AWS_KEY = "AKIAIWJR2CYTIJG6FIBA";
private static final String ASSOCIATE_TAG = "httpgrovencom-21";

@Autowired
BookService bookService;  

	@POST
	@Path("/download/amazon/data/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean fetchDataFromAmazon(@PathParam("token") String token){
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
		} catch (InvalidKeyException | IllegalArgumentException
				| UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new V2GenericException("Code-AmazonKeyFailed,Msg-Serious Error in communicating with Amazon. Contact Admin!");
		}
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
        //params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
       // params.put("Keyword", "Test");
        
        List<String> keywords = new ArrayList<>();
		try {
			keywords = FileUtils.readLines(new File("BookSearchKeyWords.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new V2GenericException("Code-KeywordFileNotRead,Msg-Serious Error in communicating in reading criteria file. Contact Admin!");
		}

        for(String keyword : keywords){
        	try {
        		params.put("Keywords", keyword);
        		String url = helper.sign(params);
        		HashMap map = new HashMap();
              	String uri = "http://webservices.amazon.com/AWSECommerceService/2011-08-01";
              	map.put( "aws", uri);
              	DocumentFactory.getInstance().setXPathNamespaceURIs( map);
              	/**
              	 * Get total number of pages got for a search result
              	 */
            	org.dom4j.Document document = AmazonClient.retrieveDocumentResponse(url, map);
            	/**
            	 * Since amazon has a restriction on contininuous hitting.
            	 */
            		try{
            			Thread.sleep(2500);
            		}
            		catch(Exception e){
            			
            		}

            	Node nodeP = document.selectSingleNode("//aws:Items");
            	Node totalPages = nodeP.selectSingleNode("//aws:TotalPages");
            		if(totalPages != null){
            			if(totalPages.getText() != null){
            					if(totalPages.getText() == null){
            						System.out.println("Something wrong no total pages got");
            					}
            				System.out.println("Total PAges "+totalPages.getText());
            				int totPages = Integer.parseInt(totalPages.getText());
            				int pages = (totPages > 10) ? 10: totPages;
            				/**
                          	 * For every page got, retrieve the individual items/books and save them in d/b.
                          	 */
            				for(int page = 1; page <= pages; page++ ){
            					params.put("ItemPage", ""+page);
            					url = helper.sign(params);
            					document = AmazonClient.retrieveDocumentResponse(url, map);
            					nodeP = document.selectSingleNode("//aws:Items");
            					AmazonClient.convertPageResultsIntoBooks(nodeP, map, bookService, keyword);
            				}
            			}
            			else{
            				System.out.println("No search yielded for above keyword");
            			}
            			
            		}
            //List<Book> books =	convertPageResultsIntoBooks(nodeP, map);	
            		      	
            } catch (Exception ex) {
            	ex.printStackTrace();
            	logger.debug("Problem while retrieving result from amazon for keyword "+keyword, ex);
            }
        }
		
		
		return true;
		
	}
	

	@POST
	@Path("/fetch/amazon/allData/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void fetchAmazonDataAll() throws InvalidKeyException, IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException{
		SignedRequestsHelper helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
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
        //params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
       // params.put("Keyword", "Test");
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
        
        Map<CareerStream,Subject> map = UtilService.getCareerStreamSubjectKeywords();
        AmazonClient1.fetchByCareerStreamSubject(map, params);
        
        Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
        AmazonClient1.fetchByInstitutionTypeSubject(map2, params);
        
        Map<Subject, Exam> map3 = UtilService.getSubjectExamKeywords();
        AmazonClient1.fetchBySubjectExam(map3, params);
	}
	
	@POST
	@Path("/fetch/amazon/careerSreams/subjects/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void fetchAmazonDataCareerStreamAndSubjects() throws InvalidKeyException, IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException{
		SignedRequestsHelper helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
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
        //params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
       // params.put("Keyword", "Test");
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
        Map<CareerStream,Subject> map = UtilService.getCareerStreamSubjectKeywords();
        AmazonClient1.fetchByCareerStreamSubject(map, params);
        
//        Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
//        AmazonClient1.fetchByInstitutionTypeSubject(map2, params);
//        
//        Map<Subject, Exam> map3 = UtilService.getSubjectExamKeywords();
//        AmazonClient1.fetchBySubjectExam(map3, params);
	}
	
	@POST
	@Path("/fetch/amazon/institutionTypes/subjects/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void fetchAmazonDatanstitutionTypesAndSubjects() throws InvalidKeyException, IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException{
		SignedRequestsHelper helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
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
        //params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
       // params.put("Keyword", "Test");
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
        //Map<CareerStream,Subject> map = UtilService.getCareerStreamSubjectKeywords();
        //AmazonClient1.fetchByCareerStreamSubject(map, params);
        
        Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
        AmazonClient1.fetchByInstitutionTypeSubject(map2, params);
//        
//        Map<Subject, Exam> map3 = UtilService.getSubjectExamKeywords();
//        AmazonClient1.fetchBySubjectExam(map3, params);
	}
	
	@POST
	@Path("/fetch/amazon/subjects/exams/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void fetchAmazonDataSubjectsAndExams() throws InvalidKeyException, IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException{
		SignedRequestsHelper helper = SignedRequestsHelper.getInstance("webservices.amazon.in", AWS_KEY, SECRET_KEY);
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
        //params.put("Keywords", "Engineering");
        params.put("ItemPage", "10");
        AmazonClient1.setHelper(helper);
        AmazonClient1.setBookService(bookService);
       // params.put("Keyword", "Test");
        //Map<CareerStream,Subject> map = UtilService.getCareerStreamSubjectKeywords();
        //AmazonClient1.fetchByCareerStreamSubject(map, params);
        
       // Map<String,Subject> map2 = UtilService.getInstitutionTypeSubjectKeywords();
        //AmazonClient1.fetchByInstitutionTypeSubject(map2, params);
//        
        Map<Subject, Exam> map3 = UtilService.getSubjectExamKeywords();
        AmazonClient1.fetchBySubjectExam(map3, params);
	}
	
	@POST
	@Path("/delete/all/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteAllNodes() throws InvalidKeyException, IllegalArgumentException, UnsupportedEncodingException, NoSuchAlgorithmException{
		bookService.deleteAllNodes();
	}
	
	public static void main(String args[]){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:appContext.xml");
    	
		AmazonWebService service = ctx.getBean(AmazonWebService.class);
		service.fetchDataFromAmazon("");
	}
}
