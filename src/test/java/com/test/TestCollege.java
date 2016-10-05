package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.College;
import com.v2tech.services.CollegeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestCollege {

	@Autowired
	CollegeService collegeService;
	
	@Test
	public void testSaveCollege(){
		College college1 = new College();
		college1.setName("Dina Mama Patil College of Management");
		college1.setCourse("MBA/PGDM");
		college1.setStream("Marketing");
		college1.setAddress("Mumbai");
		college1.setPincode(400067l);
		collegeService.saveOrUpdate(college1);
		
		
		college1 = new College();
		college1.setName("K. J. Somaiya Institute of Management Studies and Research SIMSR");
		college1.setCourse("MBA/PGDM");
		college1.setStream("Marketing");
		college1.setAddress("Mumbai");
		college1.setPincode(400087l);
		collegeService.saveOrUpdate(college1);
		
		
		college1 = new College();
		college1.setName("IES Management College and Research Centre");
		college1.setCourse("MBA/PGDM");
		college1.setStream("Marketing");
		college1.setAddress("Mumbai");
		college1.setPincode(400067l);
		collegeService.saveOrUpdate(college1);
		
		
		college1 = new College();
		college1.setName("ASM's Institute of Management & Computer Studies, IMCOST");
		college1.setCourse("MBA/PGDM");
		college1.setStream("Marketing");
		college1.setAddress("Mumbai");
		college1.setPincode(400067l);
		collegeService.saveOrUpdate(college1);
		
		college1 = new College();
		college1.setName("Sydenham Institute of Management Studies, Research and Entrepreneurship Education SIMSREE");
		college1.setCourse("MBA/PGDM");
		college1.setStream("Marketing");
		college1.setAddress("Mumbai");
		college1.setPincode(400067l);
		collegeService.saveOrUpdate(college1);
		
		college1 = new College();
		college1.setName("Thadomal Shahani Centre for Management TSCFM");
		college1.setCourse("MBA/PGDM");
		college1.setStream("Marketing");
		college1.setAddress("Mumbai");
		college1.setPincode(400067l);
		collegeService.saveOrUpdate(college1);
	}
	
	
}
