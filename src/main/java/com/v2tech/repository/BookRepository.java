package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Book;
import com.v2tech.domain.Rating;

@Repository
public interface BookRepository extends GraphRepository<Book>{
	
	@Query("MATCH (bk:Book) WHERE bk.bookTitle =~ {0} AND searchable ='yes' return bk LIMIT {1};")
	public Set<Book> findBooksByBookTitle(String title, Integer limit);
	
	
	@Query("MATCH (bk:Book) WHERE bk.bookTitle =~ {0} AND searchable ='yes' OR bk.authors =~ {0}  return bk;")
	public Iterable<Book> searchBooksByProfileData(String keyword);
	
	@Query("MATCH (bk:Book) WHERE bk.ISBN = {0} return bk;")
	public Set<Book> findBookByISBN(String ISBN);
	
	@Query("MATCH (a)-[r:HAVE_RATED]->(b) WHERE b.ISBN={0}  AND b.searchable ='yes' RETURN r LIMIT 25")
	public Set<Rating> getRatingsForBook(String bookIsbn);
	
	@Query("MATCH (book:Book) WHERE book.keyword =~ {0}  AND book.searchable ='yes' RETURN book LIMIT {1};")
	public Set<Book> searchBooksByKeyword(String keyword, Integer limit) ;
	
	
	/**
	 * Will include OR between keyword, title, isbn and publisher
	 * @param keyword
	 * @param limit
	 * @return
	 */
	@Query("MATCH (book:Book) WHERE  book.searchable ='yes' AND (book.keyword =~ {0} OR book.authors =~ {0} OR book.bookTitle =~ {0} OR book.publisher =~ {0} OR book.ISBN =~ {0} ) RETURN book LIMIT {1};")
	public Set<Book> searchBooksByGenericKeyword(String keyword, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND book.cStreams=~ {0} AND  book.rSubjects=~ {1} RETURN book LIMIT {2};")
	public Set<Book> searchBooksByCareerStreamAndSubject(String careerStream ,String subject, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND book.cStreams=~ {0} AND  book.rExams=~ {1} RETURN book LIMIT {2};")
	public Set<Book> searchBooksByCareerStreamAndExam(String careerStream, String exam, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND  book.rSubjects=~ {0} AND  book.rExams=~ {1} RETURN book LIMIT {2};")
	public Set<Book> searchBooksBySubjectAndExam(String subject, String exam, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND  book.rSubjects=~ {0}  RETURN book LIMIT {2};")
	public Set<Book> searchBooksBySubject(String subject, Integer limit) ;
	
	
	
	@Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r")
	public void deleteAllNodes() ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND  book.institute=~ {0} AND  book.rExams=~ {1} RETURN book LIMIT {2};")
	public Set<Book> searchBooksByInstitutionAndExam(String institution, String exam, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND  book.rExams=~ {0} RETURN book LIMIT {1};")
	public Set<Book> searchBooksByExam(String exam, Integer limit) ;
	
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND book.institute=~ {0} AND  book.rSubjects=~ {1} RETURN book LIMIT {2};")
	public Set<Book> searchBooksByInstitutionAndSubject(String institution, String subject, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND  book.authors=~ {0} RETURN book LIMIT {1};")
	public Set<Book> searchBooksByAuthor(String author, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND book.cStreams=~ {0} RETURN book LIMIT {1};")
	public Set<Book> searchBooksByCareerStream(String careerstream, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND book.publisher=~ {0} RETURN book LIMIT {1};")
	public Set<Book> searchBooksByPublisher(String publisher, Integer limit) ;
	
	@Query("MATCH (book:Book) where book.searchable ='yes' AND book.year=~ {0} RETURN book LIMIT {1};")
	public Set<Book> searchBooksByYear(String year, Integer limit) ;
	
	@Query("MATCH (bk:Book) WHERE bk.bookTitle =~ {0}  return bk;")
	public Set<Book> searchAllBooksByTitle(String title) ;
}
