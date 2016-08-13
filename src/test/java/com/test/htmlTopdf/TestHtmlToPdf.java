package com.test.htmlTopdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.v2.booksys.common.util.HtmlToPdf_IText;
import com.v2tech.template.domain.Template1;
import com.v2tech.template.domain.Template1Company;
import com.v2tech.template.domain.Template1Misc;
import com.v2tech.template.domain.Template1School;
import com.v2tech.template.service.Template1Service;
public class TestHtmlToPdf {
	@Test
	public void test1(){
		// step 1
        try {
			Document document = new Document(com.itextpdf.text.PageSize.A4);
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf.pdf"));
			// step 3
			document.open();
			// step 4
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,
			        new FileInputStream("Bunty Shah.html")); 
			//step 5
			 document.close();
 
			System.out.println( "PDF Created!" );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFlyingSaucer() throws IOException{
		String html  = FileUtils.readFileToString(new File("Bunty Shah.html"));
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString( html );
		renderer.layout();
		//String fileNameWithPath = outputFileFolder + "PDF-FromHtmlString.pdf";
		FileOutputStream fos = new FileOutputStream( "flyingS.pdf" );
		try {
			renderer.createPDF( fos );
		} catch (com.lowagie.text.DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fos.close();

		System.out.println( "File 2: flyingS.pdf created." );
	}
	
	
	public Template1 getExampleTemplate(){
		Template1Service service = new Template1Service();
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
	
	@Test
	public void testgenerateTestHtml() throws Exception{
		Template1Service service = new Template1Service();
		Template1 template1 = getExampleTemplate();
		String html = service.generateHtml(template1);
		HtmlToPdf_IText htmlToPdf_IText = new HtmlToPdf_IText();
		/**
		 * Use below to generate to pdf
		 */
		//htmlToPdf_IText.convert(htmlContents, pdfOutput, header);
	}
}
