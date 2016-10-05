package com.v2tech.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.OnetReportData;

@Repository
public interface OnetReportDataRepository extends GraphRepository<OnetReportData>{
	
	@Query("MATCH (rep:OnetReportData) WHERE rep.user = {0} AND rep.socialMediaType={1}  return rep;")
	public List<OnetReportData> findOnetReportForUser(String user, String socialMedia) ;

}
