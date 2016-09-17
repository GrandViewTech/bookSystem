package com.v2tech.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Book;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.KeywordEntity;
import com.v2tech.domain.RESOURCE_TYPE;
import com.v2tech.domain.Review;
import com.v2tech.domain.Source;
import com.v2tech.domain.Subject;
import com.v2tech.domain.util.ResourceEntity;
import com.v2tech.domain.util.SearchList;
import com.v2tech.repository.BookRepository;

@Service
public class BookService
	{
		@Autowired
		UserKeywordRelationService	userKeywordRelationService;
		
		@Autowired
		ReviewService				reviewService;
		
		@Autowired
		UserService					userService;
		
		@Autowired
		BookRepository				bookRepository;
		
		@Autowired
		ExamService					examService;
		
		@Autowired
		CareerStreamService			careerStreamService;
		
		@Autowired
		SubjectService				subjectService;
		
		@Autowired
		SourceService				sourceService;
		
		public boolean ifBookExists(String isbn)
			{
				java.util.Set<Book> books = bookRepository.findBookByISBN(isbn);
				if (books.size() == 0)
					{
						return false;
					}
				else
					{
						return true;
					}
			}
			
		/**
		 * Also update the subject param
		 * 
		 * @param book
		 * @return
		 */
		public Book updateKeywordAndSearchableParam(Book book)
			{
				java.util.Set<Book> books = bookRepository.findBookByISBN(book.getISBN());
				if (books.size() > 1)
					{
						throw new V2GenericException("More than 1 book by same isbn available");
					}
				else if (books.size() == 0)
					{
						throw new V2GenericException("No Book available to update the keyword");
					}
				else
					{
						Book bookArray[] = new Book[books.size()];
						Book book2 = (books.toArray(bookArray))[0];
						book2.setKeyword(book.getKeyword());
						book2.setSearchable(book.getSearchable());
						book2.setSubject(book.getSubject());
						bookRepository.save(book2);
					}
				return book;
			}
			
		/**
		 * Associations are saved irrespective of whether book is in save/update mode.
		 * 
		 * @param book
		 * @return
		 */
		public Book saveOrUpdate(Book book)
			{
				// TODO Auto-generated method stub
				
				if (book.getBookTitle() == null)
					{
						throw new V2GenericException("Book title can not be null");
					}
					
				if (book.getBookTitle().trim().length() < 3)
					{
						throw new V2GenericException("Book title is too small.");
					}
					
				if (book.getISBN() == null || book.getISBN().trim().length() < 4)
					{
						throw new V2GenericException("ISBN is either too small or null.");
					}
					
				book.setBookTitle(book.getBookTitle().trim());
				book.setISBN(book.getISBN().trim());
				book.setPublisher(book.getPublisher().trim());
				book.setYear(book.getYear().trim());
				java.util.Set<Book> books = bookRepository.findBookByISBN(book.getISBN());
				if (books.size() > 1)
					{
						throw new V2GenericException("More than 1 book by same isbn available");
					}
					
				Book bookToBeSaved = null;
				Set<Exam> exams = null;
				Set<CareerStream> careerStreams = null;
				Set<Subject> subjects = null;
				Set<Source> sources = null;
				if (books.size() == 0)
					{
						//create
						bookToBeSaved = book;
						
						for (CareerStream cS : book.getCareerStreams())
							{
								if (!bookToBeSaved.getcStreams().contains(cS.getStream()))
									{
										bookToBeSaved.setcStreams(cS.getStream());//create mode
									}
									
							}
						bookToBeSaved.setCareerStreams(new HashSet<CareerStream>());
						
						for (Exam ex : book.getRelevantExams())
							{
								if (!bookToBeSaved.getrExams().contains(ex.getExam()))
									{
										bookToBeSaved.setrExams(ex.getExam());//create mode
									}
									
							}
						bookToBeSaved.setRelevantExams(new HashSet<Exam>());
						
						for (Subject s : book.getRelevantSubjects())
							{
								if (!bookToBeSaved.getrSubjects().contains(s.getName()))
									{
										bookToBeSaved.setrSubjects(s.getName());//create mode
									}
									
							}
						bookToBeSaved.setRelevantSubjects(new HashSet<Subject>());
						
						for (Source s : book.getSources())
							{
								if (!bookToBeSaved.getSrcs().contains(s.getSource()))
									{
										bookToBeSaved.setSrcs(s.getSource());//create mode
									}
							}
						bookToBeSaved.setSources(new HashSet<Source>());
					}
				else
					{
						
						//update
						Book bookArray[] = new Book[books.size()];
						bookToBeSaved = (books.toArray(bookArray))[0];
						Long id = bookToBeSaved.getId();
						//						Mapper mapper = new DozerBeanMapper();
						//						mapper.map(book, bookToBeSaved);
						bookToBeSaved.setSearchable(book.getSearchable());
						bookToBeSaved.setAuthors(book.getAuthors());
						bookToBeSaved.setBookImage(book.getBookImage());
						bookToBeSaved.setBookTitle(book.getBookTitle());
						bookToBeSaved.setBookTp(book.getBookTp());
						//bookToBeSaved.setcStreams(book.getcStreams());
						bookToBeSaved.setCurrency(book.getCurrency());
						bookToBeSaved.setDetailPageURL(book.getDetailPageURL());
						bookToBeSaved.setEdition(book.getEdition());
						bookToBeSaved.setFormat(book.getFormat());
						bookToBeSaved.setISBN(book.getISBN());
						//							if(!bookToBeSaved.getKeyword().contains(book.getKeyword())){
						//								bookToBeSaved.setKeyword(bookToBeSaved.getKeyword()+", "+book.getKeyword());
						//							}
						
						//bookToBeSaved.setKeyword(bookToBeSaved.getKeyword()+", "+book.getKeyword());
						bookToBeSaved.setKeyword(getUnique(bookToBeSaved.getKeyword(), book.getKeyword()));
						bookToBeSaved.setLargeImageUrl(book.getLargeImageUrl());
						bookToBeSaved.setMediumImageUrl(book.getMediumImageUrl());
						bookToBeSaved.setMrp(book.getMrp());
						bookToBeSaved.setNumberOfPages(book.getNumberOfPages());
						bookToBeSaved.setProviderUniqueIdentifier(book.getProviderUniqueIdentifier());
						if (!bookToBeSaved.getPublisher().contains(book.getPublisher()))
							{
								bookToBeSaved.setPublisher(bookToBeSaved.getPublisher() + ", " + book.getPublisher());
							}
							
						//bookToBeSaved.setrExams(book.getrExams());
						//bookToBeSaved.setrSubjects(book.getrSubjects());
						bookToBeSaved.setSmallImageUrl(book.getSmallImageUrl());
						//bookToBeSaved.setSrcs(book.getSrcs());
						bookToBeSaved.setStrTableOfContents(book.getStrTableOfContents());
						bookToBeSaved.setYear(book.getYear());
						
						if (!bookToBeSaved.getInstitute().contains(book.getInstitute()))
							{
								if (!(bookToBeSaved.getInstitute().trim().length() == 0))
									{
										bookToBeSaved.setInstitute(bookToBeSaved.getInstitute() + ", " + book.getInstitute());
									}
								else
									{
										bookToBeSaved.setInstitute(book.getInstitute());
									}
									
							}
							
						//						Set<CareerStream> streams = new HashSet<CareerStream>();
						for (CareerStream cS : book.getCareerStreams())
							{
								if (!bookToBeSaved.getcStreams().contains(cS.getStream()))
									{
										bookToBeSaved.setcStreams(bookToBeSaved.getcStreams() + ", " + cS.getStream());
									}
									
							}
						//						streams.addAll(book.getCareerStreams());
						
						//bookToBeSaved.setCareerStreams(book.getCareerStreams());
						//bookToBeSaved.setCareerStreams(streams);
						
						//						Set<Exam> exms = new HashSet<Exam>();
						for (Exam ex : book.getRelevantExams())
							{
								if (!bookToBeSaved.getrExams().contains(ex.getExam()))
									{
										bookToBeSaved.setrExams(bookToBeSaved.getrExams() + ", " + ex.getExam());
									}
									
							}
						//						exms.addAll(book.getRelevantExams());
						//bookToBeSaved.setRelevantExams(book.getRelevantExams());
						//bookToBeSaved.setRelevantExams(exms);
						
						//						Set<Subject> sbjs = new HashSet<Subject>();
						for (Subject s : book.getRelevantSubjects())
							{
								if (!bookToBeSaved.getrSubjects().contains(s.getName()))
									{
										bookToBeSaved.setrSubjects(bookToBeSaved.getrSubjects() + ", " + s.getName());
									}
									
							}
						//							sbjs.addAll(book.getRelevantSubjects());
						
						for (Source s : book.getSources())
							{
								if (!bookToBeSaved.getSrcs().contains(s.getSource()))
									{
										bookToBeSaved.setSrcs(bookToBeSaved.getSrcs() + ", " + s.getSource());
									}
							}
						bookToBeSaved.setId(id);
						
					}
				//saveAllAssociations(bookToBeSaved);//no need now
				removeAllAssociations(bookToBeSaved);//making sure we don't use any associations
				
				// Update Rating if not found
				Double averageRating = bookToBeSaved.getAverageRating();
				Integer rateCount = bookToBeSaved.getRateCount();
				bookToBeSaved.setAverageRating((averageRating == null) ? 2.5 : averageRating);
				bookToBeSaved.setRateCount((rateCount == null) ? 1 : rateCount);
				bookToBeSaved = bookRepository.save(bookToBeSaved);
				return bookToBeSaved;
				
			}
			
		private String getUnique(String original, String toBeAdded)
			{
				if (original == null)
					{
						return "";
					}
					
				if (toBeAdded == null)
					{
						return original;
					}
				if (original.contains(toBeAdded))
					{
						return original;
					}
				else
					{
						return original + "," + toBeAdded;
					}
			}
			
		private void removeAllAssociations(Book book)
			{
				book.setCareerStreams(new HashSet<CareerStream>());
				book.setRelevantExams(new HashSet<Exam>());
				book.setRelevantSubjects(new HashSet<Subject>());
				book.setSources(new HashSet<Source>());
			}
			
		private void saveAllAssociations(Book book)
			{
				Set<Exam> exams = book.getRelevantExams();
				Set<CareerStream> careerStreams = book.getCareerStreams();
				Set<Subject> subjects = book.getRelevantSubjects();
				Set<Source> sources = book.getSources();
				
				for (Exam exam : exams)
					{
						exam = examService.saveOrUpdateExam(exam);
					}
					
				for (CareerStream careerStream : careerStreams)
					{
						careerStream = careerStreamService.saveOrUpdateCareerStream(careerStream);
					}
					
				for (Subject subject : subjects)
					{
						subject = subjectService.saveOrUpdateSubject(subject);
					}
					
				for (Source source : sources)
					{
						source = sourceService.saveOrUpdateSource(source);
					}
			}
			
		public Book getSingleBook(String isbn)
			{
				java.util.Set<Book> books = bookRepository.findBookByISBN(isbn);
				if (books.size() > 1)
					{
						throw new V2GenericException("More than 1 book by same isbn available");
					}
				else if (books.size() == 0)
					{
						return null;
					}
				else
					{
						Book bookArray[] = new Book[books.size()];
						return (books.toArray(bookArray))[0];
					}
			}
			
		public Set<Book> searchBooksByKeyword(String keyword, Integer limit)
			{
				return bookRepository.searchBooksByKeyword(keyword, limit);
			}
			
		public Set<Book> searchBooksByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return bookRepository.searchBooksByGenericKeyword(keyword, limit);
			}
			
		public Set<Book> searchTopRatedBooksByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return bookRepository.searchTopRatedBooksByGenericKeyword(keyword, limit);
			}
			
		public SearchList searchTopRatedBooks(Integer limit)
			{
				List<Book> results = new ArrayList<Book>();
				Set<String> uniqueIdentifiers = new HashSet<String>();
				for (Book book : bookRepository.searchTopRatedBooks(limit))
					{
						String uniqueKey = book.getISBN().trim().toLowerCase();
						if (uniqueIdentifiers.contains(uniqueKey) == false)
							{
								results.add(book);
								uniqueIdentifiers.add(uniqueKey);
							}
					}
				//results.sort((Book book1, Book book2) -> book2.getAverageRating().compareTo(book1.getAverageRating()));
				SearchList searchList = new SearchList();
				searchList.setBooks(results);
				searchList.setUniqueIds(uniqueIdentifiers);
				return searchList;
			}
			
		public Set<Book> searchBooksByCareerStreamAndSubject(String careerStream, String subject, Integer limit)
			{
				return bookRepository.searchBooksByCareerStreamAndSubject(careerStream, subject, limit);
			}
			
		public Set<Book> searchBooksByCareerStreamAndExam(String careerStream, String exam, Integer limit)
			{
				return bookRepository.searchBooksByCareerStreamAndExam(careerStream, exam, limit);
			}
			
		public Set<Book> searchBooksBySubjectAndExam(String subject, String exam, Integer limit)
			{
				return bookRepository.searchBooksBySubjectAndExam(subject, exam, limit);
			}
			
		public Set<Book> searchBooksByInstitutionTypeAndSubject(String institution, String subject, Integer limit)
			{
				return bookRepository.searchBooksByInstitutionAndSubject(institution, subject, limit);
			}
			
		public Set<Book> searchBooksByBookTitle(String bookTitle, Integer limit)
			{
				return bookRepository.findBooksByBookTitle(bookTitle, limit);
			}
			
		public Set<Book> searchBooksByISBN(String isbn, Integer limit)
			{
				//	isbn = ("(?i)" + isbn).trim();
				isbn.trim();
				return bookRepository.findBookByISBN(isbn);
			}
			
		public void deleteAllNodes()
			{
				bookRepository.deleteAllNodes();
			}
			
		public long countBooks()
			{
				return bookRepository.count();
			}
			
		public Set<Book> searchBooksByAuthor(String author, Integer limit)
			{
				return bookRepository.searchBooksByAuthor(author, limit);
			}
			
		public Set<Book> searchBooksByCareerStream(String careerstream, Integer limit)
			{
				return bookRepository.searchBooksByCareerStream(careerstream, limit);
			}
			
		public Set<Book> searchBooksByPublisher(String publisher, Integer limit)
			{
				return bookRepository.searchBooksByPublisher(publisher, limit);
			}
			
		public Set<Book> searchBooksByYear(String year, Integer limit)
			{
				return bookRepository.searchBooksByYear(year, limit);
			}
			
		public SearchList findAllBooksByRecentPublicationYear(Integer limit)
			{
				List<Book> results = new LinkedList<Book>();
				Set<String> uniqueIdentifiers = new HashSet<String>();
				Set<Book> books = bookRepository.findAllBooksByRecentPublicationYear(limit);
				for (Book book : books)
					{
						String uniqueKey = book.getISBN().trim().toLowerCase();
						if (uniqueIdentifiers.contains(uniqueKey) == false)
							{
								results.add(book);
								uniqueIdentifiers.add(uniqueKey);
							}
					}
				//results.sort((Book book1, Book book2) -> book2.getAverageRating().compareTo(book1.getAverageRating()));
				SearchList searchList = new SearchList();
				searchList.setBooks(results);
				searchList.setUniqueIds(uniqueIdentifiers);
				return searchList;
				
			}
			
		public SearchList findBooksByUserProfile(String userId)
			{
				List<Book> results = new LinkedList<Book>();
				Set<String> uniqueIdentifiers = new HashSet<String>();
				if (userId != null && userId.trim().length() > 0)
					{
						if (userId.trim().equalsIgnoreCase("annonymous") == false)
							{
								Set<String> keywords = userService.getKeywordFromUserProfile(userId);
								for (String keyword : keywords)
									{
										Set<Book> books = searchTopRatedBooksByGenericKeyword(keyword, 1);
										for (Book book : books)
											{
												String uniqueKey = book.getISBN().trim().toLowerCase();
												if (uniqueIdentifiers.contains(uniqueKey) == false)
													{
														results.add(book);
														uniqueIdentifiers.add(uniqueKey);
													}
											}
									}
								//results.sort((Book book1, Book book2) -> book2.getAverageRating().compareTo(book1.getAverageRating()));
							}
					}
				SearchList searchList = new SearchList();
				searchList.setBooks(results);
				searchList.setUniqueIds(uniqueIdentifiers);
				return searchList;
			}
			
		public SearchList findBooksByKeywords(Set<String> keywords)
			{
				
				List<Book> results = new LinkedList<Book>();
				Set<String> uniqueIdentifiers = new HashSet<String>();
				for (String keyword : keywords)
					{
						Set<Book> books = searchTopRatedBooksByGenericKeyword(keyword, 1);
						for (Book book : books)
							{
								String uniqueKey = book.getISBN().trim().toLowerCase();
								if (uniqueIdentifiers.contains(uniqueKey) == false)
									{
										results.add(book);
										uniqueIdentifiers.add(uniqueKey);
									}
							}
					}
				SearchList searchList = new SearchList();
				searchList.setBooks(results);
				searchList.setUniqueIds(uniqueIdentifiers);
				return searchList;
			}
			
		public List<ResourceEntity> findBooksForTopRating(String userId, Integer limit)
			{
				Integer reviewLimit = 5;
				String resourceReviewedType = RESOURCE_TYPE.BOOK.getType();
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				
				SearchList profileInformation = findBooksByUserProfile(userId);
				Set<String> uniqueKeys = new LinkedHashSet<String>();
				for (Book book : profileInformation.getBooks())
					{
						String resourceIdentity = book.getISBN().trim().toLowerCase();
						if (uniqueKeys.contains(resourceIdentity) == false)
							{
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(resourceReviewedType, resourceIdentity, reviewLimit);
								ResourceEntity resourceEntity = new ResourceEntity(book, reviews);
								List<String> resultCriterias = new ArrayList<String>();
								if (profileInformation.getUniqueIds().contains(resourceIdentity))
									{
										resultCriterias.add("profile");
									}
								resourceEntity.setResultCriterias(resultCriterias);
								resourceEntities.add(resourceEntity);
								uniqueKeys.add(resourceIdentity);
							}
					}
				if (resourceEntities.size() == 0)
					{
						SearchList ratingInformation = searchTopRatedBooks(limit);
						for (Book book : ratingInformation.getBooks())
							{
								String resourceIdentity = book.getISBN().trim().toLowerCase();
								if (uniqueKeys.contains(resourceIdentity) == false)
									{
										List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(resourceReviewedType, resourceIdentity, reviewLimit);
										ResourceEntity resourceEntity = new ResourceEntity(book, reviews);
										List<String> resultCriterias = new ArrayList<String>();
										if (ratingInformation.getUniqueIds().contains(resourceIdentity))
											{
												resultCriterias.add("rating");
											}
										resourceEntity.setResultCriterias(resultCriterias);
										resourceEntities.add(resourceEntity);
										uniqueKeys.add(resourceIdentity);
									}
							}
					}
					
				return resourceEntities;
			}
			
		public List<ResourceEntity> findBooksForUserFriends(String userId)
			{
				String keywordEntity = KeywordEntity.BOOKS.getEntity();
				String resourceReviewedType = RESOURCE_TYPE.BOOK.getType();
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				Set<String> friendsRecommendationKeywords = new HashSet<String>();
				List<Book> books = new ArrayList<Book>();
				for (String keyword : userKeywordRelationService.getSearchedTermByFriendsAndKeyWordEntityType(userId, keywordEntity))
					{
						for (Book book : searchTopRatedBooksByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = book.getISBN().trim().toLowerCase();
								friendsRecommendationKeywords.add(resourceIdentity);
								books.add(book);
							}
					}
				Set<String> systemsRecommendationKeywords = new HashSet<String>();
				for (String keyword : userKeywordRelationService.getRecommendedSearchTeamBySystemForEntityType(userId, keywordEntity))
					{
						for (Book book : searchTopRatedBooksByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = book.getISBN().trim().toLowerCase();
								systemsRecommendationKeywords.add(resourceIdentity);
								books.add(book);
							}
					}
				Set<String> systemsRecommendationFromProfileKeywords = new HashSet<String>();
				for (String keyword : userService.getKeywordFromUserProfile(userId))
					{
						for (Book book : searchTopRatedBooksByGenericKeyword(keyword, 5))
							{
								String resourceIdentity = book.getISBN().trim().toLowerCase();
								systemsRecommendationFromProfileKeywords.add(resourceIdentity);
								books.add(book);
							}
					}
				SearchList publicationInformation = findAllBooksByRecentPublicationYear(5);
				books.addAll(publicationInformation.getBooks());
				Set<String> bookKeywords = new HashSet<String>();
				bookKeywords.add("Pratice");
				bookKeywords.add("solved");
				bookKeywords.add("paper");
				SearchList relativeKeywordInformation = findBooksByKeywords(bookKeywords);
				books.addAll(relativeKeywordInformation.getBooks());
				Set<String> uniqueKeys = new LinkedHashSet<String>();
				Integer reviewLimit = 5;
				for (Book book : books)
					{
						String resourceIdentity = book.getISBN().trim().toLowerCase();
						if (uniqueKeys.contains(resourceIdentity) == false)
							{
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(resourceReviewedType, resourceIdentity, reviewLimit);
								ResourceEntity resourceEntity = new ResourceEntity(book, reviews);
								List<String> resultCriterias = new ArrayList<String>();
								if (friendsRecommendationKeywords.contains(resourceIdentity))
									{
										resultCriterias.add("friends");
									}
								if (systemsRecommendationKeywords.contains(resourceIdentity))
									{
										resultCriterias.add("system");
									}
								if (systemsRecommendationFromProfileKeywords.contains(resourceIdentity))
									{
										resultCriterias.add("profile");
									}
								if (publicationInformation.getUniqueIds().contains(resourceIdentity))
									{
										resultCriterias.add("publication");
									}
								if (relativeKeywordInformation.getUniqueIds().contains(resourceIdentity))
									{
										resultCriterias.add("relativeKeyword");
									}
								resourceEntity.setResultCriterias(resultCriterias);
								resourceEntities.add(resourceEntity);
								uniqueKeys.add(resourceIdentity);
							}
					}
				return resourceEntities;
			}
			
		public List<ResourceEntity> findBooksForUserPreference(String userId)
			{
				
				Integer reviewLimit = 5;
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				String resourceReviewedType = RESOURCE_TYPE.BOOK.getType();
				List<Book> books = new ArrayList<Book>();
				SearchList profileInformation = findBooksByUserProfile(userId);
				books.addAll(profileInformation.getBooks());
				SearchList publicationInformation = findAllBooksByRecentPublicationYear(5);
				books.addAll(publicationInformation.getBooks());
				Set<String> bookKeywords = new HashSet<String>();
				bookKeywords.add("Pratice");
				bookKeywords.add("solved");
				bookKeywords.add("paper");
				SearchList relativeKeywordInformation = findBooksByKeywords(bookKeywords);
				books.addAll(relativeKeywordInformation.getBooks());
				Set<String> uniqueKeys = new LinkedHashSet<String>();
				for (Book book : books)
					{
						String resourceIdentity = book.getISBN().trim().toLowerCase();
						if (uniqueKeys.contains(resourceIdentity) == false)
							{
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(resourceReviewedType, resourceIdentity, reviewLimit);
								ResourceEntity resourceEntity = new ResourceEntity(book, reviews);
								List<String> resultCriterias = new ArrayList<String>();
								if (profileInformation.getUniqueIds().contains(resourceIdentity))
									{
										resultCriterias.add("profile");
									}
								if (publicationInformation.getUniqueIds().contains(resourceIdentity))
									{
										resultCriterias.add("publication");
									}
								if (publicationInformation.getUniqueIds().contains(resourceIdentity))
									{
										resultCriterias.add("relativeKeyword");
									}
								resourceEntity.setResultCriterias(resultCriterias);
								resourceEntities.add(resourceEntity);
								uniqueKeys.add(resourceIdentity);
							}
					}
				return resourceEntities;
			}
			
		public List<ResourceEntity> userDefinedSearch(String userId, String keyword)
			{
				List<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
				userKeywordRelationService.increaseSearchTermCounterForUser(userId, keyword);
				Set<Book> books = searchBooksByGenericKeyword(keyword, 10);
				Set<String> uniqueKeys = new LinkedHashSet<String>();
				for (Book book : books)
					{
						String resourceIdentity = book.getISBN().trim().toLowerCase();
						if (uniqueKeys.contains(resourceIdentity) == false)
							{
								List<Review> reviews = reviewService.getReviewByResourceReviewedTypeAndResourceIdentity(RESOURCE_TYPE.COACHING_CLASS.getType(), resourceIdentity, 5);
								ResourceEntity resourceEntity = new ResourceEntity(book, reviews);
								resourceEntities.add(resourceEntity);
								uniqueKeys.add(resourceIdentity);
							}
					}
				return resourceEntities;
			}
	}
