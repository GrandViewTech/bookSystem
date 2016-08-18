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
import com.v2tech.domain.DigitalTool;
import com.v2tech.domain.SearchResponse;
import com.v2tech.repository.DigitalToolRepository;
import com.v2tech.services.DigitalToolService;

@Path("/digitalToolService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@PropertySource("classpath:bookSys.properties")
public class DigitalToolWebService
	{
		
		@Autowired
		DigitalToolService			digitalToolService;
		
		@Autowired
		DigitalToolRepository		digitalToolRepository;
		
		@Value("${resourceLocation}")
		private String				resourceLocation;
		private static ObjectMapper	objectMapper;
		private static ObjectWriter	objectWriter;
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
		@Path("/uploadDigitalToolsExcel/token/{token}")
		@Consumes(MediaType.MULTIPART_FORM_DATA)
		@Produces("application/json")
		public boolean uploadDigitalToolsExcel(Attachment attachment, @PathParam("token") String token)
			{
				try
					{
						DataHandler handler = attachment.getDataHandler();
						
						InputStream stream = handler.getInputStream();
						
						MultivaluedMap<String, String> map = attachment.getHeaders();
						System.out.println("fileName Here" + BookWebService.getFileName(map));
						Resource resource = new ClassPathResource("rules" + File.separator + "excelDigitalToolsRules.xml");
						List<DigitalTool> tools = ExcelReader.parseExcelFileToBeans(stream, resource.getFile());
						int count = 0;
						String resourceDir = null;
						if (resourceLocation != null && resourceLocation.trim().length() > 0)
							{
								try
									{
										resourceDir = resourceLocation + File.separator + "digitalresources";
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
						for (DigitalTool digitalTool : tools)
							{
								
								if (digitalTool.getName() != null)
									{
										String name = digitalTool.getName();
										count++;
										System.out.println(count + " ) " + name);
										digitalTool = digitalToolService.saveOrUpdate(digitalTool);
										if (objectWriter != null)
											{
												if (resourceDir != null && resourceDir.trim().length() > 0)
													{
														try
															{
																File file = new File(resourceDir + File.separator + name + ".json");
																if (file.exists())
																	{
																		file.delete();
																	}
																	
																file.createNewFile();
																FileWriter fileWriter = new FileWriter(file);
																fileWriter.write(objectWriter.writeValueAsString(digitalTool));
																fileWriter.flush();
																fileWriter.close();
															}
														catch (Exception exception)
															{
																exception.printStackTrace();
															}
													}
											}
									}
									
							}
						return true;
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
			}
			
		@POST
		@Path("/updateKeyword/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response updateKeyWord(DigitalTool digitalTool, @PathParam("token") String token)
			{
				try
					{
						digitalToolService.markKeywordAndSearchParams(digitalTool);
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
		public Response saveOrUpdate(DigitalTool digitalTool, @PathParam("token") String token)
			{
				try
					{
						digitalToolService.saveOrUpdate(digitalTool);
						return Response.ok().entity(true).build();
					}
				catch (V2GenericException e)
					{
						return Response.status(Status.BAD_REQUEST).build();
					}
			}
			
		@GET
		@Path("/searchDigitalTool/name/{name}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		@Transactional
		public SearchResponse searchAllDigitalToolsWithName(@PathParam("name") String name, @PathParam("token") String token)
			{
				//Set<Book> books = bookRepository.searchAllBooksByTitle(".*"+title+".*");
				Set<DigitalTool> tools = digitalToolRepository.findDigitalToolByName(name);
				List<DigitalTool> ret = new ArrayList<>(tools);
				SearchResponse searchResponse = new SearchResponse();
				searchResponse.setTools(ret);
				return searchResponse;
			}
			
	}
