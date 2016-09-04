package com.v2tech.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.BookMarkedResource;
import com.v2tech.repository.BookedMarkedResourceRepository;

@Service
public class BookedMarkedResourceService {
	@Autowired
	BookedMarkedResourceRepository bookedMarkedResourceRepository;

	public BookMarkedResource findBookedMarkedResource(String userId, String resourceId){
		if(userId == null || resourceId == null || userId.trim().length() == 0 || resourceId.trim().length() == 0){
			return null;
		}
		Set<BookMarkedResource> resources = bookedMarkedResourceRepository.findBookMarkedResourceByResourceIdAndResourceId(resourceId, userId);
		if(resources.size() == 0){
			return null;
		}
		
		if(resources.size() > 1){
			throw new V2GenericException("BookMarkedResource can not be more than 1 for a combination of user id and resource id");
		}
		
		BookMarkedResource resourceArray[] = new BookMarkedResource[resources.size()];
		return (resources.toArray(resourceArray))[0];
		
	}
	
	public BookMarkedResource saveOrUpdate(BookMarkedResource bookMarkedResource){
		if(bookMarkedResource.getResourceId() == null || bookMarkedResource.getResourceId().trim().length() == 0 || bookMarkedResource.getUserId() == null || bookMarkedResource.getUserId().trim().length() == 0){
			throw new V2GenericException("Insufficient information to save a BookMarkedResource");
		}
		
		BookMarkedResource bookMarkedResource2 = findBookedMarkedResource(bookMarkedResource.getUserId(), bookMarkedResource.getResourceId());
		if(bookMarkedResource2 == null){
			//create
			return bookedMarkedResourceRepository.save(bookMarkedResource);
		}
		else{
			//update
			//do nothing
			return bookMarkedResource2;
		}
	}
}
