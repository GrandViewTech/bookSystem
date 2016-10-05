
package com.v2tech.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zefer.html.doc.s;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Book;
import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.DigitalTool;
import com.v2tech.domain.RESOURCE_TYPE;
import com.v2tech.domain.Review;
import com.v2tech.repository.BookRepository;
import com.v2tech.repository.CoachingClassRepository;
import com.v2tech.repository.DigitalToolRepository;
import com.v2tech.repository.ResourceUnderReviewRepository;
import com.v2tech.repository.ReviewRepository;

@Service
public class ReviewService
	{
		@Autowired
		ReviewRepository				reviewRepository;
		
		@Autowired
		BookRepository					bookRepository;
		
		@Autowired
		BookService						bookService;
		
		@Autowired
		ResourceUnderReviewRepository	resourceUnderReviewRepository;
		
		@Autowired
		ResourceUnderReviewService		resourceUnderReviewService;
		
		@Autowired
		CoachingClassRepository			coachingClassRepository;
		
		@Autowired
		DigitalToolRepository			digitalToolRepository;
		
		public void saveOrUpdate(Review review) throws V2GenericException
			{
				if ((review.getReviewedBy() == null) || (review.getResourceReviewedType() == null))
					{
						throw new V2GenericException("Invalid data");
					}
				if ((review.getReviewedBy().length() == 0) || (review.getResourceReviewedType().length() == 0))
					{
						throw new V2GenericException("Invalid data");
					}
				review.setReviewedBy(review.getReviewedBy().trim());
				review.setResourceReviewedType(review.getResourceReviewedType());
				String reviewedBy = review.getReviewedBy().trim();
				String resourceReviewedType = review.getResourceReviewedType().trim();
				String resourceIdentity = review.getResourceIdentity().trim();
				Review existingReview = null;
				
				if (review.getResourceReviewedType().equalsIgnoreCase(RESOURCE_TYPE.BOOK.getType()))
					{
						if (reviewedBy.trim().equalsIgnoreCase("anonymous") == false)
							{
								existingReview = reviewRepository.getReviewByReviewedByResourceReviewedTypeAndResourceIdentity("(?i)" + reviewedBy, "(?i)" + resourceReviewedType, "(?i)" + resourceIdentity);
							}
						reviewBook(review, existingReview);
					}
				else if (review.getResourceReviewedType().equalsIgnoreCase(RESOURCE_TYPE.COACHING_CLASS.getType()))
					{
						if (reviewedBy.trim().equalsIgnoreCase("anonymous") == false)
							{
								String location = review.getLocation();
								existingReview = reviewRepository.getReviewByReviewedByResourceReviewedTypeResourceIdentityAndLocation("(?i)" + reviewedBy, "(?i)" + resourceReviewedType, "(?i)" + resourceIdentity, "(?i)" + location);
							}
						reviewCoachingClasses(review, existingReview);
					}
				else if (review.getResourceReviewedType().equalsIgnoreCase(RESOURCE_TYPE.DIGITAL_RESOURCE.getType()))
					{
						if (reviewedBy.trim().equalsIgnoreCase("anonymous") == false)
							{
								existingReview = reviewRepository.getReviewByReviewedByResourceReviewedTypeAndResourceIdentity("(?i)" + reviewedBy, "(?i)" + resourceReviewedType, "(?i)" + resourceIdentity);
							}
						reviewDigitalResources(review, existingReview);
					}
					
			}
			
		public List<Review> getReviewByResourceReviewedTypeAndResourceIdentity(String resourceReviewedType, String resourceIdentity, Integer limit)
			{
				resourceReviewedType = ("(?i)" + resourceReviewedType).trim();
				resourceIdentity = ("(?i)" + resourceIdentity).trim();
				List<Review> reviews = reviewRepository.getReviewByResourceReviewedTypeAndResourceIdentity(resourceReviewedType, resourceIdentity, limit);
				return reviews;
			}
			
		public void deleteAll()
			{
				reviewRepository.deleteAll();
			}
			
		private void reviewBook(Review review, Review existingReview)
			{
				Double reviewScore = 0.0;
				Integer totalValue = 0;
				int numberOrReviewedCriteria = 4;
				Book book = null;
				String resourceIdentity = review.getResourceIdentity();
				Integer visualTools = review.getVisualTools();
				Integer solvedExamples = review.getSolvedExamples();
				Integer solutionToPracticeProblems = review.getSolutionToPracticeProblems();
				Integer effectivenessAndEaseOfCommunication = review.getEffectivenessAndEaseOfCommunication();
				totalValue = (visualTools + solvedExamples + effectivenessAndEaseOfCommunication + solutionToPracticeProblems);
				String comment = review.getComment().trim();
				Integer limit = 1;
				Set<Book> books = bookService.searchBooksByISBN(resourceIdentity, limit);
				if (books.size() == 0)
					{
						throw new V2GenericException("Invalid ResourceIdentity " + resourceIdentity + " |  ResourceReviewType : Book | IdentityType : ISBN Number");
					}
				for (Book bk : books)
					{
						book = bk;
						break;
					}
				review.setResourceIdentity(resourceIdentity);
				reviewScore = totalValue.doubleValue() / numberOrReviewedCriteria;
				review.setReviewScore(reviewScore);
				if (existingReview != null)
					{
						
						// UPDATE DATA
						existingReview.setComment(comment);
						existingReview.setReviewScore(reviewScore);
						existingReview.setVisualTools(visualTools);
						existingReview.setSolvedExamples(solvedExamples);
						existingReview.setSolutionToPracticeProblems(solutionToPracticeProblems);
						existingReview.setEffectivenessAndEaseOfCommunication(effectivenessAndEaseOfCommunication);
						reviewRepository.save(existingReview);
					}
				else
					{
						List<String> reviewedSubjects=review.getReviewiedSubjects();
						if(reviewedSubjects.isEmpty())
							{
								reviewedSubjects.add("Physic");
								reviewedSubjects.add("Chemistry");
								reviewedSubjects.add("Maths");
							}
						review.setReviewScore(reviewScore);
						reviewRepository.save(review);
					}
				Integer rateCount = ((book.getRateCount() == null) ? 0 : book.getRateCount());
				Double averageRating = ((book.getAverageRating() == null) ? 0 : book.getAverageRating()) * rateCount + reviewScore;
				if (rateCount > 1 || rateCount < 1)
					{
						rateCount=rateCount+1;
					}
				book.setAverageRating(averageRating / rateCount);
				book.setRateCount(rateCount);
				bookRepository.save(book);
				
			}
			
		private void reviewCoachingClasses(Review review, Review existingReview)
			{
				Double reviewScore = 0.0;
				Integer totalValue = 0;
				int numberOrReviewedCriteria = 4;
				CoachingClass coachingClass = null;
				String resourceIdentity = review.getResourceIdentity();
				Integer faculty = review.getFaculty();
				Integer studyMaterial = review.getStudyMaterial();
				Integer personalization = review.getPersonalization();
				Integer infrastructure = review.getInfrastructure();
				totalValue = (faculty + personalization + studyMaterial + infrastructure);
				String comment = review.getComment().trim();
				String name = ("(?i)" + resourceIdentity).trim();
				String branch = ("(?i)" + review.getLocation()).trim();
				Set<CoachingClass> coachingClasses = coachingClassRepository.findByNameAndBranch(name, branch);
				if (coachingClasses.size() == 0)
					{
						throw new V2GenericException("Invalid ResourceIdentity " + resourceIdentity + " |  ResourceReviewType : CoachingClass | IdentityType : Name");
					}
				for (CoachingClass ch : coachingClasses)
					{
						coachingClass = ch;
						break;
					}
				review.setResourceIdentity(resourceIdentity);
				reviewScore = totalValue.doubleValue() / numberOrReviewedCriteria;
				review.setReviewScore(reviewScore);
				if (existingReview != null)
					{
						
						// UPDATE DATA
						existingReview.setComment(comment);
						existingReview.setReviewScore(reviewScore);
						existingReview.setFaculty(faculty);
						existingReview.setStudyMaterial(studyMaterial);
						existingReview.setInfrastructure(infrastructure);
						existingReview.setPersonalization(personalization);
						reviewRepository.save(existingReview);
					}
				else
					{
						review.setReviewScore(reviewScore);
						reviewRepository.save(review);
					}
				Integer rateCount = ((coachingClass.getRateCount() == null) ? 0 : coachingClass.getRateCount());
				Double averageRating = ((coachingClass.getAverageRating() == null) ? 0 : coachingClass.getAverageRating()) * rateCount + reviewScore;
				if (rateCount > 1 || rateCount < 1)
					{
						rateCount=rateCount+1;
					}
				coachingClass.setAverageRating(averageRating / rateCount);
				coachingClass.setRateCount(rateCount);
				coachingClassRepository.save(coachingClass);
			}
			
		private void reviewDigitalResources(Review review, Review existingReview)
			{
				Double reviewScore = 0.0;
				Integer totalValue = 0;
				int numberOrReviewedCriteria = 4;
				DigitalTool digitalTool = null;
				String resourceIdentity = review.getResourceIdentity();
				Integer easyOfUse = review.getEasyOfUse();
				Integer studyMaterial = review.getStudyMaterial();
				Integer personalization = review.getPersonalization();
				Integer interactivity = review.getInteractivity();
				totalValue = (easyOfUse + personalization + studyMaterial + interactivity);
				String comment = review.getComment().trim();
				String name = ("(?i)" + resourceIdentity).trim();
				Set<DigitalTool> digitalTools = digitalToolRepository.findDigitalToolByName(name);
				if (digitalTools.size() == 0)
					{
						throw new V2GenericException("Invalid ResourceIdentity " + resourceIdentity + " |  ResourceReviewType : DigitalResource | IdentityType : Name");
					}
				for (DigitalTool dt : digitalTools)
					{
						digitalTool = dt;
						break;
					}
				review.setResourceIdentity(resourceIdentity);
				reviewScore = totalValue.doubleValue() / numberOrReviewedCriteria;
				review.setReviewScore(reviewScore);
				if (existingReview != null)
					{
						
						// UPDATE DATA
						existingReview.setComment(comment);
						existingReview.setReviewScore(reviewScore);
						existingReview.setEasyOfUse(easyOfUse);
						existingReview.setStudyMaterial(studyMaterial);
						existingReview.setInteractivity(interactivity);
						existingReview.setPersonalization(personalization);
						reviewRepository.save(existingReview);
					}
				else
					{
						review.setReviewScore(reviewScore);
						reviewRepository.save(review);
					}
				Integer rateCount = ((digitalTool.getRateCount() == null) ? 0 : digitalTool.getRateCount());
				Double averageRating = ((digitalTool.getAverageRating() == null) ? 0 : digitalTool.getAverageRating()) * rateCount + reviewScore;
				if (rateCount > 1 || rateCount < 1)
					{
						rateCount=rateCount+1;
					}
				digitalTool.setAverageRating(averageRating / rateCount);
				digitalTool.setRateCount(rateCount);
				digitalToolRepository.save(digitalTool);
			}
			
	}
