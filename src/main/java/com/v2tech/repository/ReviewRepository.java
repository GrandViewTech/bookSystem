package com.v2tech.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.v2tech.domain.Review;

public interface ReviewRepository extends GraphRepository<Review>
	{
		
		@Query("MATCH (review:Review) WHERE review.userName = {0} AND review.resourceName={1} return review LIMIT 1;")
		public Review getReviewByUserNameAndResourceName(String user, String resourceName);
		
		@Query("MATCH (review:Review) WHERE review.reviewedBy =~ {0} AND review.resourceReviewedType=~ {1} AND review.resourceIdentity=~ {2} return review LIMIT 1;")
		public Review getReviewByReviewedByResourceReviewedTypeAndResourceIdentity(String reviewedBy, String resourceReviewedType, String resourceIdentity);
		
		@Query("MATCH (review:Review) WHERE review.reviewedBy =~ {0} AND review.resourceReviewedType=~ {1} AND review.resourceIdentity=~ {2} AND review.location=~ {3}  return review LIMIT 1;")
		public Review getReviewByReviewedByResourceReviewedTypeResourceIdentityAndLocation(String reviewedBy, String resourceReviewedType, String resourceIdentity ,String location);
		
		@Query("MATCH (review:Review) WHERE review.resourceReviewedType=~ {0}  AND review.resourceIdentity=~ {1} return review ORDER BY review.reviewScore DESC LIMIT 5 ;")
		public List<Review> getReviewByResourceReviewedTypeAndResourceIdentity(String resourceReviewedType, String resourceIdentity , Integer limit);
		
		@Query("MATCH (n:Review) where n.userName =~ {0} return n ORDER BY n.id SKIP {1} LIMIT {2}")
		public Set<Review> fetchAllReviews(String user, int start, int max);
		
	}
