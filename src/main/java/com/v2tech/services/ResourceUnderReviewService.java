package com.v2tech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.ResourceUnderReview;
import com.v2tech.repository.ResourceUnderReviewRepository;

@Service
public class ResourceUnderReviewService {
	
	@Autowired
	ResourceUnderReviewRepository resourceUnderReviewRepository;
	
	
	public ResourceUnderReview saveOrUpdate(ResourceUnderReview resource){
		if(resource.getResourceName() == null){
			throw new V2GenericException("Review without Resurce Name");
		}
		 
		ResourceUnderReview resourceUnderReview = resourceUnderReviewRepository.getResourceUnderReviewByResourceName(resource.getResourceName());
		if(resourceUnderReview == null){
			return resourceUnderReviewRepository.save(resource);
		}
		else{
			resourceUnderReview.setCriteria(resource.getCriteria());
			resourceUnderReview.setResourceTitle(resource.getResourceTitle());
			resourceUnderReview.setAverageRatingForResource(resource.getAverageRatingForResource());
			resourceUnderReview.setLinkURLToResource(resource.getLinkURLToResource());
			resourceUnderReview.setNumberOfTimesDiscussed(resource.getNumberOfTimesDiscussed());
			resourceUnderReview.setNumberOfTimesFavourited(resource.getNumberOfTimesFavourited());
			resourceUnderReview.setNumberOfTimesReviewed(resource.getNumberOfTimesReviewed());
			return resourceUnderReviewRepository.save(resourceUnderReview);
		}
	}
	
}
