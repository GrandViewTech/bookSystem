package com.v2tech.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.BookMarkedResource;

@Repository
public interface BookedMarkedResourceRepository  extends GraphRepository<BookMarkedResource>{

	
	@Query("MATCH (bk:BookMarkedResource) WHERE bk.resourceId = {0} AND bk.userId = {1}  return bk;")
	public Set<BookMarkedResource> findBookMarkedResourceByResourceIdAndResourceId(String resourceId, String userId);
	
	@Query("MATCH (bk:BookMarkedResource) WHERE bk.userId = {0}  return bk;")
	public List<BookMarkedResource> fetchAllResourcesForUser(String userId);
	
	
}
