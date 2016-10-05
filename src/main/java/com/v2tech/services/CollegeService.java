package com.v2tech.services;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.College;
import com.v2tech.repository.CollegeRepository;
@Service
public class CollegeService {
	
	@Autowired
	CollegeRepository collegeRepository;
	
	
	public College saveOrUpdate(College college){
		if(college.getName() == null){
			throw new V2GenericException("College name can not be null");
		}
		
		College college2 = collegeRepository.getCollegeByName(college.getName());
		
		if(college2 == null){
			//create
			college.setKeyword(college.getCourse() +" "+college.getStream());
			return collegeRepository.save(college);
			
		}
		else{
			//updATE
			Mapper mapper = new DozerBeanMapper();
			college.setId(college2.getId());
			mapper.map(college, college2);
			return collegeRepository.save(college2);
		}
	} 

}
