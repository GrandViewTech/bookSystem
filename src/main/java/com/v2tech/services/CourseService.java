package com.v2tech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Course;
import com.v2tech.repository.CourseRepository;

@Service
public class CourseService {
@Autowired
CourseRepository courseRepository;


	public Course saveOrUpdate(Course course){
		
		if(course == null || course.getCourseName() == null || course.getCourseType() == null){
			throw new V2GenericException("Invalid data");
		}
		
		course.setCourseName(course.getCourseName().trim());
		course.setCourseType(course.getCourseType().trim());
		if(course.getCourseName().length() == 0 || course.getCourseType().length() == 0){
			throw new V2GenericException("Invalid data");
		}
		
		Course course2 = courseRepository.getCourseByCourseNameAndType(course.getCourseName(), course.getCourseType());
		if(course2 == null){
			//create
			return courseRepository.save(course);
		}
		return course2;
	
	}

}
