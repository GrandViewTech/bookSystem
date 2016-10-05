package com.v2tech.webservices;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2.booksys.common.util.HtmlToPdf_IText;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.User;
import com.v2tech.services.UserService;
import com.v2tech.template.domain.Template1;
import com.v2tech.template.domain.Template1Company;
import com.v2tech.template.domain.Template1Misc;
import com.v2tech.template.domain.Template1School;
import com.v2tech.template.service.Template1Service;

@Path("/templateService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class TemplateWebService {
	ObjectMapper  mapper = new ObjectMapper();
	
	@Autowired
	Template1Service template1Service;
	
	@Autowired
	UserService userService;
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateWebService.class);
	
	@GET
	@Path("/template1/example/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Template1 getTemplate1Example(@PathParam("token") String token ){
		Template1 template1 = new Template1();
		template1.setCandidateName("Jatin Sutaria");
		template1.setAddress("502, Mount View, Yogi Hills, Mumbai");
		template1.setMobile("919930070660");
		template1.setEmail("jatin.sutaria@thev2technologies.com");
		
		Template1School template1School1 = new Template1School();
		template1School1.setSchoolName("ABC Law School");
		template1School1.setSchoolLocation("Cambridge, Massachusetts");
		String schoolComment1 = "Juris Doctor - LSAT Score 178 (of 180) (99th Percentile)";
		template1School1.getSchoolComments().add(schoolComment1);
		
		Template1School template1School2 = new Template1School();
		template1School2.setSchoolName("ABC University");
		template1School2.setSchoolLocation("Chicago, Illinois");
		String schoolComment21 = "Bachelor of Arts, with Honors ; Cumulative GPA 3.9 (of 4.0) - Dean’s List, All Grading Periods";
		String schoolComment22 = "Majors: Economics and Mathematical Methods in the Social Sciences (Honors Statistical EconomicsProgram)";
		String schoolComment23 = "National Merit Scholar; SAT Score 2310 (of 2400)(99th Percentile) ; ACT Score 34 (of 36) (99th Percentile)";
		template1School2.getSchoolComments().add(schoolComment21);
		template1School2.getSchoolComments().add(schoolComment22);
		template1School2.getSchoolComments().add(schoolComment23);
		template1.getSchools().add(template1School1);
		template1.getSchools().add(template1School2);
		
		Template1Company company1 = new Template1Company();
		company1.setCompanyName("ABC& Partners");
		company1.setCompanyLocation("Tokyo, Japan");
		company1.setRole("Summer Law Clerk");
		company1.setDuration("May 2015 - August 2015");
		String companyRoleComment1 = "Assisted with projects ranging from the review of a share purchase agreement for a client’s acquisition of a company in Brazil to participation in the representation of an automobile parts supplier against claims of negligent manufacturing and anticompetitive business practices";
		company1.getRoleComments().add(companyRoleComment1);
		
		
		Template1Company company2 = new Template1Company();
		company2.setCompanyName("ABC Partners, LLC");
		company2.setCompanyLocation("Boston, Massachusetts");
		company2.setRole("Private Equity Analyst");
		company2.setDuration("June 2012 – July 2014");
		String companyRoleComment21 = "Performed due diligence and provided facilitation from reception of evaluation materials to close of the transactions, including industry diligence, construction of financial/operating models, analysis of current operations, customer calls, funds flow, etc.";
		String companyRoleComment22 = "Served in project management role to help facilitate integration of the companies and performed post-acquisition work (operational and financial)";
		String companyRoleComment23 = "Analyzed profitability of products for a portfolio company that manufacturesvitamins, Pharmetics, in advance of customer negotiations";
		String companyRoleComment24 = "Conducted diligence for strategic initiative of CMC, a biosimilar contract manufacturer that specializes in cancer and anti-hemophilic drugs";
		String companyRoleComment25 = "Evaluated market size for, and feasibility of, a new service offering by Palladium Group, provider of Balanced Scorecard consulting";
		
		company2.getRoleComments().add(companyRoleComment21);
		company2.getRoleComments().add(companyRoleComment22);
		company2.getRoleComments().add(companyRoleComment23);
		company2.getRoleComments().add(companyRoleComment24);
		company2.getRoleComments().add(companyRoleComment25);
		
		//
		Template1Company company3 = new Template1Company();
		company3.setCompanyName("ABC& Co. – Financial Institutions Group");
		company3.setCompanyLocation("New York, New York");
		company3.setRole("Investment Banking Summer Analyst");
		company3.setDuration("June 2011 – August 2011");
		String companyRoleComment31 = "Prepared valuation materials including trading comparables, transaction comparables, and discounted cash flow analyses";
		String companyRoleComment32 = "Analyzed industry trends, M&A landscape, strategic rationale, and governance issues for potential acquisition opportunities";
		String companyRoleComment33 = "Evaluated impact of proposed transactions on EPS accretion/dilution and value creation for clients";
		String companyRoleComment34 = "Conducted pro forma ratings analysis for clients striving to maintain their investment-grade credit rating";
		
		company3.getRoleComments().add(companyRoleComment31);
		company3.getRoleComments().add(companyRoleComment32);
		company3.getRoleComments().add(companyRoleComment33);
		company3.getRoleComments().add(companyRoleComment34);
		
		//
		Template1Company company4 = new Template1Company();
		company4.setCompanyName("ABC Capital, LLC");
		company4.setCompanyLocation("Dallas, Texas");
		company4.setRole("Investment Banking Summer Analyst");
		company4.setDuration("June 2010 – September 2010");
		String companyRoleComment41 = "Created pitch books for transaction and investment proposals, including a proposed bank merger and a bank equity investment";
		String companyRoleComment42 = "Conducted pro forma analysis of capital raises, mergers, and acquisitions for companies in the financial services industry model for bank valuation across such variables as Tier 1 Ratio, Efficiency Ratio, Non-Performing Assets/Assets, etc.";
		
		company4.getRoleComments().add(companyRoleComment41);
		company4.getRoleComments().add(companyRoleComment42);
		
		//
		Template1Company company5 = new Template1Company();
		company5.setCompanyName("Bank of Dallas");
		company5.setCompanyLocation("Dallas, Texas");
		company5.setRole("Intern");
		company5.setDuration("June 2009 – September 2009");
		String companyRoleComment51 = "Prepared an in-depth report to analyze the impact and detail the current and historical significance of the energy sector in Texas";
		String companyRoleComment52 = "Frequently attended Fed economists’ presentations on current national economic topics";
		String companyRoleComment53 = "Gained an understanding of the basic economic research methodology used by Federal Reserve economists";
		
		company5.getRoleComments().add(companyRoleComment51);
		company5.getRoleComments().add(companyRoleComment52);
		company5.getRoleComments().add(companyRoleComment53);
		//
		Template1Company company6 = new Template1Company();
		company6.setCompanyName("ABC Partners, LP ");
		company6.setCompanyLocation("Dallas, Texas");
		company6.setRole("Intern");
		company6.setDuration("June 2008 – August 2008");
		String companyRoleComment61 = "Managed lease files for four commercial buildings and compiled annual property operating data";
		String companyRoleComment62 = "Compiled, analyzed, and prepared appraisal files for ad valorem tax protest cases";
		
		company6.getRoleComments().add(companyRoleComment61);
		company6.getRoleComments().add(companyRoleComment62);
		
		//
		Template1Company company7 = new Template1Company();
		company7.setCompanyName("The Dallas Journal");
		company7.setCompanyLocation("Dallas, Texas");
		company7.setRole("Editorial Intern");
		company7.setDuration("June 2007 – August 2007");
		String companyRoleComment71 = "Wrote and published articles on various business and economic issues in the Dallas Metroplex – copies available upon request";
		String companyRoleComment72 = "Compiled two weekly sections covering local business events and career movement of executive-level corporate personnel";
		
		company7.getRoleComments().add(companyRoleComment71);
		company7.getRoleComments().add(companyRoleComment72);
		
		template1.getCompanies().add(company1);
		template1.getCompanies().add(company2);
		template1.getCompanies().add(company3);
		template1.getCompanies().add(company4);
		template1.getCompanies().add(company5);
		template1.getCompanies().add(company6);
		template1.getCompanies().add(company7);
		//
		Template1Misc misc1 = new Template1Misc();
		misc1.setSource("ABC Law School");
		String miscComment11 = "Elected Treasurer of HLS Lambda for the 2015 – 2016 academic year";
		String miscComment12 = "Section Representative for the Federalist Society";
		String miscComment13 = "Subciter for Harvard Business Law Review";
		misc1.getComments().add(miscComment11);
		misc1.getComments().add(miscComment12);
		misc1.getComments().add(miscComment13);
		//
		Template1Misc misc2 = new Template1Misc();
		misc2.setSource("ABC University");
		String miscComment21 = "Studied abroad in Madrid, Spain to increase Spanish language fluency";
		String miscComment22 = "Campus Coordinator of campusCATALYST (engages students in for-credit consulting class with local Chicagoland non-profit organizations)";
		String miscComment23 = "Financial Institutions Sector Leader for Northwestern Capital Management (formerly The Nugget Fund)(manages real-money portfolio)";
		String miscComment24 = "First Place in Deloitte Consulting Case Competition (proposed strategy for $400MM private equity investment)";
		
		misc2.getComments().add(miscComment21);
		misc2.getComments().add(miscComment22);
		misc2.getComments().add(miscComment23);
		misc2.getComments().add(miscComment24);
		//
		Template1Misc misc3 = new Template1Misc();
		misc3.setSource("ABC School of Texas");
		String miscComment31 = "Presidential Community Service Award (over 100 hours of service annually); St. Mark’s School of Texas Community Service Board";
		String miscComment32 = "Award of Excellence (individual award) from the Interscholastic League Press Conference for Writing";
		String miscComment33 = "Eagle Scout (Bronze, Gold, and Silver Palms); Order of the Arrow (Scouting’s National Honor Society)";
		String miscComment34 = "Letters in Varsity Swimming (Captain) and Varsity Football";
		
		misc3.getComments().add(miscComment31);
		misc3.getComments().add(miscComment32);
		misc3.getComments().add(miscComment33);
		misc3.getComments().add(miscComment34);
		
		template1.getLeaderships().add(misc1);
		template1.getLeaderships().add(misc2);
		template1.getLeaderships().add(misc3);
		return template1;
	}
	
	@POST
	@Path("/assignIds/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public void tempAssignIds(@PathParam("token") String token) throws Exception{
		String fl = "resumes"+File.separator+"template1";
		String ext[] = {"json"};
		Collection<File> files = FileUtils.listFiles(new File(fl), ext , false);
			for(File file : files){
				Template1 temp = mapper.readValue(file, Template1.class);
				UUID uuid = UUID.randomUUID();
				temp.setId(uuid.toString());
					for(Template1School school : temp.getSchools()){
						uuid = UUID.randomUUID();
						school.setId(uuid.toString());
					}
					
					for(Template1Company company : temp.getCompanies()){
						uuid = UUID.randomUUID();
						company.setId(uuid.toString());
					}
					
					for(Template1Misc misc : temp.getLeaderships()){
						uuid = UUID.randomUUID();
						misc.setId(uuid.toString());
					}
					
					mapper.writeValue(file, temp);
			}
	}
	
	
	@GET
	@Path("/template1/candidateName/{candidateName}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Template1 getTemplate1(@PathParam("candidateName") String candidateName, @PathParam("token") String token ) throws JsonParseException, JsonMappingException, IOException{
		Template1 template1 = null;
		String fl = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(fl);
			if(file.isFile()){
				template1 = mapper.readValue(file, Template1.class);
			}
			else{
				template1 = new Template1();
				int index = candidateName.indexOf("[");
				int lastIndex = candidateName.indexOf("]");
				String email = candidateName.substring(0, index);
				String socialMedia = candidateName.substring(index+1, lastIndex);
				User user = userService.getUserByUserNameAndSocialMediaType(email, socialMedia);
				template1.setCandidateName(user.getFirstName()+" "+user.getLastName());
				template1.setEmail(user.getUser());
				template1.setMobile(user.getContact());
				//template1.setAddress(user.get);
				StringWriter stringWriter = new StringWriter();
				mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template1);
			}
		
		return template1;
	}
	
	
	@POST
	@Path("/template1/school/candidateName/{candidateName}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveTemplate1School(Template1School school, @PathParam("candidateName") String candidateName, @PathParam("token") String token) throws JsonGenerationException, JsonMappingException, IOException{
		
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		
		if(file.isFile()){
			
			boolean toAdd = true;
			for(Template1School school2 : template.getSchools()){
				if(school.getId() == null){
					school.setId("");
				}
				
				if(school.getId().equalsIgnoreCase(school2.getId())){
					int index = template.getSchools().indexOf(school2);
					template.getSchools().remove(school2);
					template.getSchools().add(index, school);
					toAdd = false;
					break;
				}
			}
			
			if(toAdd){
				UUID uuid = UUID.randomUUID();
		        String schoolId = uuid.toString();
		        school.setId(schoolId);
				template.getSchools().add(school);
			}
			//add/update school
		}
		else{
			throw new V2GenericException("Template json not exisitng on backend");
		}
		//StringWriter stringWriter = new StringWriter();
		mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template);
		//FileUtils.
		
	}
	
	@POST
	@Path("/template1/candidateName/{candidateName}/delete/schoolId/{schoolId}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public void deleteTemplate1School(@PathParam("schoolId") String schoolId, @PathParam("candidateName") String candidateName, @PathParam("token") String token) throws JsonGenerationException, JsonMappingException, IOException{
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		for(Template1School school2 : template.getSchools()){
			if(schoolId.equalsIgnoreCase(school2.getId())){
				template.getSchools().remove(school2);
				break;
			}
		}
		
		StringWriter stringWriter = new StringWriter();
		mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template);
	}
	
	
	
	///
	@POST
	@Path("/template1/company/candidateName/{candidateName}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveTemplate1Company(Template1Company company, @PathParam("candidateName") String candidateName, @PathParam("token") String token) throws JsonGenerationException, JsonMappingException, IOException{
		
		/**
		 * Step 1 - Check out 
		 */
		
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		
		if(file.isFile()){
			
			boolean toAdd = true;
			for(Template1Company company2 : template.getCompanies()){
				//if(company.getCompanyName().equals(company2.getCompanyName()) && company.getRole().equals(company2.getRole())){
				if(company.getId() == null){
					company.setId("");
				}
				if(company.getId().equals(company2.getId())){
					int index = template.getCompanies().indexOf(company2);
					template.getCompanies().remove(company2);
					template.getCompanies().add(index, company);
					toAdd = false;
					break;
				}
			}
			
			if(toAdd){
				UUID uuid = UUID.randomUUID();
		        String companyId = uuid.toString();
		        company.setId(companyId);
				template.getCompanies().add(company);
			}
			//add/update school
		}
		else{
			throw new V2GenericException("Template json not exisitng on backend");
		}
		mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template);
		//FileUtils.
		
	}
	
	@POST
	@Path("/template1/candidateName/{candidateName}/delete/companyId/{companyId}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public void deleteTemplate1Company( @PathParam("candidateName") String candidateName, @PathParam("companyId") String companyId,  @PathParam("token") String token) throws JsonGenerationException, JsonMappingException, IOException{
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		for(Template1Company company : template.getCompanies()){
			//if(company.getCompanyName().equals(companyName) && company.getRole().equals(role)){
			if(company.getId().equals(companyId)){
				template.getCompanies().remove(company);
				break;
			}
		}
		mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template);
	}
	
	///
	
	//last misc
	@POST
	@Path("/template1/leadershipActivity/candidateName/{candidateName}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveTemplate1LeadershipActivity(Template1Misc activity, @PathParam("candidateName") String candidateName, @PathParam("token") String token) throws JsonGenerationException, JsonMappingException, IOException{
		
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		
		if(file.isFile()){
			
			boolean toAdd = true;
			for(Template1Misc activity2 : template.getLeaderships()){
				if(activity.getId() == null){
					activity.setId("");
				}
				
				if(activity.getId().equals(activity2.getId()) ){
					int index = template.getLeaderships().indexOf(activity2);
					template.getLeaderships().remove(activity2);
					template.getLeaderships().add(index, activity);
					toAdd = false;
					break;
				}
			}
			
			if(toAdd){
				UUID uuid = UUID.randomUUID();
		        String miscId = uuid.toString();
		        activity.setId(miscId);
				template.getLeaderships().add(activity);
			}
			//add/update school
		}
		else{
			throw new V2GenericException("Template json not exisitng on backend");
		}
		mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template);
		//FileUtils.
		
	}
	
	@POST
	@Path("/template1/candidateName/{candidateName}/delete/leadershipId/{leadershipId}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public void deleteTemplate1LeadershipActivity( @PathParam("candidateName") String candidateName, @PathParam("leadershipId") String leadershipId,  @PathParam("token") String token) throws JsonGenerationException, JsonMappingException, IOException{
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		for(Template1Misc template1Misc : template.getLeaderships()){
			if(template1Misc.getId().equals(leadershipId) ){
				template.getLeaderships().remove(template1Misc);
				break;
			}
		}
		mapper.writeValue(new File("resumes"+File.separator+"template1"+File.separator+candidateName+".json"), template);
	}
	
	//last misc end
	
	
	@POST
	@Path("/template1/candidateName/{candidateName}/token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveTemplate1(Template1 template1, @PathParam("candidateName") String candidateName, @PathParam("token") String token) throws Exception{
		template1.setFileNameWithoutExt(candidateName);
		FileUtils.forceMkdir(new File("resumes"+File.separator+"template1"));
		String path = "resumes"+File.separator+"template1"+File.separator+candidateName+".json";
		File file = new File(path);
			if(!file.exists()){
				UUID uuid = UUID.randomUUID();
		        String templateId = uuid.toString();
		        template1.setId(templateId);
			}
		mapper.writeValue(file, template1);
	}
	
	
	@POST
	@Path("/checkIfCoverOrCVExistsForUser/user/{user}/socialMedia/{socialMedia}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response checkIfCoverOrCVExistsForUser(@PathParam("user") String user, @PathParam("socialMedia") String socialMedia, @PathParam("token") String token){
		String fName1= user+"["+socialMedia+"][COVER]";
		String fName2= user+"["+socialMedia+"][CV]";
		File file1 = new File("resumes"+File.separator+"template1"+File.separator+fName1+".pdf");
		File file2 = new File("resumes"+File.separator+"template1"+File.separator+fName2+".pdf");
		UserCV_Cover_Availability availability = new  UserCV_Cover_Availability();
			if(file1.isFile()){
				availability.setCoverAvailable(true);
			}
			
			if(file2.isFile()){
				availability.setCvAvailable(true);
			}
		Response response = Response.ok().entity(availability).build();
		return response;
	}
	
	@POST
	@Path("/generateCoverLetterPDFForUser/user/{user}/socialMedia/{socialMedia}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	public Response generateCoverLetterPDFForUser(@PathParam("user") String user, @PathParam("socialMedia") String socialMedia, @PathParam("token") String token) throws Exception{
		String fName2= user+"["+socialMedia+"][COVER]";
		String path = "resumes"+File.separator+"template1"+File.separator+fName2+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		return generatePDF(template, token);
	}
	
	
	@POST
	@Path("/generateCVPDFForUser/user/{user}/socialMedia/{socialMedia}/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	public Response generateCVPDFForUser(@PathParam("user") String user, @PathParam("socialMedia") String socialMedia, @PathParam("token") String token) throws Exception{
		String fName1= user+"["+socialMedia+"][CV]";
		String path = "resumes"+File.separator+"template1"+File.separator+fName1+".json";
		File file = new File(path);
		Template1 template = mapper.readValue(file, Template1.class);
		return generatePDF(template, token);
	}
	
	@POST
	@Path("/generatePdfForTemplate1/token/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	public Response generatePDF(Template1 template1, @PathParam("token") String token){
		try {
			
				//for(Template1School school : template1.getSchools()){
					//school.setSchoolLocation(school.getSchoolLocation()+" - "+school.getMonthAndYear());
				//}
			
			String templateHtml = template1Service.generateHtml(template1);
//			File file = new File("resumes"+File.separator+"template1"+File.separator+template1.getCandidateName()+".pdf");
//			File html = new File("resumes"+File.separator+"template1"+File.separator+template1.getCandidateName()+".html");
			File file = new File("resumes"+File.separator+"template1"+File.separator+template1.getFileNameWithoutExt()+".pdf");
			File html = new File("resumes"+File.separator+"template1"+File.separator+template1.getFileNameWithoutExt()+".html");
			FileUtils.writeStringToFile(html, templateHtml);
			String template = FileUtils.readFileToString(new File("template1_header.html"));
			template = template.replace("${Name}", template1.getCandidateName());
			template = template.replace("${Address}", template1.getAddress());
			template = template.replace("${Mobile}", template1.getMobile());
			template = template.replace("${Email}", template1.getEmail());
			FileUtils.write(new File("temp.html"), template);
			//HTMLToPDF htmlToPDF = new HTMLToPDF();
			//htmlToPDF.convert(html, file, template);
			HtmlToPdf_IText htmlToPdf_IText = new HtmlToPdf_IText();
			htmlToPdf_IText.convertFlyingSoccer(html, file, template);
			ResponseBuilder response = Response.ok((Object) file);
		    response.header("Content-Disposition", "attachment; filename="+template1.getCandidateName()+".pdf");
		    return response.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem in pdf generation", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
		
	}
	

}

class UserCV_Cover_Availability{
	boolean cvAvailable = false;
	
	boolean coverAvailable = false;

	public boolean isCvAvailable() {
		return cvAvailable;
	}

	public void setCvAvailable(boolean cvAvailable) {
		this.cvAvailable = cvAvailable;
	}

	public boolean isCoverAvailable() {
		return coverAvailable;
	}

	public void setCoverAvailable(boolean coverAvailable) {
		this.coverAvailable = coverAvailable;
	}
	
	
}
