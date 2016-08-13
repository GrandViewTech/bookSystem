package com.v2tech.template.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.v2tech.template.domain.Template1;
import com.v2tech.template.domain.Template1Company;
import com.v2tech.template.domain.Template1Misc;
import com.v2tech.template.domain.Template1School;
@Service
public class Template1Service {
	
	private static final String companyHeaderComment = "<span style=\"font-family: Garamond;font-size:${font_size}; padding:0; margin-top: none; margin-bottom: none;margin-left: 18px\"> <i>${header_comment}</i></span>";
	
	
	public String generateHtml(Template1 template1) throws Exception{
		String template = FileUtils.readFileToString(new File("template1.html"));
		char c = '\u2022';
		//char c = '\u25aa';
		
		String bullet = String.valueOf(c);
		template = template.replace("${bullet}", bullet);
		String school = FileUtils.readFileToString(new File("school.txt"));
		String schoolComments = FileUtils.readFileToString(new File("schoolComment.txt"));
		String schoolRepeatTableRow = FileUtils.readFileToString(new File("schoolRepeatTableRow.txt"));
		
		
		String company = FileUtils.readFileToString(new File("company.txt"));
		String companyComments = FileUtils.readFileToString(new File("companyRoleComment.txt"));
		String companyRepeatTableRow = FileUtils.readFileToString(new File("companyRepeatTableRow.txt"));
		
		String leadership = FileUtils.readFileToString(new File("leadershipSource.txt"));
		String leadershipComment = FileUtils.readFileToString(new File("leadershipComment.txt"));
		String leadershipRepeatTableRow = FileUtils.readFileToString(new File("leadershipRepeatTableRow.txt"));
		
		
		template = template.replace("${Name}", template1.getCandidateName());
		template = template.replace("${Address}", template1.getAddress());
		template = template.replace("${Mobile}", template1.getMobile());
		template = template.replace("${Email}", template1.getEmail());
		template = template.replace("${font_size}", template1.getFontSizeContents()+"px");
		
		String schoolHtml = "";
		
		
		List<Template1School> schools = template1.getSchools();
			//for(Template1School template1School  : schools){
			for(int i=0;i<schools.size();i++){
				Template1School template1School = schools.get(i);
				String repeatSchComments = "";
				String schoolName = template1School.getSchoolName();
				String degree = template1School.getDegree();
				//schoolName += " - "+degree;
				String schoolLocal = school.replace("${School}", schoolName);
				
				schoolLocal = schoolLocal.replace("${School_Location}", template1School.getSchoolLocation());
				schoolLocal = schoolLocal.replace("${font_size}", template1.getFontSizeContents()+"px");
				
					
				
				schoolLocal = schoolLocal.replace("${School_Degree}", degree);
				schoolLocal = schoolLocal.replace("${School_MonthAndYear}", template1School.getMonthAndYear());
				//schoolHtml += schoolLocal+"\n";
				
				
				
				List<String> comments = template1School.getSchoolComments();
				//comments.add(0, degree);
					if(comments.size() >0){
						//repeatSchComments += "<ul  style=\"margin-top:0px; padding:0; margin-bottom:0px;margin-left:2em\">\n";
						repeatSchComments += FileUtils.readFileToString(new File("ul.txt"));
					}
				
					for(String comment: comments){
						String schoolCommentsLocal = schoolComments.replace("{Comment}", comment);
						schoolCommentsLocal = schoolCommentsLocal.replace("${font_size}", template1.getFontSizeContents()+"px");
						//repeatSchComments += schoolCommentsLocal+"\n";
						repeatSchComments += schoolCommentsLocal;
					}
					
					if(comments.size() > 0){
						repeatSchComments += "</ul>\n";
					}
					
					String tempSchoolRepeatTableRow =  schoolRepeatTableRow.replace("${Repeat_School_Comment}", repeatSchComments)	;
					tempSchoolRepeatTableRow = tempSchoolRepeatTableRow.replace("${font_size}", template1.getFontSizeContents()+"px");
					if(!(i == schools.size() -1)){
						tempSchoolRepeatTableRow = tempSchoolRepeatTableRow.replace("${padding-bottom}", "padding-bottom:4px");
					}
					else{
						/**
						 * To increase the gap between last bullet point of first school and school header next. However after last bullet point of last school 
						 * we don't need extra padding bottomn.
						 */
						tempSchoolRepeatTableRow = tempSchoolRepeatTableRow.replace("${padding-bottom}", "");
					}
					
					schoolHtml += schoolLocal.replace("${Repeat_School_Comments}", tempSchoolRepeatTableRow);
					
			}
		
		String companyHtml = "";
		
		List<Template1Company> companies = template1.getCompanies();
			for(Template1Company template1Company  : companies){
				String repeatCpnComments = "";
				String companyName = template1Company.getCompanyName();
				String companyLocation = template1Company.getCompanyLocation();
				String companyRole = template1Company.getRole();
				String companyRoleDuration = template1Company.getDuration();
				
				
				String companyLocal = company.replace("${Company}", companyName);
				companyLocal = companyLocal.replace("${Company_Location}", companyLocation);
				companyLocal = companyLocal.replace("${Role}", companyRole);
				companyLocal = companyLocal.replace("${Duration}", companyRoleDuration);
				companyLocal = companyLocal.replace("${font_size}", template1.getFontSizeContents()+"px");
				
				String headerComm = "";
				//companyHtml += companyLocal+"\n";
				
					if(template1Company.getHeaderComment() != null || template1Company.getHeaderComment().trim().length() > 0){
							if(template1Company.getHeaderComment().trim().length() != 0){
								headerComm =  companyHeaderComment.replace("${header_comment}", template1Company.getHeaderComment().trim());
								headerComm = headerComm.replace("${font_size}", template1.getFontSizeContents()+"px");
								repeatCpnComments = headerComm+"\n";
							}
						
					}
					List<String> comments = template1Company.getRoleComments();
					if(comments.size() > 0){
						//companyHtml += "<ul>\n";
						//repeatCpnComments  += "<ul  style=\"margin-top:0px; padding:0; margin-bottom:0px;margin-left:2em\">\n";
						repeatCpnComments  += FileUtils.readFileToString(new File("ul.txt"));
					}
				
					for(String comment: comments){
						String companyRoleCommentLocal = companyComments.replace("${Role_Comment}", comment);
						companyRoleCommentLocal = companyRoleCommentLocal.replace("${font_size}", template1.getFontSizeContents()+"px");
						//companyHtml += companyRoleCommentLocal+"\n";
						//repeatCpnComments  += companyRoleCommentLocal+"\n";
						repeatCpnComments  += companyRoleCommentLocal;
					}
					
					if(comments.size() > 0){
						//companyHtml += "</ul>\n";
						repeatCpnComments  += "</ul>\n";
					}
					
					//companyRepeatTableRow
					String tmpCompanyRepeatTableRow =  companyRepeatTableRow.replace("${Repeat_Company_Comment}", repeatCpnComments)	;
					tmpCompanyRepeatTableRow = tmpCompanyRepeatTableRow.replace("${font_size}", template1.getFontSizeContents()+"px");
					companyHtml += companyLocal.replace("${Repeat_Company_Comments}", tmpCompanyRepeatTableRow);
				
			}
			
			
		String miscHtml = "";
		List<Template1Misc> leaderships = template1.getLeaderships();
		for(Template1Misc misc  : leaderships){
			String repeatLdrComments = "";
			
			String sourceLeadership = misc.getSource();
			
			
			String leadershipLocal = leadership.replace("${Source}", misc.getSource());
			leadershipLocal = leadershipLocal.replace("${font_size}", template1.getFontSizeContents()+"px");
			//miscHtml += leadershipLocal+"\n";
			List<String> comments = misc.getComments();
				if(comments.size() > 0){
					//miscHtml += "<ul>\n";
					//repeatLdrComments += "<ul  style=\"margin-top:0px; padding:0; margin-bottom:0px;margin-left:2em\">\n";
					repeatLdrComments += FileUtils.readFileToString(new File("ul.txt"));
				}
			
				for(String comment: comments){
					String leadershipCommentLocal = leadershipComment.replace("${Source_Activity_Comment}", comment);
					leadershipCommentLocal = leadershipCommentLocal.replace("${font_size}", template1.getFontSizeContents()+"px");
					//miscHtml += leadershipCommentLocal+"\n";
					//repeatLdrComments += leadershipCommentLocal+"\n";
					repeatLdrComments += leadershipCommentLocal	;
				}
				
				if(comments.size() > 0){
					//miscHtml += "</ul>\n";
					repeatLdrComments += "</ul>\n";
				}
				
				//companyRepeatTableRow
				String tmpleadershipRepeatTableRow =  leadershipRepeatTableRow.replace("${Repeat_Leadership_Comment}", repeatLdrComments)	;
				tmpleadershipRepeatTableRow = tmpleadershipRepeatTableRow.replace("${font_size}", template1.getFontSizeContents()+"px");
				miscHtml += leadershipLocal.replace("${Repeat_Leadership_Comments}", tmpleadershipRepeatTableRow);
			
		}
		/**
		 * Also add code for making the header dynamic
		 */
		template = template.replace("{{1_HEADER}}", template1.getHeader1());
		template = template.replace("{{2_HEADER}}", template1.getHeader2());
		template = template.replace("{{3_HEADER}}", template1.getHeader3());
		
		String sequencing = template1.getSequencingOfSections();
			if(sequencing.length() == 3 && StringUtils.countOccurrencesOf(sequencing, "1") == 1 && StringUtils.countOccurrencesOf(sequencing, "2") == 1 && StringUtils.countOccurrencesOf(sequencing, "3") == 1){
				//we are good with sequencing data
					if(sequencing.startsWith("1")){
						template = template.replace("${Repeat_1_Comment}", schoolHtml);
					}
					else if(sequencing.startsWith("2")){
						template = template.replace("${Repeat_1_Comment}", companyHtml);
					}
					else if(sequencing.startsWith("3")){
						template = template.replace("${Repeat_1_Comment}", miscHtml);
					}
					
					String next = ""+sequencing.charAt(1);
					if(next.startsWith("1")){
						template = template.replace("${Repeat_2_Comment}", schoolHtml);
					}
					else if(next.startsWith("2")){
						template = template.replace("${Repeat_2_Comment}", companyHtml);
					}
					else if(next.startsWith("3")){
						template = template.replace("${Repeat_2_Comment}", miscHtml);
					}
					
					String last = ""+sequencing.charAt(2);
					if(last.startsWith("1")){
						template = template.replace("${Repeat_3_Comment}", schoolHtml);
					}
					else if(last.startsWith("2")){
						template = template.replace("${Repeat_3_Comment}", companyHtml);
					}
					else if(last.startsWith("3")){
						template = template.replace("${Repeat_3_Comment}", miscHtml);
					}
			}
			else{
				template = template.replace("${Repeat_1_Comment}", schoolHtml);
				template = template.replace("${Repeat_2_Comment}", companyHtml);
				template = template.replace("${Repeat_3_Comment}", miscHtml);
				template = template.replace("${font_size}", template1.getFontSizeContents()+"px");
				template = template.replace("&", "and");
			}
		
		
		return template;
	}
	
	public void convertHtmlToPdf(String templateHtml){
		
		
	}

}
