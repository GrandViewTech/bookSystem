package com.test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.Book;
import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.SearchResponse;
import com.v2tech.repository.CoachingClassRepository;
import com.v2tech.services.CoachingClassService;
import com.v2tech.services.CoachingClassService1;
import com.v2tech.webservices.CoachingClassWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestCoachingClass {
	
@Autowired	
CoachingClassService1 coachingClassService;

@Autowired
CoachingClassWebService coachingClassWebService;

@Autowired
CoachingClassRepository coachingClassRepository;


@Test
@Rollback(value=false)
public void testDuplicates(){
	Result<CoachingClass> classes = coachingClassRepository.findAll();
	Map<String,CoachingClass> map = new HashMap<>();
	Iterator<CoachingClass> itr = classes.iterator();
	List<CoachingClass> list = new ArrayList<>();
		while(itr.hasNext()){
			CoachingClass bk = itr.next();
			if(map.get(bk.getName()+"-"+bk.getBranch()+"-"+bk.getZip()) == null){
				map.put(bk.getName()+"-"+bk.getBranch()+"-"+bk.getZip(), bk);
			}
			else{
				list.add(bk);
			}
		}
	for(CoachingClass bk : list){
		System.out.println(bk.getName()+"-"+bk.getBranch()+"-"+bk.getZip());
		coachingClassRepository.delete(bk);
	}
}

	@Test
	public void testcoachingClass(){
		CoachingClass coachingClass = new CoachingClass();
		coachingClass.setAddress("test");
		coachingClass.setcStreams("Engineering");
		coachingClass.setLocation("Juhu");
		coachingClass.setName("Alps Academy");
		//coachingClassService.addCoachingClass(coachingClass);
	}
	
	@Test
	public void testSearchCoachingClasses(){
		String value = "(?i).*Maths.*";
		SearchResponse response = coachingClassWebService.searchCoachingClass(value, "test");
		System.out.println(response.getClasses().size());
		
	}
	
	
	@Test
	public void testCoachingClassesUploadExcelLocally(){
		try {
			   ContentDisposition cd = new ContentDisposition("attachment;filename=Coaching Classes_Medical_Excel.xlsx");
			   List<Attachment> atts = new LinkedList<Attachment>();
			   FileInputStream fis = new FileInputStream("D:\\jatin\\shalini\\ExcelData\\Coaching Classes_Medical_Excel.xlsx");
			   Attachment att = new Attachment("root", fis, cd);
			   atts.add(att);
			   coachingClassWebService.uploadCoachingClassExcel(att, "test");
			   Assert.assertEquals(true, true);
			} catch(Exception e){
				e.printStackTrace();
				Assert.assertEquals(true, false);
				//logErrorStack(e);
			}	
	}
}
