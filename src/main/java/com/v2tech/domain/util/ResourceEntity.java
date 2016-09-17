package com.v2tech.domain.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.v2tech.domain.Book;
import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.DigitalTool;
import com.v2tech.domain.KeywordEntity;
import com.v2tech.domain.Review;

public class ResourceEntity implements Serializable
	{
		private static final long	serialVersionUID	= 1L;
		
		final static private String	DEFAULT_IMAGE		= "images/class.jpg";
		
		private String				name;
		private String				isbn;
		private String				publication;
		private String				year;
		private String				priceTag;
		private String				description;
		private String				uniqueKey;
		private Float				price;
		private String				stream;
		private Integer				rating				= 0;
		private String				bigUrl				= DEFAULT_IMAGE;
		private String				smallUrl			= DEFAULT_IMAGE;
		private String				resourceEntity;
		private String				author;
		private String				readMore			= "";
		private String				branch;
		private String				address;
		private List<Review>		reviews				= new LinkedList<Review>();
		private String				identity			= "";
		private String				detailUrl			= "";
		
		private String				website				= "";
		private Integer				rateCount			= 0;
		@JsonIgnore
		private String[]			indicatorArray1		= new String[] { "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0" };
		@JsonIgnore
		private String[]			indicatorArray2		= new String[] { "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0" };
		@JsonIgnore
		private String[]			indicatorArray3		= new String[] { "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0" };
		@JsonIgnore
		private String[]			indicatorArray4		= new String[] { "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0", "rate0" };
		
		private String				subject				= "Physics";
		
		private List<String>		resultCriterias		= new ArrayList<String>();
		
		public String getName()
			{
				return name;
			}
			
		public void setName(String name)
			{
				this.name = name;
			}
			
		public String getPublication()
			{
				return publication;
			}
			
		public void setPublication(String publication)
			{
				if (publication.trim().length() == 0)
					{
						publication = "NA";
					}
				this.publication = publication;
			}
			
		public String getPriceTag()
			{
				return priceTag;
			}
			
		public void setPriceTag(String priceTag)
			{
				this.priceTag = priceTag;
			}
			
		public String getDescription()
			{
				return description;
			}
			
		public void setDescription(String description)
			{
				this.description = description;
			}
			
		public String getUniqueKey()
			{
				return uniqueKey;
			}
			
		public void setUniqueKey(String uniqueKey)
			{
				this.uniqueKey = uniqueKey;
			}
			
		public Float getPrice()
			{
				return price;
			}
			
		public void setPrice(String currency, Float price)
			{
				if (price == null || price == -0.1F)
					{
						try
							{
								price = new Float(RandomStringUtils.randomNumeric(3));
							}
						catch (Exception exception)
							{
								exception.printStackTrace();
								price = 100F;
							}
						setPriceTag("NA");
						setPriceTag(currency + " " + price.intValue());
					}
				else
					{
						setPriceTag(currency + " " + price.intValue());
					}
				this.price = price;
				
			}
			
		public Integer getRating()
			{
				rating = (rating > 5) ? 5 : rating;
				return rating;
			}
			
		public void setRating(Integer rating)
			{
				this.rating = rating;
			}
			
		public String getBigUrl()
			{
				return bigUrl;
			}
			
		public void setBigUrl(String bigUrl)
			{
				this.bigUrl = bigUrl;
			}
			
		public String getSmallUrl()
			{
				return smallUrl;
			}
			
		public void setSmallUrl(String smallUrl)
			{
				this.smallUrl = smallUrl;
			}
			
		public String getResourceEntity()
			{
				return resourceEntity;
			}
			
		public void setResourceEntity(String resourceEntity)
			{
				this.resourceEntity = resourceEntity;
			}
			
		public String getAuthor()
			{
				return author;
			}
			
		public void setAuthor(String author)
			{
				this.author = author;
			}
			
		public String getReadMore()
			{
				return readMore;
			}
			
		public void setReadMore(String readMore)
			{
				this.readMore = readMore;
			}
			
		public String getBranch()
			{
				return branch;
			}
			
		public void setBranch(String branch)
			{
				this.branch = branch;
			}
			
		public String getAddress()
			{
				return address;
			}
			
		public void setAddress(String address)
			{
				this.address = address;
			}
			
		public String getIdentity()
			{
				return identity;
			}
			
		public void setIdentity(String identity)
			{
				this.identity = identity;
			}
			
		public String getDetailUrl()
			{
				return detailUrl;
			}
			
		public void setDetailUrl(String detailUrl)
			{
				this.detailUrl = detailUrl;
			}
			
		public List<Review> getReviews()
			{
				return reviews;
			}
			
		public void setReviews(List<Review> reviews)
			{
				Integer indicator1 = 0;
				Integer indicator2 = 0;
				Integer indicator3 = 0;
				Integer indicator4 = 0;
				for (Review review : reviews)
					{
						indicator1 = indicator1 + review.getEffectivenessAndEaseOfCommunication();
						indicator2 = indicator2 + review.getSolutionToPracticeProblems();
						indicator3 = indicator3 + review.getVisualTools();
						indicator4 = indicator4 + review.getSolvedExamples();
					}
				indicator1 = Math.round(indicator1 / 5);
				indicator2 = Math.round(indicator2 / 5);
				indicator3 = Math.round(indicator3 / 5);
				indicator4 = Math.round(indicator4 / 5);
				if (indicator1 == 0)
					{
						indicator1 = 4;
					}
				if (indicator2 == 0)
					{
						indicator2 = 4;
					}
				if (indicator3 == 0)
					{
						indicator3 = 4;
					}
				if (indicator4 == 0)
					{
						indicator4 = 4;
					}
				for (int i = 0; i < indicator1; i++)
					{
						indicatorArray1[i] = "rate" + (i + 1);
					}
				for (int i = 0; i < indicator2; i++)
					{
						indicatorArray2[i] = "rate" + (i + 1);
					}
				for (int i = 0; i < indicator3; i++)
					{
						indicatorArray3[i] = "rate" + (i + 1);
					}
				for (int i = 0; i < indicator4; i++)
					{
						indicatorArray4[i] = "rate" + (i + 1);
					}
				this.reviews = reviews;
			}
		
		
		public ResourceEntity(Book book){
			setResourceEntity(KeywordEntity.BOOKS.getEntity());
			setDescription("");
			setPriceTag(((book.getMrp() == null) || (book.getMrp() == -0.1f)) ? "NA" : "" + book.getMrp());
			setName(book.getBookTitle());
			setPrice(book.getCurrency(), book.getMrp());
			setPublication("" + book.getPublisher());
			setDetailUrl(book.getDetailPageURL());
			setIdentity(book.getISBN());
			if ((book.getLargeImageUrl() != null) && (book.getLargeImageUrl().trim().length() > 0))
				{
					setBigUrl("" + book.getLargeImageUrl());
				}
			if ((book.getSmallImageUrl() != null) && (book.getSmallImageUrl().trim().length() > 0))
				{
					setSmallUrl("" + book.getSmallImageUrl());
				}
			Integer rating = (int) ((book.getAverageRating() == null) ? 1 : Math.round(book.getAverageRating()));
			setRating(rating.intValue());
			setAuthor(book.getAuthors());
			setRateCount(book.getRateCount());
			setYear(book.getYear());
			setUniqueKey(book.getISBN());
			setSubject(book.getSubject());
			String isbn=book.getISBN();
			String charr="ISBN-";
			if(isbn.trim().contains(charr.trim()))
				{
					isbn=isbn.replaceAll(charr, "");
				}
			setIsbn(isbn);
		}
			
		public ResourceEntity(Book book, List<Review> reviews)
			{
				setResourceEntity(KeywordEntity.BOOKS.getEntity());
				setDescription("");
				setPriceTag(((book.getMrp() == null) || (book.getMrp() == -0.1f)) ? "NA" : "" + book.getMrp());
				setName(book.getBookTitle());
				setPrice(book.getCurrency(), book.getMrp());
				setPublication("" + book.getPublisher());
				setDetailUrl(book.getDetailPageURL());
				setIdentity(book.getISBN());
				if ((book.getLargeImageUrl() != null) && (book.getLargeImageUrl().trim().length() > 0))
					{
						setBigUrl("" + book.getLargeImageUrl());
					}
				if ((book.getSmallImageUrl() != null) && (book.getSmallImageUrl().trim().length() > 0))
					{
						setSmallUrl("" + book.getSmallImageUrl());
					}
				Integer rating = (int) ((book.getAverageRating() == null) ? 1 : Math.round(book.getAverageRating()));
				setRating(rating.intValue());
				String readMore = getReadMore();
				setAuthor(book.getAuthors());
				setRateCount(book.getRateCount());
				setReadMore(readMore);
				setReviews(reviews);
				setYear(book.getYear());
				setUniqueKey(book.getISBN());
				setSubject(book.getSubject());
				String isbn = book.getISBN();
				String charr = "ISBN-";
				if (isbn.trim().contains(charr.trim()))
					{
						isbn = isbn.replaceAll(charr, "");
					}
				setIsbn(isbn);
			}
			
		public ResourceEntity(CoachingClass coachingClass){
			setResourceEntity(KeywordEntity.COACHING_CLASSES.getEntity());
			setDescription(coachingClass.getDescription());
			setName(coachingClass.getName());
			setBranch(coachingClass.getBranch());
			setAddress(coachingClass.getAddress());
			setDetailUrl(coachingClass.getWebsite());
			setIdentity(coachingClass.getName() + "-" + coachingClass.getBranch() + "-");
			if ((coachingClass.getTypeOfProgram() != null) && (coachingClass.getTypeOfProgram().trim().length() > 0))
			{
				readMore += "Programs" + coachingClass.getTypeOfProgram() + "\n";
			}
			if ((coachingClass.getTargetStudents() != null) && (coachingClass.getTargetStudents().trim().length() > 0))
				{
					readMore += "Target Students : " + coachingClass.getTargetStudents() + "\n";
				}
			if ((coachingClass.getAverageBatchSize() != null) && (coachingClass.getAverageBatchSize() != 0))
				{
					readMore += "Average BatchSize : " + coachingClass.getAverageBatchSize();
				}
			setUniqueKey(coachingClass.getName());
			setSmallUrl("images/class.jpg");
			Integer rating = (int) ((coachingClass.getAverageRating() == null) ? 1 : Math.round(coachingClass.getAverageRating()));
			setRating(rating.intValue());
			setYear(coachingClass.getYearFounded());
			setWebsite(coachingClass.getWebsite());
			setStream(coachingClass.getCourses());
		}
		
		public ResourceEntity(CoachingClass coachingClass, List<Review> reviews)
			{
				setResourceEntity(KeywordEntity.COACHING_CLASSES.getEntity());
				setDescription(coachingClass.getDescription());
				setName(coachingClass.getName());
				setBranch(coachingClass.getBranch());
				setAddress(coachingClass.getAddress());
				setDetailUrl(coachingClass.getWebsite());
				setIdentity(coachingClass.getName() + "-" + coachingClass.getBranch() + "-");
				String readMore = getReadMore();
				if ((coachingClass.getTypeOfProgram() != null) && (coachingClass.getTypeOfProgram().trim().length() > 0))
					{
						readMore += "Programs" + coachingClass.getTypeOfProgram() + "\n";
					}
				if ((coachingClass.getTargetStudents() != null) && (coachingClass.getTargetStudents().trim().length() > 0))
					{
						readMore += "Target Students : " + coachingClass.getTargetStudents() + "\n";
					}
				if ((coachingClass.getAverageBatchSize() != null) && (coachingClass.getAverageBatchSize() != 0))
					{
						readMore += "Average BatchSize : " + coachingClass.getAverageBatchSize();
					}
				setUniqueKey(coachingClass.getName());
				setSmallUrl("images/class.jpg");
				setReadMore(readMore);
				setReviews(reviews);
				Integer rating = (int) ((coachingClass.getAverageRating() == null) ? 1 : Math.round(coachingClass.getAverageRating()));
				setRating(rating.intValue());
				setYear(coachingClass.getYearFounded());
				setWebsite(coachingClass.getWebsite());
				setStream(coachingClass.getCourses());
				
			}
			
		public ResourceEntity(ResultRow coachingClass, List<Review> reviews)
			{
				setResourceEntity(KeywordEntity.COACHING_CLASSES.getEntity());
				Object desc = coachingClass.getAdditionalProperties().get("description");
				setDescription((desc != null) ? (String) desc : null);
				setName(coachingClass.getName());
				setBranch(coachingClass.getBranch());
				setAddress(coachingClass.getAddress());
				setDetailUrl(coachingClass.getWebsite());
				setIdentity(coachingClass.getName() + "-" + coachingClass.getBranch() + "-");
				String readMore = getReadMore();
				if ((coachingClass.getTypeOfProgram() != null) && (coachingClass.getTypeOfProgram().trim().length() > 0))
					{
						readMore += "Programs" + coachingClass.getTypeOfProgram() + "\n";
					}
				if ((coachingClass.getTargetStudents() != null) && (coachingClass.getTargetStudents().trim().length() > 0))
					{
						readMore += "Target Students : " + coachingClass.getTargetStudents() + "\n";
					}
				//				if ((coachingClass.getAverageBatchSize() != null) && (coachingClass.getAverageBatchSize() != 0))
				//					{
				//						readMore += "Average BatchSize : " + coachingClass.getAverageBatchSize();
				//					}
				setUniqueKey(coachingClass.getName());
				setSmallUrl("images/class.jpg");
				setReadMore(readMore);
				setReviews(reviews);
				Integer rating = (int) ((coachingClass.getAverageRating() == null) ? 1 : Math.round(coachingClass.getAverageRating()));
				setRating(rating.intValue());
				Object yearFounded = coachingClass.getAdditionalProperties().get("yearFounded");
				setYear((yearFounded != null) ? (String) yearFounded : null);
				setWebsite(coachingClass.getWebsite());
				setStream(coachingClass.getCourses());
			}
		
		public ResourceEntity(DigitalTool digitalTool){
			setResourceEntity(KeywordEntity.DIGITAL_RESOURCES.getEntity());
			setDescription(digitalTool.getWhatDoesItDo());
			setName(digitalTool.getName());
			setBigUrl("images/digitalResource.png");
			setDetailUrl(digitalTool.getWebSite());
			setIdentity(digitalTool.getName());
			Integer rating = (int) ((digitalTool.getAverageRating() == null) ? 1 : Math.round(digitalTool.getAverageRating()));
			setRating(rating.intValue());
			setStream(digitalTool.getCareerStreams());
			setWebsite(digitalTool.getWebSite());
			setUniqueKey(digitalTool.getName());
			setSubject(digitalTool.getSubject());
		}
			
		public ResourceEntity(DigitalTool digitalTool, List<Review> reviews)
			{
				setResourceEntity(KeywordEntity.DIGITAL_RESOURCES.getEntity());
				setDescription(digitalTool.getWhatDoesItDo());
				setName(digitalTool.getName());
				setBigUrl("images/digitalResource.png");
				setDetailUrl(digitalTool.getWebSite());
				setIdentity(digitalTool.getName());
				Integer rating = (int) ((digitalTool.getAverageRating() == null) ? 1 : Math.round(digitalTool.getAverageRating()));
				setRating(rating.intValue());
				setReviews(reviews);
				setStream(digitalTool.getCareerStreams());
				setWebsite(digitalTool.getWebSite());
				setUniqueKey(digitalTool.getName());
				setSubject(digitalTool.getSubject());
			}
			
		public String[] getIndicatorArray1()
			{
				return indicatorArray1;
			}
			
		public void setIndicatorArray1(String[] indicatorArray1)
			{
				this.indicatorArray1 = indicatorArray1;
			}
			
		public String[] getIndicatorArray2()
			{
				return indicatorArray2;
			}
			
		public void setIndicatorArray2(String[] indicatorArray2)
			{
				this.indicatorArray2 = indicatorArray2;
			}
			
		public String[] getIndicatorArray3()
			{
				return indicatorArray3;
			}
			
		public void setIndicatorArray3(String[] indicatorArray3)
			{
				this.indicatorArray3 = indicatorArray3;
			}
			
		public String[] getIndicatorArray4()
			{
				return indicatorArray4;
			}
			
		public void setIndicatorArray4(String[] indicatorArray4)
			{
				this.indicatorArray4 = indicatorArray4;
			}
			
		public Integer getRateCount()
			{
				return rateCount;
			}
			
		public void setRateCount(Integer rateCount)
			{
				this.rateCount = rateCount;
			}
			
		public String getYear()
			{
				return year;
			}
			
		public void setYear(String year)
			{
				if (year == null)
					{
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						year = simpleDateFormat.format(new Date());
					}
				this.year = year;
			}
			
		public String getStream()
			{
				return stream;
			}
			
		public void setStream(String stream)
			{
				this.stream = stream;
			}
			
		public String getWebsite()
			{
				return website;
			}
			
		public void setWebsite(String website)
			{
				this.website = website;
			}
			
		public List<String> getResultCriterias()
			{
				return resultCriterias;
			}
			
		public void setResultCriterias(List<String> resultCriterias)
			{
				this.resultCriterias = resultCriterias;
			}
			
		public void setPrice(Float price)
			{
				this.price = price;
			}
			
		public String getSubject()
			{
				return subject;
			}
			
		public void setSubject(String subject)
			{
				this.subject = subject;
			}
			
		public String getIsbn()
			{
				return isbn;
			}
			
		public void setIsbn(String isbn)
			{
				this.isbn = isbn;
			}
			
	}
