package com.v2tech.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.User_Template;

@Repository
public interface User_TemplateRepository extends GraphRepository<User_Template>{
	
	@Query("MATCH (ut:User_Template) WHERE ut.user = {0} AND ut.socialMediaType = {1} AND ut.temlplateType = {2} return ut;")
	public List<User_Template> getUserTemplate(String user, String socialMedia, String temlplateType);
	
	
	@Query("MATCH (ut:User_Template) WHERE ut.user =~ {0}  return ut;")
	public List<User_Template> searchAllUserTemplatesByName(String name) ;

}
