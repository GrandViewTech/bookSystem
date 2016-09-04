package com.v2tech.webservices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.v2.booksys.common.util.ExcelReader;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.CountryStateCity;
import com.v2tech.domain.SearchResponse;
import com.v2tech.repository.CoachingClassRepository;
import com.v2tech.services.CoachingClassService1;
import com.v2tech.services.CountryStateCityService;

@Path("/coachingClassService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@PropertySource("classpath:bookSys.properties")
public class CoachingClassWebService
	{
		@Autowired
		CoachingClassService1			coachingClassService;
		
		@Autowired
		CoachingClassRepository			coachingClassRepository;
		
		@Autowired
		private CountryStateCityService	countryStateCityService;
		
		@Value("${resourceLocation}")
		private String					resourceLocation;
		
		private static ObjectMapper		objectMapper;
		private static ObjectWriter		objectWriter;
		static
			{
				try
					{
						objectMapper = new ObjectMapper();
						objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
					}
				catch (Exception exception)
					{
						objectMapper = null;
						objectWriter = null;
						exception.printStackTrace();
					}
			}
			
		@POST
		@Path("/uploadCoachingClassExcel/token/{token}")
		@Consumes(MediaType.MULTIPART_FORM_DATA)
		@Produces("application/json")
		public boolean uploadCoachingClassExcel(Attachment attachment, @PathParam("token") String token)
			{
				List<CountryStateCity> countryStateCities = new ArrayList<CountryStateCity>();
				try
					{
						DataHandler handler = attachment.getDataHandler();
						
						InputStream stream = handler.getInputStream();
						
						MultivaluedMap<String, String> map = attachment.getHeaders();
						System.out.println("fileName Here" + BookWebService.getFileName(map));
						Resource resource = new ClassPathResource("rules" + File.separator + "excelCoachingClassesRules.xml");
						List<CoachingClass> classes = ExcelReader.parseExcelFileToBeans(stream, resource.getFile());
						
						int count = 0;
						String resourceDir = null;
						if (resourceLocation != null && resourceLocation.trim().length() > 0)
							{
								try
									{
										resourceDir = resourceLocation + File.separator + "coachingclasses";
										File file = new File(resourceDir);
										if (file.isDirectory() == false)
											{
												FileUtils.forceMkdir(file);
											}
									}
								catch (Exception exception)
									{
										exception.printStackTrace();
									}
							}
						for (CoachingClass coachingClass : classes)
							{
								if (coachingClass.getName() != null)
									{
										boolean add = true;
										count++;
										String name = coachingClass.getName();
										System.out.println(count + " )  " + coachingClass.getName());
										coachingClass = coachingClassService.saveOrUpdate(coachingClass);
										if (objectWriter != null)
											{
												try
													{
														if (resourceDir != null && resourceDir.trim().length() > 0)
															{
																String state = coachingClass.getState();
																String city = coachingClass.getCity();
																if (state == null || state.trim().length() == 0)
																	{
																		state = "annonymous";
																		add = false;
																	}
																String dirPath = resourceDir + File.separator + state + File.separator + city;
																File dir = new File(dirPath);
																if (dir.isDirectory() == false)
																	{
																		FileUtils.forceMkdir(dir);
																	}
																File file = new File(dirPath + File.separator + name + ".json");
																if (file.exists())
																	{
																		file.delete();
																	}
																file.createNewFile();
																FileWriter fileWriter = new FileWriter(file);
																fileWriter.write(objectWriter.writeValueAsString(coachingClass));
																fileWriter.flush();
																fileWriter.close();
															}
													}
												catch (Exception exception)
													{
														exception.printStackTrace();
													}
											}
										if (add == true)
											{
												String state = coachingClass.getState();
												String city = coachingClass.getCity();
												CountryStateCity countryStateCity = new CountryStateCity();
												countryStateCity.setCity(city);
												countryStateCity.setCountry("India");
												countryStateCity.setCountryCode("IN");
												countryStateCity.setState(state);
												countryStateCity.setRegion(coachingClass.getBranch());
												countryStateCity.setZipcode(coachingClass.getZip());
												countryStateCities.add(countryStateCity);
											}
									}
									
							}
					}
				catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new V2GenericException("Code-FileUploadProblem,Msg-Problem with file upload");
					}
				catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new V2GenericException("Code-ExcelParseProblem,Msg-Can not convert excel into beans");
					}
				if (countryStateCities.size() > 0)
					{
						try
							{
								countryStateCityService.saveCountryStateCity(countryStateCities);
							}
						catch (Exception exception)
							{
								exception.printStackTrace();
							}
					}
				return true;
			}
			
		@POST
		@Path("/updateKeyword/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response updateKeyWord(CoachingClass coachingClass, @PathParam("token") String token)
			{
				try
					{
						coachingClassService.updateKeyword(coachingClass);
						return Response.ok().entity(true).build();
					}
				catch (V2GenericException e)
					{
						return Response.status(Status.BAD_REQUEST).build();
					}
			}
			
		@POST
		@Path("/saveOrUpdate/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response saveOrUpdate(CoachingClass coachingClass, @PathParam("token") String token)
			{
				try
					{
						coachingClassService.saveOrUpdate(coachingClass);
						return Response.ok().entity(true).build();
					}
				catch (V2GenericException e)
					{
						return Response.status(Status.BAD_REQUEST).build();
					}
			}
			
		@GET
		@Path("/searchCoachingClass/name/{name}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchCoachingClass(@PathParam("name") String name, @PathParam("token") String token)
			{
				//Set<Book> books = bookRepository.searchAllBooksByTitle(".*"+title+".*");
				Set<CoachingClass> classes = coachingClassRepository.searchAllCoachingClassesByName(name);
				List<CoachingClass> ret = new ArrayList<>(classes);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setClasses(ret);
				return searchResponse;
			}
			
		//	@GET
		//	@Path("/uniqueCoachingClassByName/token/{token}")
		//	@Produces(MediaType.APPLICATION_JSON)
		//	@Transactional
		//	public SearchResponse searchCoachingClass(@PathParam("token") String token) {
		//		//Set<Book> books = bookRepository.searchAllBooksByTitle(".*"+title+".*");
		//		Set<CoachingClass> classes = coachingClassRepository.searchAllCoachingClassesByName(name);
		//		List<CoachingClass> ret = new ArrayList<>(classes);
		//		SearchResponse searchResponse = new SearchResponse();
		//		searchResponse.setClasses(ret);
		//		return searchResponse;
		//	}
	}
