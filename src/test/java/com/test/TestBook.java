package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.v2tech.domain.Book;
import com.v2tech.domain.SearchResponse;
import com.v2tech.repository.BookRepository;
import com.v2tech.services.BookService;
import com.v2tech.services.CareerStreamService;
import com.v2tech.services.ExamService;
import com.v2tech.services.SubjectService;
import com.v2tech.webservices.BookWebService;

/**
 * @author brij
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestBook {
@Autowired
BookService bookService;
	


@Autowired
CareerStreamService careerStreamService;

@Autowired
ExamService examService;

@Autowired
SubjectService subjectService;

@Autowired
BookRepository bookRepository;

@Autowired
BookWebService bookWebService;

static List<Object> providers = new ArrayList<Object>();
static WebClient webClient = null;



@Before
	public void setUp() throws Exception {
		try {
			providers.add(new JacksonJsonProvider() );
			//webClient = WebClient.create("http://utilityapplications-socialapp.rhcloud.com/ws/rest/pdfBoxService/pdfToJavaOutput", providers);
			webClient = WebClient.create("http://localhost/v2-booksysN-1.0/ws/rest/bookService/uploadBooksExcel", providers);
			webClient.header("Content-Type", "multipart/form-data");
			webClient.type(MediaType.MULTIPART_FORM_DATA_TYPE);
			HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
			conduit.getClient().setReceiveTimeout(0);
			webClient.accept("application/json").type("multipart/form-data");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testSearchBooksByGenericCriteria(){
	SearchResponse response = bookWebService.searchBooksByGenericCriteria("Maths", "1", "11", "token", "default", "anonymous");
	List<Book> books = response.getBooks();
	System.out.println("books "+books.size());
		for(Book book : books){
			System.out.println(book.getLargeImageUrl()+" medium "+book.getMediumImageUrl());
		}
	}

	@Test
	public void testBooksUploadExcelLocally(){
		try {
			   ContentDisposition cd = new ContentDisposition("attachment;filename=Book_Template_v1.0.xlsx");
			   List<Attachment> atts = new LinkedList<Attachment>();
			   FileInputStream fis = new FileInputStream("Book_Excel_DataCapture_March 16_v2.xlsx");
			   Attachment att = new Attachment("root", fis, cd);
			   atts.add(att);
			   bookWebService.uploadBookExcel(att, "test");
			   Assert.assertEquals(true, true);
			} catch(Exception e){
				e.printStackTrace();
				Assert.assertEquals(true, false);
				//logErrorStack(e);
			}	
	}
	
	@Test
	@Rollback(value=false)
	public void testUploadExcel(){
		try {
		   ContentDisposition cd = new ContentDisposition("attachment;filename=Book_Template_v1.0.xlsx");
		   List<Attachment> atts = new LinkedList<Attachment>();
		   FileInputStream fis = new FileInputStream("Book_Template_v1.0.xlsx");
		   Attachment att = new Attachment("root", fis, cd);
		   atts.add(att);
		   Boolean res =  webClient.post(att, Boolean.class);
		   Assert.assertEquals(true, true);
		} catch(Exception e){
			e.printStackTrace();
			Assert.assertEquals(true, false);
			//logErrorStack(e);
		}	
		
	}

	@Test
	@Rollback(value=false)
	public void testCreateBook(){
		//
		Book b = new Book();
		b.setBookTitle("PLC Educators CBSE PMT (NEET) Biology Question Bank");
		b.setISBN("Title-PLC Educators CBSE PMT (NEET) Biology Question Bank");
		bookService.saveOrUpdate(b);
	}
	
	@Test
	@Rollback(value=false)
	public void testDuplicates(){
		Result<Book> books = bookRepository.findAll();
		Map<String,Book> map = new HashMap<>();
		Iterator<Book> itr = books.iterator();
		List<Book> list = new ArrayList<>();
			while(itr.hasNext()){
				Book bk = itr.next();
				if(map.get(bk.getISBN()) == null){
					map.put(bk.getISBN(), bk);
				}
				else{
					list.add(bk);
				}
			}
		for(Book book : list){
			System.out.println(book.getISBN());
			bookRepository.delete(book);
		}
	}
	
	@Test
	public void testGenericCriteria() throws JsonProcessingException{
		String value = "(?i).*Coach.*";
		Set<Book> books = bookRepository.searchBooksByGenericKeyword(value, 5);
		ObjectMapper mapper = new ObjectMapper();
		String d = mapper.writeValueAsString(books);
		System.out.println(d);
	}
	
	@Test
	public void testGetAllBooks() throws IOException{
		Result<Book> books = bookRepository.findAll();
		Iterator<Book> itr = books.iterator();
		List<String> rows = new ArrayList();
			while(itr.hasNext()){
				Book book = itr.next();
//				String row[] = new String[4];
//				row[0] = book.getISBN();
//				row[1] = book.getAuthors();
//				row[2] = book.getPublisher();
//				row[3] = book.getBookTitle();
				rows.add(book.getBookTitle());
				
			}
			FileUtils.writeLines(new File("books.csv"), rows);
			
		
	}
	
}
