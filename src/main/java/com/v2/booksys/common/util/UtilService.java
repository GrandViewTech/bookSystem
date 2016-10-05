package com.v2.booksys.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.Subject;

public class UtilService
	{
		static org.slf4j.Logger		logger	= LoggerFactory.getLogger(UtilService.class);
		
		private static Properties	props	= new Properties();
		
		static
			{
				try
					{
						Resource resource = new ClassPathResource("bookSys.properties");
						props.load(resource.getInputStream());
						String resourceLocation = props.getProperty("resourceLocation");
						if (resourceLocation != null && resourceLocation.trim().length() > 0)
							{
								String path = resourceLocation + File.separator + "configuration";
								File dir = new File(path);
								if (dir.isDirectory() == false)
									{
										FileUtils.forceMkdir(dir);
									}
								File file = new File(path + File.separator + "configuration.json");
								if (file.exists() == true)
									{
										file.delete();
									}
								file.createNewFile();
								ObjectMapper objectMapper = new ObjectMapper();
								ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
								FileWriter fileWriter = new FileWriter(file);
								fileWriter.write(objectWriter.writeValueAsString(props));
								fileWriter.flush();
								fileWriter.close();
							}
					}
				catch (FileNotFoundException e)
					{
						e.printStackTrace();
						System.exit(1);
					}
				catch (IOException e)
					{
						e.printStackTrace();
						System.exit(1)	;
					}
			}
			
		public static String getValue(String key)
			{
				return props.getProperty(key);
			}
			
		public static Map<CareerStream, Subject> getCareerStreamSubjectKeywords()
			{
				try
					{
						String path = "keywords" + File.separator + "CareerStream-Subject.txt";
						Resource resource = new ClassPathResource(path);
						List<String> lines = FileUtils.readLines(resource.getFile());
						Map<CareerStream, Subject> results = new HashMap<>();
						for (String line : lines)
							{
								StringTokenizer stk = new StringTokenizer(line, "-");
								if (stk.countTokens() != 2)
									{
										V2GenericException e = new V2GenericException("Wrong keyword in CareerStream-Subject.txt " + line);
										logger.error("Wrong keyword in CareerStream-Subject.txt " + line, e);
										e.printStackTrace();
									}
								else
									{
										String careerStream = stk.nextToken();
										String subject = stk.nextToken();
										CareerStream careerStream2 = new CareerStream(careerStream.trim());
										Subject subject2 = new Subject(subject.trim());
										results.put(careerStream2, subject2);
										
									}
									
							}
						return results;
					}
				catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
			}
			
		public static Map<String, Subject> getInstitutionTypeSubjectKeywords()
			{
				try
					{
						String path = "keywords" + File.separator + "InstitutionType-Subject.txt";
						Resource resource = new ClassPathResource(path);
						List<String> lines = FileUtils.readLines(resource.getFile());
						Map<String, Subject> results = new HashMap<>();
						for (String line : lines)
							{
								StringTokenizer stk = new StringTokenizer(line, "-");
								if (stk.countTokens() != 2)
									{
										V2GenericException e = new V2GenericException("Wrong keyword in CareerStream-Subject.txt " + line);
										logger.error("Wrong keyword in CareerStream-Subject.txt " + line, e);
										e.printStackTrace();
									}
								else
									{
										String institutionType = stk.nextToken();
										String subject = stk.nextToken();
										Subject subject2 = new Subject(subject.trim());
										results.put(institutionType.trim(), subject2);
										
									}
									
							}
						return results;
					}
				catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
			}
			
		public static Map<Subject, Exam> getSubjectExamKeywords()
			{
				try
					{
						String path = "keywords" + File.separator + "Subject-Exam.txt";
						Resource resource = new ClassPathResource(path);
						List<String> lines = FileUtils.readLines(resource.getFile());
						Map<Subject, Exam> results = new HashMap<>();
						for (String line : lines)
							{
								StringTokenizer stk = new StringTokenizer(line, "-");
								if (stk.countTokens() != 2)
									{
										V2GenericException e = new V2GenericException("Wrong keyword in CareerStream-Subject.txt " + line);
										logger.error("Wrong keyword in CareerStream-Subject.txt " + line, e);
										e.printStackTrace();
									}
								else
									{
										
										String subject = stk.nextToken();
										String exam = stk.nextToken();
										Subject subject2 = new Subject(subject.trim());
										Exam exam2 = new Exam(exam.trim());
										results.put(subject2, exam2);
										
									}
									
							}
						return results;
					}
				catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
			}
	}
