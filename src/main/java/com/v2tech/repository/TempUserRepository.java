package com.v2tech.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.TempUser;

@Repository
public interface TempUserRepository extends GraphRepository<TempUser>{
	
	@Query("MATCH (user:TempUserRepository) WHERE user.email =~ {0} return user LIMIT {1};")
	public TempUser findTempUser(String email);
	

}
