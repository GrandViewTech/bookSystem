package com.v2tech.webservices;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.v2.booksys.common.util.EmailFeedbackThread;
import com.v2tech.domain.Course;
import com.v2tech.domain.CourseType;
import com.v2tech.domain.TempUser;
import com.v2tech.repository.CourseRepository;
import com.v2tech.repository.TempUserRepository;
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
	
	@Autowired
	TempUserRepository tempUserRepository;
	
	@GET
	@Path("/topics/subject/{subject}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopicsForSubject(@PathParam("subject") String subject, @PathParam("token") String token){
		try {
			//java.io.InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(subject+".txt");
			String path = "subjects"+File.separator+subject+".txt";
			List<String> topics = FileUtils.readLines(new File(path));
			return Response.ok().entity(topics).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
	}
	
	@POST
	@Path("/feedbackEmail/email/{email}/name/{name}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendFeedbackEmail(@PathParam("email") String email, @PathParam("name") String name, String message){
		EmailFeedbackThread emailFeedbackThread = new EmailFeedbackThread(email, name, message);
		Thread t = new Thread(emailFeedbackThread);
		t.start();
		return Response.ok().build();
	}
	
	
	@POST
	@Path("/tempUser/email/{email}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendTempUser(@PathParam("email") String email,  @PathParam("token") String token){
		if(email != null || email.trim().length() != 0){
			TempUser tempUser = new TempUser();
			tempUser.setEmail(email);
			tempUserRepository.save(tempUser);
		}
		//tempUserRepository.save(arg0)
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
