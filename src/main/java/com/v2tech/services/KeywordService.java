package com.v2tech.services;

import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;

import com.v2tech.domain.Book;
import com.v2tech.domain.Keyword;
import com.v2tech.domain.KeywordType;
import com.v2tech.repository.BookRepository;
import com.v2tech.repository.CoachingClassRepository;
import com.v2tech.repository.KeywordRepository;

@Service
public class KeywordService {
	@Autowired
	BookService bookService;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	KeywordRepository keywordRepository;
	
	@Autowired
	CoachingClassRepository coachingClassRepository;
	
	public void createAndUpdateKeywordsForSystem(){
		
		Result<Book> result =  bookRepository.findAll();
		Iterator<Book> itr = result.iterator();
			while(itr.hasNext()){
				Book book = itr.next();
				String bookTitle = book.getBookTitle();
				Keyword keyword = keywordRepository.findKeywordByText(bookTitle);
					if(keyword == null){
						keyword = new Keyword(bookTitle);
						keyword.setKeywordType(KeywordType.BOOK_TITLE.toString());
						try{
						keywordRepository.save(keyword);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				
				String authors = book.getAuthors();
				StringTokenizer stk = new StringTokenizer(authors, ",");
					while(stk.hasMoreTokens()){
						String author = stk.nextToken();
						author = author.replace('(', ' ');
						author = author.replace(')', ' ');
						keyword = keywordRepository.findKeywordByText(author);
						if(keyword == null){
							keyword = new Keyword(author);
							keyword.setKeywordType(KeywordType.AUTHOR.toString());
							keywordRepository.save(keyword);
							
						}
					}
					
				String cStreams = book.getcStreams();
				stk = new StringTokenizer(cStreams, ",");
				while(stk.hasMoreTokens()){
					String cStream = stk.nextToken();
					keyword = keywordRepository.findKeywordByText(cStream);
					if(keyword == null){
						keyword = new Keyword(cStream);
						keyword.setKeywordType(KeywordType.CAREERSTREAM.toString());
						keywordRepository.save(keyword);
						
					}
				}
				
				String publisher = book.getPublisher();
				if(publisher != null && publisher.length() > 0){
					keyword = keywordRepository.findKeywordByText(publisher);
					if(keyword == null){
						keyword = new Keyword(publisher);
						keyword.setKeywordType(KeywordType.PUBLISHER.toString());
						keywordRepository.save(keyword);
					}
				}
				
				
				String exams = book.getrExams();
				stk = new StringTokenizer(exams, ",");
				while(stk.hasMoreTokens()){
					String exam = stk.nextToken();
					keyword = keywordRepository.findKeywordByText(exam);
					if(keyword == null){
						keyword = new Keyword(exam);
						keyword.setKeywordType(KeywordType.EXAM.toString());
						keywordRepository.save(keyword);
						
					}
				}
				String subjects = book.getrSubjects();
				stk = new StringTokenizer(subjects, ",");
				while(stk.hasMoreTokens()){
					String subject = stk.nextToken();
					keyword = keywordRepository.findKeywordByText(subject);
					if(keyword == null){
						keyword = new Keyword(subject);
						keyword.setKeywordType(KeywordType.SUBJECT.toString());
						keywordRepository.save(keyword);
						
					}
				}
				
			}
	}
	
	public Set<Keyword> getAutowireKeywords(String startWith){
		String filter = "(?i)"+startWith+".*";
		return keywordRepository.getKeywords(filter); 
	}
	
	public Set<Keyword> getAutowireKeywordsContains(String contains){
		String filter = "(?i).*"+contains+".*";
		return keywordRepository.getKeywords(filter); 
	}
	
	

}
