package com.v2tech.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Subject;
import com.v2tech.repository.SubjectRepository;

@Service
public class SubjectService {
	@Autowired
	SubjectRepository subjectRepository;
	
	public Subject saveOrUpdateSubject(Subject subject){
		if(subject.getName() == null || subject.getName().trim().length() < 3){
			throw new V2GenericException("Invalid Subject name");
		}
		Subject sub2 = findSubjectByName(subject.getName());
		if( sub2 == null){
			//create
			return subjectRepository.save(subject);
		}
		else{
			//update - do nbothing
			return sub2;
		}
		
	}
	
	public Subject findSubjectByName(String subject){
		Set<Subject> subjects = subjectRepository.findSubjectByName(subject);
		if(subjects.size() == 0){
			return null;
		}
		else if(subjects.size() > 1){
			throw new V2GenericException("More than 1 Subject with same name");
		}
		else{
			Subject[] sub = new Subject[subjects.size()];
			sub = (Subject[]) subjects.toArray(sub);
			return sub[0];
		}
	}

}
