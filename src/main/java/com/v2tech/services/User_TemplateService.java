package com.v2tech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.domain.User_Template;
import com.v2tech.repository.User_TemplateRepository;

@Service
public class User_TemplateService {

	@Autowired
	User_TemplateRepository user_TemplateRepository;
	
	
	public User_Template findUser_Template(String user, String socialMedia, String templateType){
		List<User_Template> uts = user_TemplateRepository.getUserTemplate(user, socialMedia, templateType);
			if(uts.size() == 0){
				return null;
			}
			else{
				return uts.get(0);
			}
	}
	
	public User_Template saveOrUpdate(User_Template user_Template){
		User_Template user_Template2 = findUser_Template(user_Template.getUser(), user_Template.getSocialMediaType(), user_Template.getTemlplateType());
		if(user_Template2 == null){
			//create
			user_Template2 = user_TemplateRepository.save(user_Template);
		}
		else{
			user_Template2.setDateLastUpdated(user_Template.getDateLastUpdated());
			user_Template2 = user_TemplateRepository.save(user_Template2);
		}
		return user_Template2;
	}
}
