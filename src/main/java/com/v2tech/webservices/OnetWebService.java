package com.v2tech.webservices;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLDecoder;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.v2.booksys.onet.careers.Careers;
import com.v2.booksys.onet.data.Questions;
import com.v2.booksys.onet.data.answers.Results;
import com.v2.booksys.onet.data.occupation.fullDetails.DetailsReport;
import com.v2.booksys.onet.data.occupation.fullSummary.SummaryReport;
import com.v2.booksys.onet.data.occupations.Occupations;
import com.v2.booksys.onet.data.occupations.overview.Occupation;
import com.v2tech.domain.OnetReportData;
import com.v2tech.services.OnetReportDataService;

@Path("/onetService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class OnetWebService
	{
		@Autowired
		OnetReportDataService	onetReportDataService;
		
		static JAXBContext		jaxbContextQuestions			= null;
		static Unmarshaller		unmarshallerQuestions			= null;
		
		static JAXBContext		jaxbContextAnswers				= null;
		static Unmarshaller		unmarshallerAnswers				= null;
		
		static JAXBContext		jaxbContextOccupations			= null;
		static Unmarshaller		unmarshallerOccupations			= null;
		
		static JAXBContext		jaxbContextOccupationOverview	= null;
		static Unmarshaller		unmarshallerOccupationOverview	= null;
		
		static String			urlOnet							= "https://services.onetcenter.org/v1.6/ws/mnm/interestprofiler/questions?start={start}&amp;end={end}";
		
		static String			urlOnetAnswers					= "https://services.onetcenter.org/v1.6/ws/mnm/interestprofiler/results?answers={answers}";
		
		static String			urlOnetOccupations				= "https://services.onetcenter.org/ws/online/occupations/?sort=name&amp;start={start}&amp;end={end}";
		static String			urlOnetOccupationOverview		= "https://services.onetcenter.org/ws/online/occupations/{occupationCode}/";
		
		static JAXBContext		jaxbContextFullSummary			= null;
		static Unmarshaller		unmarshallerFullSummary			= null;
		static String			urlOnetOccupationFullSummary	= "https://services.onetcenter.org/v1.6/ws/online/occupations/{occupationCode}/summary/";
		
		static JAXBContext		jaxbContextFullDetails			= null;
		static Unmarshaller		unmarshallerFullDetails			= null;
		static String			urlOnetOccupationFullDetails	= "https://services.onetcenter.org/v1.6/ws/online/occupations/{occupationCode}/details/";
		
		static JAXBContext		jaxbContextCareers				= null;
		static Unmarshaller		unmarshallerCareers				= null;
		static String			urlOnetCareers					= "https://services.onetcenter.org/ws/mnm/interestprofiler/careers?answers={answers}&job_zone={job_zone}&start={start}&end={end}&sort=bright_outlook";
		
		static org.slf4j.Logger	logger							= LoggerFactory.getLogger(OnetWebService.class);
		private String			user							= "v2_technologies";
		private String			pwd								= "5946kfe";
		
		static Questions		questions1						= new Questions();
		static Questions		questions2						= new Questions();
		static Questions		questions3						= new Questions();
		static Questions		questions4						= new Questions();
		static Questions		questions5						= new Questions();
		
		static
			{
				try
					{
						jaxbContextQuestions = JAXBContext.newInstance(Questions.class);
						unmarshallerQuestions = jaxbContextQuestions.createUnmarshaller();
						
						jaxbContextAnswers = JAXBContext.newInstance(Results.class);
						unmarshallerAnswers = jaxbContextAnswers.createUnmarshaller();
						
						jaxbContextOccupations = JAXBContext.newInstance(Occupations.class);
						unmarshallerOccupations = jaxbContextOccupations.createUnmarshaller();
						
						jaxbContextOccupationOverview = JAXBContext.newInstance(Occupation.class);
						unmarshallerOccupationOverview = jaxbContextOccupationOverview.createUnmarshaller();
						
						jaxbContextFullSummary = JAXBContext.newInstance(SummaryReport.class);
						unmarshallerFullSummary = jaxbContextFullSummary.createUnmarshaller();
						
						jaxbContextFullDetails = JAXBContext.newInstance(DetailsReport.class);
						unmarshallerFullDetails = jaxbContextFullDetails.createUnmarshaller();
						
						jaxbContextCareers = JAXBContext.newInstance(Careers.class);
						unmarshallerCareers = jaxbContextCareers.createUnmarshaller();
						Resource resource = new ClassPathResource("onet" + File.separator + "questions.xml");
						String str = FileUtils.readFileToString(resource.getFile());
						StringReader reader = new StringReader(str);
						Questions questions = (Questions) unmarshallerQuestions.unmarshal(reader);
						questions1.getQuestion().addAll(questions.getQuestion().subList(0, 12));
						questions2.getQuestion().addAll(questions.getQuestion().subList(12, 24));
						questions3.getQuestion().addAll(questions.getQuestion().subList(24, 36));
						questions4.getQuestion().addAll(questions.getQuestion().subList(36, 48));
						questions5.getQuestion().addAll(questions.getQuestion().subList(48, 60));
						
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
			}
			
		//	@GET
		//	@Path("/onet/questions/start/{start}/end/{end}/token/{token}")
		//	@Produces(MediaType.APPLICATION_JSON)
		//	public Response getQuestions(@PathParam("start") Integer start, @PathParam("end") Integer end, @PathParam("token") String token ) throws JsonParseException, JsonMappingException, IOException{
		//		try {
		//			String urlOnet1 = urlOnet.replace("{start}", start+"");
		//			urlOnet1 = urlOnet1.replace("{end}", end+"");
		//			URL url = new URL(urlOnet1);
		//			//url.
		//			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		//			String authString = user + ":" + pwd;
		//			String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
		//			System.out.println(authStringEnc);
		//			connection.setRequestProperty("Authorization", "Basic "+ authStringEnc);
		//			//connection.setRequestProperty("password", "5946kfe");
		//			connection.setDoOutput(true);
		//			connection.setDoInput(true);
		//			connection.setUseCaches(false);
		//			int responseCode = connection.getResponseCode();
		//			//InputStream is = connection.getInputStream();
		//			String str = "";
		//			
		//			 BufferedReader in = new BufferedReader(new InputStreamReader(
		//					 connection.getInputStream()));
		//			String inputLine;
		//				while ((inputLine = in.readLine()) != null) {
		//					str += inputLine+"\n";
		//				}
		//			in.close();
		//			
		////			int count = is.available();
		////			byte data[] = new byte[count];
		////			is.read(data);
		//			connection.disconnect();
		//			//String str = new String(data);
		//			//System.out.println(str);
		//			FileUtils.write(new File("onet"+File.separator+"questions.xml"), str);
		//			StringReader reader = new StringReader(str);
		//			Questions questions =  (Questions) unmarshallerQuestions.unmarshal(reader);
		//			reader.close();
		//			return Response.ok().entity(questions).build();
		//		} catch (JAXBException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//			logger.error("Can not unmarshal back the xml rsults from onet to java", e);
		//			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		//		}
		//	}
		@GET
		@Path("/onet/careers/answers/{answers}/jobZone/{jobZone}/start/{start}/end/{end}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getCareersByResultsAndJobZone(@PathParam("answers") String answers, @PathParam("jobZone") String jobZone, @PathParam("start") Integer start, @PathParam("end") Integer end, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						String urlCareers = urlOnetCareers.replace("{answers}", answers);
						urlCareers = urlCareers.replace("{job_zone}", jobZone);
						urlCareers = urlCareers.replace("{start}", "" + start);
						urlCareers = urlCareers.replace("{end}", "" + end);
						URL url = new URL(urlCareers);
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						String authString = user + ":" + pwd;
						String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
						System.out.println(authStringEnc);
						connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						//connection.setRequestProperty("password", "5946kfe");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						int responseCode = connection.getResponseCode();
						//InputStream is = connection.getInputStream();
						String str = "";
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						while ((inputLine = in.readLine()) != null)
							{
								str += inputLine + "\n";
							}
						in.close();
						StringReader reader = new StringReader(str);
						System.out.println(str);
						Careers careers = (Careers) unmarshallerCareers.unmarshal(reader);
						reader.close();
						return Response.ok().entity(careers).build();
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
		@GET
		@Path("/onet/questions/start/{start}/end/{end}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getQuestions(@PathParam("start") Integer start, @PathParam("end") Integer end, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						
						if (start == 1 && end == 12)
							{
								return Response.ok().entity(questions1).build();
							}
						else if (start == 13 && end == 24)
							{
								return Response.ok().entity(questions2).build();
							}
						else if (start == 25 && end == 36)
							{
								return Response.ok().entity(questions3).build();
							}
						else if (start == 37 && end == 48)
							{
								return Response.ok().entity(questions4).build();
							}
						else if (start == 49 && end == 60)
							{
								return Response.ok().entity(questions5).build();
							}
						else
							{
								return Response.status(Status.SERVICE_UNAVAILABLE).build();
							}
							
					}
				catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
		@POST
		@Path("/onet/answers/{answers}/user/{user}/socialMedia/{socialMedia}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response saveAnswers(@PathParam("answers") String answers, @PathParam("user") String user, @PathParam("socialMedia") String socialMedia, @PathParam("token") String token)
			{
				
				OnetReportData onetReportData = new OnetReportData();
				onetReportData.setAnswers(answers);
				onetReportData.setUser(user);
				onetReportData.setSocialMediaType(socialMedia);
				if (!(user == null || user.length() == 0))
					{
						onetReportDataService.saveOrUpdate(onetReportData);
					}
					
				return Response.ok().build();
			}
			
		@GET
		@Path("/onet/questions/answers/{answers}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getAnswers(@PathParam("answers") String answers, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						String urlOnetAnswers1 = urlOnetAnswers.replace("{answers}", answers);
						URL url = new URL(urlOnetAnswers1);
						//url.
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						String authString = user + ":" + pwd;
						String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
						System.out.println(authStringEnc);
						connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						//connection.setRequestProperty("password", "5946kfe");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						int responseCode = connection.getResponseCode();
						//InputStream is = connection.getInputStream();
						String str = "";
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						while ((inputLine = in.readLine()) != null)
							{
								str += inputLine + "\n";
							}
						in.close();
						StringReader reader = new StringReader(str);
						System.out.println(str);
						Results answers2 = (Results) unmarshallerAnswers.unmarshal(reader);
						reader.close();
						return Response.ok().entity(answers2).build();
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
		@GET
		@Path("/onet/occupations/start/{start}/end/{end}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getOccupations(@PathParam("start") Integer start, @PathParam("end") Integer end, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						String urlOnetOccupations1 = urlOnetOccupations.replace("{start}", start + "");
						urlOnetOccupations1 = urlOnetOccupations1.replace("{end}", end + "");
						URL url = new URL(urlOnetOccupations1);
						//url.
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						String authString = user + ":" + pwd;
						String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
						System.out.println(authStringEnc);
						connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						//connection.setRequestProperty("password", "5946kfe");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						int responseCode = connection.getResponseCode();
						//InputStream is = connection.getInputStream();
						String str = "";
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						while ((inputLine = in.readLine()) != null)
							{
								str += inputLine + "\n";
							}
						in.close();
						
						connection.disconnect();
						
						System.out.println(str);
						StringReader reader = new StringReader(str);
						Occupations occupations = (Occupations) unmarshallerOccupations.unmarshal(reader);
						reader.close();
						return Response.ok().entity(occupations).build();
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
		@GET
		@Path("/onet/occupationOverview/occupationCode/{occupationCode}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getOccupationOverview(@PathParam("occupationCode") String occupationCode, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						String urlOnetOccupationOverview1 = urlOnetOccupationOverview.replace("{occupationCode}", occupationCode);
						URL url = new URL(urlOnetOccupationOverview1);
						//url.
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						String authString = user + ":" + pwd;
						String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
						System.out.println(authStringEnc);
						connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						//connection.setRequestProperty("password", "5946kfe");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						int responseCode = connection.getResponseCode();
						//InputStream is = connection.getInputStream();
						String str = "";
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						while ((inputLine = in.readLine()) != null)
							{
								str += inputLine + "\n";
							}
						in.close();
						StringReader reader = new StringReader(str);
						System.out.println(str);
						Occupation occupationOverview = (Occupation) unmarshallerOccupationOverview.unmarshal(reader);
						reader.close();
						return Response.ok().entity(occupationOverview).build();
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
		@GET
		@Path("/onet/occupationFullSummary/occupationCode/{occupationCode}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getOccupationFullSummaryReport(@PathParam("occupationCode") String occupationCode, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						String urlOnetOccupationFullSummary1 = urlOnetOccupationFullSummary.replace("{occupationCode}", occupationCode);
						URL url = new URL(urlOnetOccupationFullSummary1);
						//url.
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						String authString = user + ":" + pwd;
						String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
						System.out.println(authStringEnc);
						connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						//connection.setRequestProperty("password", "5946kfe");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						int responseCode = connection.getResponseCode();
						//InputStream is = connection.getInputStream();
						String str = "";
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						while ((inputLine = in.readLine()) != null)
							{
								str += inputLine + "\n";
							}
						in.close();
						StringReader reader = new StringReader(str);
						System.out.println(str);
						SummaryReport summaryReport = (SummaryReport) unmarshallerFullSummary.unmarshal(reader);
						reader.close();
						return Response.ok().entity(summaryReport).build();
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
		@GET
		@Path("/onet/occupationFullDetails/occupationCode/{occupationCode}/token/{token}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getOccupationFullDetailsReport(@PathParam("occupationCode") String occupationCode, @PathParam("token") String token) throws JsonParseException, JsonMappingException, IOException
			{
				try
					{
						String urlOnetOccupationFullDetails1 = urlOnetOccupationFullDetails.replace("{occupationCode}", URLDecoder.decode(occupationCode));
						URL url = new URL(urlOnetOccupationFullDetails1);
						//url.
						HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
						String authString = user + ":" + pwd;
						String authStringEnc = Base64Utils.encodeToString(authString.getBytes());
						System.out.println(authStringEnc);
						connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						//connection.setRequestProperty("password", "5946kfe");
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						int responseCode = connection.getResponseCode();
						//InputStream is = connection.getInputStream();
						String str = "";
						
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;	
						while ((inputLine = in.readLine()) != null)
							{
								str += inputLine + "\n";
							}
						in.close();
						StringReader reader = new StringReader(str);
						System.out.println(str);
						DetailsReport detailsReport = (DetailsReport) unmarshallerFullDetails.unmarshal(reader);
						reader.close();
						return Response.ok().entity(detailsReport).build();
					}
				catch (JAXBException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("Can not unmarshal back the xml rsults from onet to java", e);
						return Response.status(Status.SERVICE_UNAVAILABLE).build();
					}
			}
			
	}
