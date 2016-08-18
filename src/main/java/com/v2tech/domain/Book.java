package com.v2tech.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Book
	{
		
		public Book()
			{
				
			}
			
		public Book(String isbn)
			{
				this.ISBN = isbn;
			}
			
		@GraphId
		private Long id;
		
		public Long getId()
			{
				return id;
			}
			
		private String		bookTitle					= "";
		//private Author author; // TODO : multi table task
		
		private String		edition						= "";
		
		private String		format						= "";
		
		private String		keyword						= "";
		
		private String		institute					= "";
		
		private String		providerUniqueIdentifier	= "";
		
		//	@ManyToMany(fetch=FetchType.EAGER)
		//	private List<Author> authors = new ArrayList<Author>();
		/**
		 * indexing done in BookBridge
		 */
		
		private String		authors						= "";
		
		//	@RelatedToVia(direction = org.neo4j.graphdb.Direction.INCOMING)
		//	Set<Rating> ratings;
		
		@Indexed
		private String		ISBN						= "";
		
		private String		strTableOfContents			= "";
		
		private String		publisher					= "";
		
		private String		year						= "";
		
		private Float		mrp							= -0.1f;
		
		private String		currency					= "INR";
		
		private byte[]		bookImage;
		
		private String		smallImageUrl				= "";
		
		private String		mediumImageUrl				= "";
		
		private String		largeImageUrl				= "";
		
		private String		detailPageURL				= "";
		
		private Integer		numberOfPages				= 0;
		
		private String		searchable					= "yes";
		
		/**
		 * Comma sep values
		 */
		@Transient
		String				bookTp						= "";
		
		/**
		 * Comma sep values
		 */
		
		String				cStreams					= "";
		
		/**
		 * Comma sep values
		 */
		
		String				rExams						= "";
		
		/**
		 * Comma sep values
		 */
		
		String				rSubjects					= "";
		
		/**
		 * Comma sep values
		 */
		
		String				srcs						= "";
		
		@Fetch
		@RelatedTo(type = "BELONGS_TO_CAREERSTREAM", direction = Direction.INCOMING)
		Set<CareerStream>	careerStreams				= new HashSet<>();
		
		@Fetch
		@RelatedTo(type = "BELONGS_TO_EXAM", direction = Direction.INCOMING)
		Set<Exam>			relevantExams				= new HashSet<>();
		
		@Fetch
		@RelatedTo(type = "BELONGS_TO_SUBJECT", direction = Direction.INCOMING)
		Set<Subject>		relevantSubjects			= new HashSet<Subject>();
		
		@Fetch
		@RelatedTo(type = "BELONGS_TO_SOURCE", direction = Direction.INCOMING)
		private Set<Source>	sources						= new HashSet<Source>();
		
		private Double		averageRating				= 2.5;
		
		private Integer		rateCount					= 1;
		
		public String getBookTitle()
			{
				return bookTitle;
			}
			
		public void setBookTitle(String bookTitle)
			{
				this.bookTitle = bookTitle;
			}
			
		public String getEdition()
			{
				return edition;
			}
			
		public void setEdition(String edition)
			{
				this.edition = edition;
			}
			
		public String getISBN()
			{
				return ISBN;
			}
			
		public void setISBN(String iSBN)
			{
				ISBN = iSBN;
			}
			
		public String getStrTableOfContents()
			{
				return strTableOfContents;
			}
			
		public void setStrTableOfContents(String strTableOfContents)
			{
				this.strTableOfContents = strTableOfContents;
			}
			
		public String getPublisher()
			{
				return publisher;
			}
			
		public void setPublisher(String publisher)
			{
				this.publisher = publisher;
			}
			
		public String getYear()
			{
				return year;
			}
			
		public void setYear(String year)
			{
				this.year = year;
			}
			
		public Float getMrp()
			{
				return mrp;
			}
			
		public void setMrp(Float mrp)
			{
				this.mrp = mrp;
			}
			
		public String getCurrency()
			{
				return currency;
			}
			
		public void setCurrency(String currency)
			{
				this.currency = currency;
			}
			
		public byte[] getBookImage()
			{
				return bookImage;
			}
			
		public void setBookImage(byte[] bookImage)
			{
				this.bookImage = bookImage;
			}
			
		public void setAuthors(String authors)
			{
				this.authors = authors;
			}
			
		public void setId(Long id)
			{
				this.id = id;
			}
			
		public String getAuthors()
			{
				return authors;
			}
			
		public Set<Source> getSources()
			{
				return sources;
			}
			
		public void setSources(Set<Source> sources)
			{
				this.sources = sources;
			}
			
		public String getProviderUniqueIdentifier()
			{
				return providerUniqueIdentifier;
			}
			
		public void setProviderUniqueIdentifier(String providerUniqueIdentifier)
			{
				this.providerUniqueIdentifier = providerUniqueIdentifier;
			}
			
		public String getSmallImageUrl()
			{
				return smallImageUrl;
			}
			
		public void setSmallImageUrl(String smallImageUrl)
			{
				this.smallImageUrl = smallImageUrl;
			}
			
		public String getMediumImageUrl()
			{
				return mediumImageUrl;
			}
			
		public void setMediumImageUrl(String mediumImageUrl)
			{
				this.mediumImageUrl = mediumImageUrl;
			}
			
		public String getLargeImageUrl()
			{
				return largeImageUrl;
			}
			
		public void setLargeImageUrl(String largeImageUrl)
			{
				this.largeImageUrl = largeImageUrl;
			}
			
		public String getDetailPageURL()
			{
				return detailPageURL;
			}
			
		public void setDetailPageURL(String detailPageURL)
			{
				this.detailPageURL = detailPageURL;
			}
			
		public String getFormat()
			{
				return format;
			}
			
		public void setFormat(String format)
			{
				this.format = format;
			}
			
		public Integer getNumberOfPages()
			{
				return numberOfPages;
			}
			
		public void setNumberOfPages(Integer numberOfPages)
			{
				this.numberOfPages = numberOfPages;
			}
			
		public String getKeyword()
			{
				return keyword;
			}
			
		public void setKeyword(String keyword)
			{
				this.keyword = keyword;
			}
			
		public String getBookTp()
			{
				return bookTp;
			}
			
		public void setBookTp(String bookTp)
			{
				this.bookTp = bookTp;
			}
			
		public String getcStreams()
			{
				return cStreams;
			}
			
		public void setcStreams(String cStreams)
			{
				this.cStreams = cStreams;
			}
			
		public String getrExams()
			{
				return rExams;
			}
			
		public void setrExams(String rExams)
			{
				this.rExams = rExams;
			}
			
		public String getrSubjects()
			{
				return rSubjects;
			}
			
		public void setrSubjects(String rSubjects)
			{
				this.rSubjects = rSubjects;
			}
			
		public String getSrcs()
			{
				return srcs;
			}
			
		public void setSrcs(String srcs)
			{
				this.srcs = srcs;
			}
			
		public Set<CareerStream> getCareerStreams()
			{
				return careerStreams;
			}
			
		public void setCareerStreams(Set<CareerStream> careerStreams)
			{
				this.careerStreams = careerStreams;
			}
			
		public Set<Exam> getRelevantExams()
			{
				return relevantExams;
			}
			
		public void setRelevantExams(Set<Exam> relevantExams)
			{
				this.relevantExams = relevantExams;
			}
			
		public Set<Subject> getRelevantSubjects()
			{
				return relevantSubjects;
			}
			
		public void setRelevantSubjects(Set<Subject> relevantSubjects)
			{
				this.relevantSubjects = relevantSubjects;
			}
			
		public String getInstitute()
			{
				return institute;
			}
			
		public void setInstitute(String institute)
			{
				this.institute = institute;
			}
			
		public String getSearchable()
			{
				return searchable;
			}
			
		public void setSearchable(String searchable)
			{
				this.searchable = searchable;
			}
			
		public Double getAverageRating()
			{
				return averageRating;
			}
			
		public void setAverageRating(Double averageRating)
			{
				if (averageRating.intValue() > 5)
					{
						averageRating = averageRating % 5;
					}
				this.averageRating = averageRating;
			}
			
		public Integer getRateCount()
			{
				return rateCount;
			}
			
		public void setRateCount(Integer rateCount)
			{
				this.rateCount = rateCount;
			}
	}
