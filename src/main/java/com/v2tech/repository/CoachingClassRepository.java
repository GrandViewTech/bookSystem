package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.CoachingClass;

@Repository
public interface CoachingClassRepository extends GraphRepository<CoachingClass>
	{
		@Query("MATCH (class:CoachingClass) WHERE class.keyword =~ {0} AND class.city =~ {1} return class LIMIT {2};")
		public Set<CoachingClass> findCoachingClassByKeywordAndCity(String keyword, String city, Integer limit);
		
		@Query("MATCH (class:CoachingClass) WHERE class.city =~ {0} return class LIMIT {1};")
		public Set<CoachingClass> findCoachingClassByCity(String city, Integer limit);
		
		@Query("MATCH (class:CoachingClass) WHERE class.name =~ {0} AND class.location =~ {1} return class ")
		public Set<CoachingClass> findCoachingClassByNameAndLocation(String name, String location);
		
		@Query("MATCH (class:CoachingClass) WHERE class.name =~ {0} AND class.branch =~ {1} AND class.zip =~ {2}   return class;")
		public Set<CoachingClass> findByNameAndBranchAndZipCode(String name, String branch, String zip);
		
		@Query("MATCH (class:CoachingClass) WHERE class.name =~ {0} AND class.branch =~ {1} return class;")
		public Set<CoachingClass> findByNameAndBranch(String name, String branch);
		
		@Query("MATCH (class:CoachingClass) WHERE class.name =~ {0}  return class;")
		public Set<CoachingClass> searchAllCoachingClassesByName(String name);
		
		@Query("MATCH (class:CoachingClass) WHERE  class.searchable ='yes' AND (class.keyword =~ {0} OR class.name =~ {0} OR class.typeOfProgram =~ {0}  OR class.courses =~ {0}  OR class.rExams =~ {0}  OR class.cStreams =~ {0} ) RETURN class LIMIT {1};")
		public Set<CoachingClass> searchCoachingClassByGenericKeyword(String keyword, Integer limit);
		
	}
