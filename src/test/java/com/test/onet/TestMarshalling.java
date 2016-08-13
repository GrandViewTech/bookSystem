package com.test.onet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2.booksys.onet.careers.Careers;
import com.v2.booksys.onet.data.Questions;
import com.v2.booksys.onet.data.answers.Results;
import com.v2.booksys.onet.data.occupation.fullDetails.DetailsReport;
import com.v2.booksys.onet.data.occupation.fullSummary.SummaryReport;
import com.v2.booksys.onet.data.occupations.Occupations;
import com.v2.booksys.onet.data.occupations.overview.Occupation;
import com.v2tech.webservices.OnetWebService;

public class TestMarshalling {
	private String user = "v2_technologies";
	private String pwd = "5946kfe";	
	
	@Test
	public void testGetCareersBasedOnAnswers() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getCareersByResultsAndJobZone("333333333333333333333333333333333333333333333333333333333333", "3", 1, 1000, "");
		Careers careers = (Careers) response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(careers);
		System.out.println(str);
	}
	
	@Test
	public void testQuestionsXmltoJava() throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance( Questions.class );
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Questions questions = (Questions) unmarshaller.unmarshal(new File("questions.xml"));
		System.out.println(questions);
	}
	
	@Test
	public void testQuestionsXmltoJavaFromOnet() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getQuestions(25, 36, "");
		Questions questions = (Questions) response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(questions);
		System.out.println(str);
		System.out.println(questions.getQuestion().size());
	}
	
	@Test
	public void testAnswersXmltoJavaFromOnet() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getAnswers("333333333335333333333333333333333333333333333333333333333333", "");
		Results answers = (Results) response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(answers);
		System.out.println(str);
	}
	
	@Test
	public void testOccupationsXmltoJavaFromOnet() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getOccupations(1, 12, "");
		Occupations occupations = (Occupations) response.getEntity();
		System.out.println(occupations);
	}
	
	@Test
	public void testOccupationOverviewXmltoJavaFromOnet() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getOccupationOverview("43-6012.00", "");
		Occupation occupationOverview = (Occupation) response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(occupationOverview);
		System.out.println(str);
	}

	@Test
	public void testOccupationFullSummaryXmltoJavaFromOnet() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getOccupationFullSummaryReport("43-6012.00", "");
		SummaryReport summaryReport = (SummaryReport) response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(summaryReport);
		System.out.println(str);
	}
	
	/**
	 * Use this for interest profiler reporting
	 * @throws Exception
	 */
	@Test
	public void testOccupationFullDetailsXmltoJavaFromOnet() throws Exception{
		OnetWebService onetWebService = new  OnetWebService();
		Response response = onetWebService.getOccupationFullDetailsReport("43-6012.00", "");
		DetailsReport summaryReport = (DetailsReport) response.getEntity();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(summaryReport);
		System.out.println(str);
	}
	
	@Test
	public void testGetCareerDetails() throws Exception{
		List<String> cs = FileUtils.readLines(new File("careersWithoutInterestArea.txt"));
			for(String career : cs){
				URL url = new URL("https://services.onetcenter.org/ws/mnm/careers/"+career+"/report");
				//url.
				HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
				String authString = user + ":" + pwd;
				String authStringEnc = java.util.Base64.getEncoder().encodeToString(authString.getBytes());
				//System.out.println(authStringEnc);
				connection.setRequestProperty("Authorization", "Basic "+ authStringEnc);
				//connection.setRequestProperty("password", "5946kfe");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setUseCaches(false);
				int responseCode = connection.getResponseCode();
				//InputStream is = connection.getInputStream();
				String str = "";
				
				 BufferedReader in = new BufferedReader(new InputStreamReader(
						 connection.getInputStream()));
				String inputLine;
					while ((inputLine = in.readLine()) != null) {
						str += inputLine+"\n";
					}
				in.close();
				
//				int count = is.available();
//				byte data[] = new byte[count];
//				is.read(data);
				connection.disconnect();
				//String str = new String(data);
			//	System.out.println(str);
				StringReader reader = new StringReader(str);
				Document document = new SAXReader().read(reader);
//				String value = document.valueOf("//report/job_outlook/outlook/description");
//				System.out.println(value);
				List<Node> nodes = document.selectNodes("//report/career/what_they_do");
				String str1 = "";
				int count = 0;
					for(Node node : nodes){
							if(nodes.size()-1 != count){
								str1+= node.getText()+" AND ";
							}
							else{
								str1+= node.getText();
							}
						
							count ++;
					}
				System.out.println(str1);
			}
		
		
	}
}
