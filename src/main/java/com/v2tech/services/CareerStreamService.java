package com.v2tech.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CareerStream;
import com.v2tech.domain.Exam;
import com.v2tech.domain.Subject;
import com.v2tech.repository.CareerStreamRepository;

@Service
public class CareerStreamService {

	@Autowired
	CareerStreamRepository careerStreamRepository;
	
	@Autowired
	ExamService examService;
	
	@Autowired
	SubjectService subjectService;
	
	public CareerStream saveOrUpdateCareerStream(CareerStream careerStream){
		careerStream.setStream(careerStream.getStream().trim());
		if(careerStream.getStream() == null || careerStream.getStream().trim().length() < 2){
			throw new V2GenericException("Invalid CareerStream");
		}
		
		CareerStream cS = getSingleCareerStream(careerStream.getStream());
		if(cS == null){
			//create
			cS = careerStream;
		}else{
			return cS;
		}
		
		Set<Exam> exams = 	cS.getExams();
		//Set<Exam> exams2 = new HashSet();
		for(Exam exam : exams){
			examService.saveOrUpdateExam(exam);
		}
		
		Set<Subject> subjects = cS.getSubjects();
		for(Subject subject : subjects){
			subjectService.saveOrUpdateSubject(subject);
		}
		
		return careerStreamRepository.save(cS);
		
	}
	
	public CareerStream getSingleCareerStream(String stream){
		Set<CareerStream> streams =   careerStreamRepository.findCareerStreamByName(stream);
		if(streams.size() == 0){
			return null;
		}
		else if(streams.size() > 1){
			throw new V2GenericException("More than 1 CS exists with same name");
		}
		else{
			CareerStream cStreams[] = new CareerStream[streams.size()];
			CareerStream[]  careerStreams = (CareerStream[]) streams.toArray(cStreams);
			return careerStreams[0];
			//return stream
		}
	}
}
