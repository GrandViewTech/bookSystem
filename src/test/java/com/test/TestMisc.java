package com.test;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2tech.domain.Book;

public class TestMisc {
	@Test
	public void testBook() throws JsonProcessingException{
		Book book = new Book();
		book.setISBN("isbn_toBeDeleted");
		book.setBookTitle("to be deleted");
		book.setAuthors("jais");
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(book));
	}

}
