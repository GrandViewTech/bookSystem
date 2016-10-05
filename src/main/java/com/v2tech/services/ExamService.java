package com.v2tech.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Exam;
import com.v2tech.repository.ExamRepository;

@Service
public class ExamService {
	@Autowired
	ExamRepository examRepository;
	
	public Exam saveOrUpdateExam(Exam exam){
		if(exam.getExam() == null || exam.getExam().length() < 2){
			throw new V2GenericException("Invalid exam");
		}
		
		Exam exam2 = getSingleExamByName(exam.getExam());
		if(exam2 == null){
			//create
			return examRepository.save(exam);
			
		}
		else{
			return exam2;
			//update
			//don't do anything
		}
	}

	public Exam getSingleExamByName(String exam){
		Set<Exam> exams = examRepository.findExamByName(exam);
		if(exams.size() == 0){
			return null;
		}
		else if(exams.size() > 1){
			throw new V2GenericException("2 exams with same name found");
		}
		else{
			Exam[] exams2 = new Exam[exams.size()];
			exams2 = (Exam[]) exams.toArray(exams2);
			return exams2[0];
		}
	}
}
