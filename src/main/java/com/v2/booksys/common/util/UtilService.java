package com.v2.booksys.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.Subject;

public class UtilService {
static org.slf4j.Logger logger = LoggerFactory.getLogger(UtilService.class);

private static Properties props = new Properties();	
	static{
		try {
			FileInputStream fis = new FileInputStream("bookSys.properties");
			props.load(fis);
		} catch (FileNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}
	
	public static Map<CareerStream,Subject> getCareerStreamSubjectKeywords(){
		try {
			List<String> lines = FileUtils.readLines(new File("keywords/CareerStream-Subject.txt"));
			Map<CareerStream,Subject> results = new HashMap<>();
				for(String line : lines){
					StringTokenizer stk = new StringTokenizer(line, "-");
					if(stk.countTokens() != 2){
						V2GenericException e =  new V2GenericException("Wrong keyword in CareerStream-Subject.txt "+line);
						logger.error("Wrong keyword in CareerStream-Subject.txt "+line, e);
						e.printStackTrace();
					}else{
						String careerStream = stk.nextToken();
						String subject = stk.nextToken();
						CareerStream careerStream2 = new CareerStream(careerStream.trim());
						Subject subject2 = new Subject(subject.trim());
						results.put(careerStream2, subject2);
						
					}
					
				}
		return results;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String,Subject> getInstitutionTypeSubjectKeywords(){
		try {
			List<String> lines = FileUtils.readLines(new File("keywords/InstitutionType-Subject.txt"));
			Map<String,Subject> results = new HashMap<>();
				for(String line : lines){
					StringTokenizer stk = new StringTokenizer(line, "-");
					if(stk.countTokens() != 2){
						V2GenericException e =  new V2GenericException("Wrong keyword in CareerStream-Subject.txt "+line);
						logger.error("Wrong keyword in CareerStream-Subject.txt "+line, e);
						e.printStackTrace();
					}else{
						String institutionType = stk.nextToken();
						String subject = stk.nextToken();
						Subject subject2 = new Subject(subject.trim());
						results.put(institutionType.trim(), subject2);
						
					}
					
				}
		return results;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<Subject, Exam> getSubjectExamKeywords(){
		try {
			List<String> lines = FileUtils.readLines(new File("keywords/Subject-Exam.txt"));
			Map<Subject, Exam> results = new HashMap<>();
				for(String line : lines){
					StringTokenizer stk = new StringTokenizer(line, "-");
					if(stk.countTokens() != 2){
						V2GenericException e =  new V2GenericException("Wrong keyword in CareerStream-Subject.txt "+line);
						logger.error("Wrong keyword in CareerStream-Subject.txt "+line, e);
						e.printStackTrace();
					}else{
						
						String subject = stk.nextToken();
						String exam = stk.nextToken();
						Subject subject2 = new Subject(subject.trim());
						Exam exam2 = new Exam(exam.trim());
						results.put( subject2, exam2);
						
					}
					
				}
		return results;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
