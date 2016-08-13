package com.v2tech.webservices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2.booksys.common.util.ExcelReader;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Book;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.SearchResponse;
import com.v2tech.domain.Source;
import com.v2tech.domain.Subject;
import com.v2tech.repository.BookRepository;
import com.v2tech.services.BookService;
import com.v2tech.services.OrderService;
import com.v2tech.services.UserKeywordRelationService;

@Path("/bookService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class BookWebService
	{
		@Autowired
		BookService							bookService;
		
		@Autowired
		BookRepository						bookRepository;
		
		@Autowired
		private UserKeywordRelationService	userKeywordRelationService;
		
		
		private List<Book> convertToBooks(Iterable<Book> books)
			{
				List<Book> booksToBeReturned = new ArrayList<Book>();
				for (Book book : books)
					{
						Book bk = new Book();
						bk.setId(book.getId());
						bk.setAuthors(book.getAuthors());
						bk.setBookTitle(book.getBookTitle());
						//				if(book.getb){
						//					bk.setBookTp(book.getBookType().getType());
						//				}
						/**
						 * Don't set the career streams got out from book into bk reference.These career streams will give a lazy init exception
						 */
						//bk.setCareerStreams(book.getCareerStreams());
						bk.setCurrency(book.getCurrency());
						bk.setDetailPageURL(book.getDetailPageURL());
						bk.setEdition(book.getEdition());
						bk.setFormat(book.getFormat());
						//bk.setId(book.getId());
						bk.setISBN(book.getISBN());
						bk.setKeyword(book.getKeyword());
						bk.setLargeImageUrl(book.getLargeImageUrl());
						bk.setMediumImageUrl(book.getMediumImageUrl());
						bk.setMrp(book.getMrp());
						bk.setNumberOfPages(book.getNumberOfPages());
						bk.setProviderUniqueIdentifier(book.getProviderUniqueIdentifier());
						bk.setPublisher(book.getPublisher());
						/**
						 * Don't set the exams got out from book into bk reference.Theseexams will give a lazy init exception
						 */
						//bk.setRelevantExams(book.getRelevantExams());
						//bk.setRelevantSubjects(book.getRelevantSubjects());//don't uncomment
						bk.setSmallImageUrl(book.getSmallImageUrl());
						//bk.setSources(book.getSources());//don't uncomment
						bk.setStrTableOfContents(book.getStrTableOfContents());
						bk.setYear(book.getYear());
						booksToBeReturned.add(bk);
					}
				return booksToBeReturned;
			}
			
		@GET
		@Path("/searchBook/title/{title}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchAllBooksWithTitle(@PathParam("title") String title, @PathParam("token") String token)
			{
				//Set<Book> books = bookRepository.searchAllBooksByTitle(".*"+title+".*");
				Set<Book> books = bookRepository.searchAllBooksByTitle(title);
				List<Book> ret = new ArrayList<>(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(ret);
				return searchResponse;
			}
			
		@GET
		@Path("/search/author/{author}/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByAuthor(@PathParam("author") String author, @PathParam("limit") String limit, @PathParam("token") String token)
			{
				
				int lim = -1;
				
				try
					{
						lim = Integer.parseInt(limit);
						
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
					
				if (lim > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
					
				Set<Book> books = bookService.searchBooksByAuthor(".*" + author + ".*", lim);
				List<Book> ret = new ArrayList<>(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(ret);
				return searchResponse;
			}
			
		@GET
		@Path("/search/careerStream/{careerStream}/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByCareerStream(@PathParam("careerStream") String careerStream, @PathParam("limit") String limit, @PathParam("token") String token)
			{
				
				int lim = -1;
				
				try
					{
						lim = Integer.parseInt(limit);
						
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
					
				if (lim > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
					
				Set<Book> books = bookService.searchBooksByCareerStream(careerStream, lim);
				List<Book> ret = new ArrayList<>(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(ret);
				return searchResponse;
			}
			
		@GET
		@Path("/search/publisher/{publisher}/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByPublisher(@PathParam("publisher") String publisher, @PathParam("limit") String limit, @PathParam("token") String token)
			{
				
				int lim = -1;
				
				try
					{
						lim = Integer.parseInt(limit);
						
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
					
				if (lim > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
					
				Set<Book> books = bookService.searchBooksByPublisher(publisher, lim);
				List<Book> ret = new ArrayList<>(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(ret);
				return searchResponse;
			}
			
		@GET
		@Path("/search/publisher/{publisher}/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByYear(@PathParam("year") String year, @PathParam("limit") String limit, @PathParam("token") String token)
			{
				
				int lim = -1;
				
				try
					{
						lim = Integer.parseInt(limit);
						
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
					
				if (lim > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
					
				Set<Book> books = bookService.searchBooksByYear(year, lim);
				List<Book> ret = new ArrayList<>(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(ret);
				return searchResponse;
			}
			
		@GET
		@Path("/search")
		public SearchResponse performGenericSearch(@QueryParam("search") String search, @QueryParam("startFrom") String startFrom, @QueryParam("maxResults") String maxResults, @QueryParam("token") String token, @DefaultValue("default") @QueryParam("searchType") String searchType, @DefaultValue("") @QueryParam("userId") String userId)
			{
				return searchBooksByGenericCriteria(search, startFrom, maxResults, token, searchType, userId);
			}
			
		@GET
		@Path("/search/genericCriteria/{genericCriteria}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByGenericCriteria(@PathParam("genericCriteria") String genericCriteria, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token, @DefaultValue("default") @QueryParam("searchType") String searchType, @DefaultValue("") @QueryParam("userId") String userId)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Set<Book> books = new HashSet<Book>();
				//userId = "info.sharmapuneet@gmail.com";
				if (searchType.equalsIgnoreCase("default") == true)
					{
						List<String> recommendedKeywords = userKeywordRelationService.getSearchedTermByFriends(userId);
						for (String recommendedKeyword : recommendedKeywords)
							{
								Set<Book> tempBook = bookService.searchBooksByGenericKeyword("(?i).*" + recommendedKeyword + ".*", max);
								if ((tempBook != null) && (tempBook.size() > 0))
									{
										books.addAll(tempBook);
									}
							}
					}
				else
					
					{
						// User Specific Personalize Search
						// TODO: Currently Using token Value as userId later would be replaced by input from ui.
						userKeywordRelationService.increaseSearchTermCounterForUser(userId, genericCriteria);
						books = bookService.searchBooksByGenericKeyword("(?i).*" + genericCriteria + ".*", max);
					}
				SearchResponse searchResponse = new SearchResponse();
				List<Book> booksToBeReturned = convertToBooks(books);
				//List<List<Book>> superList = com.google.common.collect.Lists.partition(booksToBeReturned, 5);
				//searchResponse.setSuperList(superList);
				
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/criteria/{criteria}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooks(@PathParam("criteria") String criteria, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksByKeyword("(?i).*" + criteria + ".*", max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/careerStream/{careerStream}/subject/{subject}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByCareerStreamAndSubject(@PathParam("careerStream") String careerStream, @PathParam("subject") String subject, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksByCareerStreamAndSubject(careerStream, subject, max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/careerStream/{careerStream}/exam/{exam}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByCareerStreamAndExam(@PathParam("careerStream") String careerStream, @PathParam("exam") String exam, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksByCareerStreamAndExam(careerStream, exam, max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/subject/{subject}/exam/{exam}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksBySubjectAndExam(@PathParam("subject") String subject, @PathParam("exam") String exam, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksBySubjectAndExam(subject, exam, max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/subject/{subject}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksBySubject(@PathParam("subject") String subject, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookRepository.searchBooksBySubject(".*" + subject + ".*", max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/exam/{exam}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByExam(@PathParam("exam") String exam, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookRepository.searchBooksBySubject(".*" + exam + ".*", max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		//searchBooksByInstitutionTypeAndSubject
		@GET
		@Path("/search/institution/{institution}/subject/{subject}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByInstitutionTypeAndSubject(@PathParam("institution") String institution, @PathParam("subject") String subject, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksByInstitutionTypeAndSubject(institution, subject, max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/bookTitle/{bookTitle}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByBookTitle(@PathParam("bookTitle") String bookTitle, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksByBookTitle(bookTitle, max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/search/isbn/{isbn}/startFrom/{startFrom}/maxResults/{maxResults}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchBooksByISBN(@PathParam("isbn") String isbn, @PathParam("startFrom") String startFrom, @PathParam("maxResults") String maxResults, @PathParam("token") String token)
			{
				int start = -1;
				int max = -1;
				try
					{
						start = Integer.parseInt(startFrom);
						max = Integer.parseInt(maxResults);
					}
				catch (NumberFormatException e)
					{
						
						e.printStackTrace();
						new V2GenericException("Code-InvalidPagingParams,Msg-Paging Params are not numeric");
					}
				if (start >= max)
					{
						if (!((start == -1) && (max == -1)))
							{
								new V2GenericException("Code-InvalidPagingParams,Msg-Start Paging Value can not be larger or equal than Max Paging Value");
								
							}
					}
					
				if ((max - start) > 50)
					{
						new V2GenericException("Code-FetchingTooMuchData,Msg-You can at most fetch 50 records of data");
					}
				Iterable<Book> books = bookService.searchBooksByISBN(isbn, max);
				List<Book> booksToBeReturned = convertToBooks(books);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setBooks(booksToBeReturned);
				return searchResponse;
			}
			
		@GET
		@Path("/count/books/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public Long countBooks(@PathParam("token") String token)
			{
				return bookService.countBooks();
			}
			
		/**
		 * The method extracts the file name (sans extension) of the input file passed to this service
		 *
		 * @param header
		 * @return
		 */
		public static String getFileName(MultivaluedMap<String, String> header)
			{
				
				String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
				
				for (String filename : contentDisposition)
					{
						if ((filename.trim().startsWith("filename")))
							{
								
								String[] name = filename.split("=");
								
								String finalFileName = name[1].trim().replaceAll("\"", "");
								return finalFileName;
							}
					}
				return "unknown";
			}
			
		@POST
		@Path("/uploadBooksExcel")
		@Consumes(MediaType.MULTIPART_FORM_DATA)
		@Produces("application/json")
		public boolean uploadBookExcel(Attachment attachment, @PathParam("token") String token)
			{
				try
					{
						DataHandler handler = attachment.getDataHandler();
						
						InputStream stream = handler.getInputStream();
						
						MultivaluedMap<String, String> map = attachment.getHeaders();
						System.out.println("fileName Here" + getFileName(map));
						
						File excelRules = new File("rules" + File.separator + "excelBookRules.xml");
						List<Book> books = ExcelReader.parseExcelFileToBeans(stream, excelRules);
						for (Book book : books)
							{
								
								//book.setBookType(new BType(book.getBookTp()));
								String stream1 = book.getcStreams();
								Set<CareerStream> csList = new HashSet<CareerStream>();
								//CareerStream cs = new CareerStream(stream1);
								//csList.add(cs);
								book.setCareerStreams(csList);
								
								String rExams = book.getrExams();
								Set<Exam> relExamsList = new HashSet<>();
								book.setRelevantExams(relExamsList);
								//					if(rExams != null){
								//					StringTokenizer stk = new StringTokenizer(rExams, ",");
								//						while(stk.hasMoreTokens()){
								//							String exm = stk.nextToken();
								//							if(exm.trim().length() > 1){
								//								Exam ex = new Exam(exm);
								//								ex.setExam(exm);
								//								relExamsList.add(ex);
								//							}
								//						}
								//					book.setRelevantExams(relExamsList);
								//					}
								
								String rSubjects = book.getrSubjects();
								Set<Subject> relSubjectsList = new HashSet<>();
								book.setRelevantSubjects(relSubjectsList);
								//					if(rSubjects != null){
								//					StringTokenizer stk = new StringTokenizer(rSubjects, ",");
								//						while(stk.hasMoreTokens()){
								//							String rSubject = stk.nextToken();
								//							if(rSubject.trim().length() > 1){
								//							Subject sub = new Subject(rSubject);
								//							//sub.set
								//							relSubjectsList.add(sub);
								//							}
								//						}
								//					book.setRelevantSubjects(relSubjectsList);
								//					}
								
								String rSources = book.getSrcs();
								Set<Source> sourcesList = new HashSet<>();
								//					if(rSources != null){
								//					StringTokenizer stk = new StringTokenizer(rSources, ",");
								//						while(stk.hasMoreTokens()){
								//							String src = stk.nextToken();
								//							if(src.trim().length() > 1){
								//								sourcesList.add(new Source(src));
								//							}
								//						}
								//					book.setSources(sourcesList);
								//					}
								book.setSources(sourcesList);
								
								bookService.saveOrUpdate(book);
							}
						return true;
					}
				catch (IOException e)
					{
						
						e.printStackTrace();
						throw new V2GenericException("Code-FileUploadProblem,Msg-Problem with file upload");
					}
				catch (Exception e)
					{
						
						e.printStackTrace();
						throw new V2GenericException("Code-ExcelParseProblem,Msg-Can not convert excel into beans");
					}
			}
			
		@POST
		@Path("/saveOrUpdate/book/token/{token}")
		@Consumes("application/json")
		public void saveOrUpdateBook(Book book, @PathParam("token") String token)
			{
				bookService.saveOrUpdate(book);
			}
			
		@POST
		@Path("/updateKeywordAndSearchableParam/book/token/{token}")
		@Consumes("application/json")
		public void updateKeywordAndSearchableParam(Book book, @PathParam("token") String token)
			{
				bookService.updateKeywordAndSearchableParam(book);
			}
			
	}
