package com.test;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.services.DigitalToolService;
import com.v2tech.webservices.DigitalToolWebService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestDigitalTools {
	@Autowired
	DigitalToolWebService digitalToolWebService ;
	
	@Autowired
	DigitalToolService digitalToolService;
	
	@Test
	public void uploadToolsExcel() throws Exception{
		//Book_Excel_DataCapture_March 16_v2.xlsx
		//DigitalToolWebService digitalToolWebService = new DigitalToolWebService();
		ContentDisposition cd = new ContentDisposition("attachment;filename=Coaching Classes_Medical_Excel.xlsx");
		   List<Attachment> atts = new LinkedList<Attachment>();
		   FileInputStream fis = new FileInputStream("C:\\Users\\jsutaria\\git\\v2booksys\\Digital Tools Repository_InThisFormat.xls");
		   Attachment att = new Attachment("root", fis, cd);
		   atts.add(att);
		   digitalToolWebService.uploadDigitalToolsExcel(att, "test");
	}

}
