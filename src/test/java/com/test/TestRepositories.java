package com.test;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2tech.domain.Book;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Rating;
import com.v2tech.domain.SocialMediaType;
import com.v2tech.domain.User;
import com.v2tech.domain.util.BookResult;
import com.v2tech.repository.BookRepository;
import com.v2tech.repository.UserRepository;
import com.v2tech.services.BookService;
import com.v2tech.services.CareerStreamService;
import com.v2tech.services.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional("neo4jTransactionManager")
public class TestRepositories {
	

	@Autowired
    UserRepository userrep;
	
	@Autowired
	BookRepository bookRep;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Neo4jTemplate template;
	
	@Autowired
	CareerStreamService careerStreamService;
	
	@Test
	public void testDummy(){
		try {
			User u1 = new User("bunty1@yahoo.com", SocialMediaType.NONE);
			User u2 = new User("user10@yahoo.com", SocialMediaType.NONE);
			Set<User> users = new HashSet();
			users.add(u1);
			users.add(u2);
			ObjectMapper objectMapper = new ObjectMapper();
			String str = objectMapper.writeValueAsString(users);
			System.out.println(str);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testAddToFriend(){
		User user = new User("user7@yahoo.com", "temp");
		
		User user2 = new User("bunty1@yahoo.com", "temp");
		userService.saveOrUpdate(user2);
		Set<User> frnds = new HashSet();
		User temp = new User("user10@yahoo.com");
		frnds.add(user2);
		frnds.add(temp);
		userService.addFriendsToUser(user, frnds);
	}
	
	@Test
	public void testUserOutgoing(){
		User user1 = new User();
		user1.setUser("user7@yahoo.com");
		user1.setPassword("temp");
		user1.setFullName("user 1");
		user1.setAge(39);
		
		User user2 = new User();
		user2.setUser("user8@yahoo.com");
		user2.setPassword("temp");
		user2.setFullName("user 2");
		user2.setAge(44);
		
		userService.saveOrUpdate(user2);
		user1.getFriends().add(user2);
		userService.saveOrUpdate(user1);
		
		
		
	}
	
	@Test
	public void testRating(){
		User user = new User("user10@yahoo.com");
		Book book = new Book("123456789");
		Rating rating= new Rating(user, book);
		userService.addUserRatingToBook(rating);
	}
	
	@Test
	public void testGetUser(){
		User user = userService.getSingleUser(new User("satinsut@yahoo.com"));
		//System.out.println(user.getRatings().size());
	}
	
	@Test
	public void testUserCreation() throws ParseException{
		
		User user = new User("bunty1@yahoo.com", "temp");
		String date = "17-04-1976";
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date bDate = 	format.parse(date);
		user.setBirthday(bDate);
		userService.saveOrUpdate(user);
		//movie.
	}
	
	@Test
	public void testBook(){
		Book book = new Book();
		book.setISBN("werty1234");
		book.setBookTitle("Physics Engineering");
		book.setAuthors("Robert D and Others Et All");
		bookService.saveOrUpdate(book);
	}
	
	
	
	@Test
	public void testGetBooks(){
		//bookRep.
		//Book book = bookRep.findBySchemaPropertyValue("ISBN", );
		java.util.Set<Book> books = bookRep.findBookByISBN("123456679");
			for(Book book : books){
				bookRep.delete(book);
			}
		//Book book = bookRep.findBookByISBN("123456679");
		//System.out.println(book);
	}
	
	@Test
	public void testGetBooksByProfileData(){
		String keyword="(?i).*electro.*";
		Iterable<Book> books =  bookRep.searchBooksByProfileData(keyword);
		Iterator<Book> itr = books.iterator();
		int count = 0;
		while(itr.hasNext()){
			count ++;
			Book book = itr.next();
			System.out.println("boo title is "+book.getBookTitle());
		}
		System.out.println(" books count "+count);
	}
	
	@Test
	public void testSearchBooksByKeyword(){
		String keyword = "(?i).*Architecture.*";
		System.out.println(URLEncoder.encode(keyword));
		
		Iterable<Book> books =  bookRep.searchBooksByKeyword(keyword, 10);
		Iterator<Book> itr = books.iterator();
		int count = 0;
		while(itr.hasNext()){
			count ++;
			Book book = itr.next();
			System.out.println("boo title is "+book.getBookTitle());
		}
		System.out.println(" books count "+count);
	}
	
	@Test
	public void testGetRatingBook(){
	//	Book book = bookService.getSingleBook("123456679");
		Set<Rating> ratings = bookRep.getRatingsForBook("123456679");
		for(Rating rating: ratings){
			System.out.println("rating  "+rating.getUser().getUser()+" "+rating.getBook().getBookTitle());
		}
	}
	
	@Test
	public void testGetRatingByUser(){
		Set<Rating> ratings = userrep.getRatingsByUser("user2@yahoo.com");
		for(Rating rating: ratings){
			System.out.println("rating  "+rating.getUser().getUser()+" "+rating.getBook().getBookTitle());
		}
	}
	
	@Test
	public void testGetBooksRatedByFriends(){
		List<BookResult> objects = userrep.searchBooksRatedByFriends("user9@yahoo.com");
		for(BookResult bookResult : objects){
			int rated = bookResult.getRated();
			Book book = bookResult.getBook();
			//template.createEntityFromState((State) book, type, mappingPolicy)
			System.out.println(book.getClass());
		}
	}
	
	@Test
	public void testDuplicateProblem(){
		java.util.Set<Book> books = bookRep.findBookByISBN("werty1234");
		Book[] books1 = new Book[books.size()];
		books.toArray(books1);
		CareerStream cs1 = new CareerStream("Test CS1");
		careerStreamService.saveOrUpdateCareerStream(cs1);
		CareerStream cs2 = careerStreamService.getSingleCareerStream(cs1.getStream());
		CareerStream cs3 = careerStreamService.getSingleCareerStream(cs1.getStream());
		Book book = books1[0];
		book.getCareerStreams().add(cs2);
		book.getCareerStreams().add(cs3);
		book = bookService.saveOrUpdate(book);
	}
	
	@Test
	public void testsearchBooksByKeyword(){
		String keyword="(?i).*JEE Main.*";
		Set<Book> books = bookService.searchBooksByKeyword(keyword, 10);
			for(Book book : books){
				System.out.println(book.getBookTitle());
			}
	}
	
	@Test
	public void testGenericsearchBooksByKeyword(){
		String keyword="(?i).*Medical.*";
		Set<Book> books = bookService.searchBooksByGenericKeyword(keyword, 10);
			for(Book book : books){
				System.out.println(book.getBookTitle()+" "+book.getAuthors());
			}
	}
	
	@Test
	public void testsearchBooksByCareerStreamAndSubject(){
		String cS="(?i).*medical.*";
		String subject="(?i).*maths.*";
		Set<Book> books = bookService.searchBooksByCareerStreamAndSubject(cS, subject, 15);
			for(Book book : books){
				System.out.println(book.getBookTitle()+" "+book.getAuthors());
			}
	}
	
	@Test
	public void testsearchBooksByCareerStreamAndExam(){
		String cS="(?i).*medical.*";
		String exam="(?i).*SAT.*";
		Set<Book> books = bookService.searchBooksByCareerStreamAndExam(cS, exam, 15);
			for(Book book : books){
				System.out.println(book.getBookTitle()+" "+book.getAuthors());
			}
	}
	
	@Test
	public void testsearchBooksBySubjectAndExam(){
		String subject="(?i).*chemistry.*";
		String exam="(?i).*jee.*";
		Set<Book> books = bookService.searchBooksBySubjectAndExam(subject, exam, 15);
			for(Book book : books){
				System.out.println(book.getBookTitle()+" "+book.getAuthors());
			}
	}
	
	@Test
	public void testsearchBooksByInstitutionTypeAndSubject(){
		String institute="(?i).*iit.*";
		String subject="(?i).*Chemistry.*";
		Set<Book> books = bookService.searchBooksByInstitutionTypeAndSubject(institute, subject, 15);
			for(Book book : books){
				System.out.println(book.getBookTitle()+" "+book.getAuthors());
			}
	}
	
	@Test
	public void testDeleteAll(){
		bookService.deleteAllNodes();
	}
}
