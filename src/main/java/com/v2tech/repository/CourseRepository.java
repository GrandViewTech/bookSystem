package com.v2tech.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Course;
@Repository
public interface CourseRepository extends GraphRepository<Course> {

	@Query("MATCH (cr:Course) WHERE cr.courseType =~ {0} return cr LIMIT {1};")
	public Set<Course> getCourses(String courseType, Integer limit);
	
	@Query("MATCH (cr:Course) WHERE cr.courseName =~ {0} AND cr.courseType =~ {1} return cr LIMIT 1;")
	public Course getCourseByCourseNameAndType(String courseName, String courseType);
}
