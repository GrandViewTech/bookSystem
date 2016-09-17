package com.v2tech.domain.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.v2tech.domain.Book;

public class SearchList
	{
		private List<Book>	books		= new ArrayList<Book>();
		private Set<String>	uniqueIds	= new HashSet<String>();
		
		public List<Book> getBooks()
			{
				return books;
			}
			
		public void setBooks(List<Book> books)
			{
				this.books = books;
			}
			
		public Set<String> getUniqueIds()
			{
				return uniqueIds;
			}
			
		public void setUniqueIds(Set<String> uniqueIds)
			{
				this.uniqueIds = uniqueIds;
			}
			
	}
