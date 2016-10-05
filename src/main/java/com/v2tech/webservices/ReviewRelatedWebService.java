package com.v2tech.webservices;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.BookMarkedResource;
import com.v2tech.domain.ResourceUnderReview;
import com.v2tech.domain.Review;
import com.v2tech.repository.BookedMarkedResourceRepository;
import com.v2tech.repository.ResourceUnderReviewRepository;
import com.v2tech.services.BookedMarkedResourceService;
import com.v2tech.services.ReviewService;

@Path("/reviewRelatedService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ReviewRelatedWebService
	{
		@Autowired
		ReviewService					reviewService;
		
		@Autowired
		ResourceUnderReviewRepository	resourceUnderReviewRepository;
		
		@Autowired
		BookedMarkedResourceService bookedMarkedResourceService;
		
		@Autowired
		BookedMarkedResourceRepository bookedMarkedResourceRepository;
		
		@POST
		@Path("/createBookmark/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public BookMarkedResource createBookmark(BookMarkedResource bookMarkedResource,  @PathParam("token") String token){
			return bookedMarkedResourceService.saveOrUpdate(bookMarkedResource);
		}
		
		@GET
		@Path("/fetchBookMarks/user/{user}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public List<BookMarkedResource> fetchBookMarks(@PathParam("user") String user,  @PathParam("token") String token){
			return bookedMarkedResourceRepository.fetchAllResourcesForUser(user);
		}
		
		@POST
		@Path("/saveOrUpdateReview/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public boolean addOrUpdateReviewRating(Review review, @PathParam("token") String token)
			{
				reviewService.saveOrUpdate(review);
				return true;
			}
			
		@GET
		@Path("/topRatedResources/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<ResourceUnderReview> getTopRatedResources(@PathParam("limit") String limit, @PathParam("token") String token)
			{
				int lim = getLimit(limit);
				Set<ResourceUnderReview> resources = resourceUnderReviewRepository.getTopResourcesByRating(lim);
				return resources;
			}
			
		@GET
		@Path("/topRatedBooks/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<ResourceUnderReview> getTopRatedBooks(@PathParam("limit") String limit, @PathParam("token") String token)
			{
				int lim = getLimit(limit);
				Set<ResourceUnderReview> resources = resourceUnderReviewRepository.getTopBooksByRating(lim);
				return resources;
			}
			
		@GET
		@Path("/topRatedCoachingClasses/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<ResourceUnderReview> getTopRatedCoachingClasses(@PathParam("limit") String limit, @PathParam("token") String token)
			{
				int lim = getLimit(limit);
				Set<ResourceUnderReview> resources = resourceUnderReviewRepository.getTopCoachingClassesByRating(lim);
				return resources;
			}
			
		private Integer getLimit(String limit)
			{
				Integer lim = 4;
				try
					{
						lim = Integer.parseInt(limit);
					}
				catch (Exception e)
					{
						
					}
				return lim;
			}
			
		@GET
		@Path("/topRatedDigitalResources/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<ResourceUnderReview> getTopRatedDigitalResources(@PathParam("limit") String limit, @PathParam("token") String token)
			{
				
				int lim = getLimit(limit);
				Set<ResourceUnderReview> resources = resourceUnderReviewRepository.getTopDigitalResourcesByRating(lim);
				return resources;
			}
			
		@GET
		@Path("/topRatedColleges/criteria/{criteria}/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<ResourceUnderReview> getTopRatedCollegesByCriteria(@PathParam("criteria") String criteria, @PathParam("limit") String limit, @PathParam("token") String token)
			{
				Integer lim = getLimit(limit);
				Set<ResourceUnderReview> resources = resourceUnderReviewRepository.getTopCollegesByCriteriaAndRating(criteria, lim);
				return resources;
			}
			
		@GET
		@Path("/topRatedBooks/criteria/{criteria}/limit/{limit}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Set<ResourceUnderReview> getTopRatedBooksByCriteria(@PathParam("criteria") String criteria, @PathParam("limit") String limit, @PathParam("token") String token)
			{
				Integer lim = getLimit(limit);
				Set<ResourceUnderReview> resources = resourceUnderReviewRepository.getTopBooksByCriteriaAndRating(criteria, lim);
				return resources;
			}
			
	}
