package com.v2tech.webservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2.booksys.common.util.EmailFeedbackThread;
import com.v2tech.domain.Course;
import com.v2tech.domain.CourseType;
import com.v2tech.repository.CourseRepository;
import com.v2tech.services.CourseService;

@Path("/utilService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UtilWebService {
	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseRepository  coucourseRepository;
	
	@POST
	@Path("/feedbackEmail/email/{email}/name/{name}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendFeedbackEmail(@PathParam("email") String email, @PathParam("name") String name, String message){
		EmailFeedbackThread emailFeedbackThread = new EmailFeedbackThread(email, name, message);
		Thread t = new Thread(emailFeedbackThread);
		t.start();
		return Response.ok().build();
	}
	
	@GET
	@Path("/courseTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllCourseTypes(){
		CourseType[] types = CourseType.values();
		List<String> tps = new ArrayList<String>();
		for(CourseType type : types){
			tps.add(type.toString());
		}
		return Response.ok().entity(tps).build();
	}
	
	@GET
	@Path("/courses/courseType/{courseType}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response fetchAllCoursesForType(@PathParam("courseType") String courseType){
		Set<Course> courses = coucourseRepository.getCourses(courseType, 10);
		return Response.ok().entity(courses).build();
	}

}
