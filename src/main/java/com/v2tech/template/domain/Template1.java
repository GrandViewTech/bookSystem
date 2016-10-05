package com.v2tech.template.domain;

import java.util.ArrayList;
import java.util.List;

import com.v2tech.template.domain.Template1School;

public class Template1
	{
		
		String					id;
		
		String					candidateName;
		
		String					templateType			= "";									//CV or COVER
		
		String					address;
		
		String					mobile;
		
		String					email;
		
		String					fileNameWithoutExt		= "";
		
		String					fontSizeContents		= "12";
		
		String					sequencingOfSections	= "123";
		
		String					header1					= "EDUCATION";	
		String					header2					= "WORK EXPERIENCE";
		String					header3					= "LEADERSHIP, ACTIVITIES AND AWARDS";
		
		List<Template1School>	schools					= new ArrayList<Template1School>();
		
		List<Template1Company>	companies				= new ArrayList<Template1Company>();
		
		List<Template1Misc>		leaderships				= new ArrayList<Template1Misc>();
		
		public String getCandidateName()
			{
				return candidateName;
			}
			
		public void setCandidateName(String candidateName)
			{
				this.candidateName = candidateName;
			}
			
		public String getAddress()
			{
				return address;
			}
			
		public void setAddress(String address)
			{
				this.address = address;
			}
			
		public String getMobile()
			{
				return mobile;
			}
			
		public void setMobile(String mobile)
			{
				this.mobile = mobile;
			}
			
		public String getEmail()
			{
				return email;
			}
			
		public void setEmail(String email)
			{
				this.email = email;
			}
			
		public List<Template1School> getSchools()
			{
				return schools;
			}
			
		public void setSchools(List<Template1School> schools)
			{
				this.schools = schools;
			}
			
		public List<Template1Company> getCompanies()
			{
				return companies;
			}
			
		public void setCompanies(List<Template1Company> companies)
			{
				this.companies = companies;
			}
			
		public List<Template1Misc> getLeaderships()
			{
				return leaderships;
			}
			
		public void setLeaderships(List<Template1Misc> leaderships)
			{
				this.leaderships = leaderships;
			}
			
		public String getId()
			{
				return id;
			}
			
		public void setId(String id)
			{
				this.id = id;
			}
			
		public String getFontSizeContents()
			{
				return fontSizeContents;
			}
			
		public void setFontSizeContents(String fontSizeContents)
			{
				this.fontSizeContents = fontSizeContents;
			}
			
		public String getTemplateType()
			{
				return templateType;
			}
			
		public void setTemplateType(String templateType)
			{
				this.templateType = templateType;
			}
			
		public String getFileNameWithoutExt()
			{
				return fileNameWithoutExt;
			}
			
		public void setFileNameWithoutExt(String fileNameWithoutExt)
			{
				this.fileNameWithoutExt = fileNameWithoutExt;
			}
			
		public String getSequencingOfSections()
			{
				return sequencingOfSections;
			}
			
		public void setSequencingOfSections(String sequencingOfSections)
			{
				this.sequencingOfSections = sequencingOfSections;
			}
			
		public String getHeader1()
			{
				return header1;
			}
			
		public void setHeader1(String header1)
			{
				this.header1 = header1;
			}
			
		public String getHeader2()
			{
				return header2;
			}
			
		public void setHeader2(String header2)
			{
				this.header2 = header2;
			}
			
		public String getHeader3()
			{
				return header3;
			}
			
		public void setHeader3(String header3)
			{
				this.header3 = header3;
			}
			
	}
