package com.v2tech.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Rating;
@Repository
public interface RatingRepository extends GraphRepository<Rating>{
	

}
