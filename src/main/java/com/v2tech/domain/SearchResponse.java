package com.v2tech.domain;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
	
	List<Book> books = new ArrayList<>();
	
	List<List<Book>> superList = new ArrayList<List<Book>>();
	
	List<CoachingClass> classes = new ArrayList<>();
	
	List<DigitalTool> tools = new ArrayList<>();

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<List<Book>> getSuperList() {
		return superList;
	}

	public void setSuperList(List<List<Book>> superList) {
		this.superList = superList;
	}

	public List<CoachingClass> getClasses() {
		return classes;
	}

	public void setClasses(List<CoachingClass> classes) {
		this.classes = classes;
	}

	public List<DigitalTool> getTools() {
		return tools;
	}

	public void setTools(List<DigitalTool> tools) {
		this.tools = tools;
	}
	
	

}
