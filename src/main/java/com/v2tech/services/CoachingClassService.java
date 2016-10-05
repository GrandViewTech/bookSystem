package com.v2tech.services;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CoachingClass;
import com.v2tech.repository.CoachingClassRepository;

@Service
@Transactional
public class CoachingClassService
	{
		@Autowired
		CoachingClassRepository coachingClassRepository;
		
		
			
		public CoachingClass findCoachingClassByNameAndLocation(String name, String location)
			{
				if (name == null || name.trim().length() == 0 || location == null || location.trim().length() == 0)
					{
						throw new V2GenericException("Empty String Coaching Class name or Location");
					}
				Set<CoachingClass> classes = coachingClassRepository.findCoachingClassByNameAndLocation(name, location);
				if (classes.size() == 0)
					{
						return null;
					}
				else if (classes.size() > 1)
					{
						throw new V2GenericException("Serious problem - More than 1 Coaching class exist by same name");
					}
				else
					{
						CoachingClass[] classes2 = new CoachingClass[classes.size()];
						classes2 = classes.toArray(classes2);
						return classes2[0];
					}
			}
			
		@Transactional
		public CoachingClass addCoachingClass(CoachingClass coachingClass)
			{
				if (coachingClass == null)
					{
						throw new V2GenericException("CoachingClass argument can not be null");
					}
					
				CoachingClass coachingClass2 = findCoachingClassByNameAndLocation(coachingClass.getName(), coachingClass.getLocation());
				if (coachingClass2 != null)
					{
						throw new V2GenericException("CoachingClass already exists!");
					}
					
				coachingClass2 = coachingClassRepository.save(coachingClass);
				return coachingClass2;
			}
			
		@Transactional
		public CoachingClass updateCoachingClass(CoachingClass coachingClass)
			{
				if (coachingClass == null)
					{
						throw new V2GenericException("CoachingClass argument can not be null");
					}
					
				CoachingClass coachingClass2 = findCoachingClassByNameAndLocation(coachingClass.getName(), coachingClass.getLocation());
				if (coachingClass2 == null)
					{
						throw new V2GenericException("CoachingClass does not exists!");
					}
					
				coachingClass2 = map(coachingClass, coachingClass2);
				coachingClass2 = coachingClassRepository.save(coachingClass);
				return coachingClass2;
			}
			
		//	@Transactional
		//	public CoachingClass saveOrUpdate(CoachingClass coachingClass) {
		//		if(coachingClass == null){
		//			throw new V2GenericException("CoachingClass argument can not be null");
		//		}
		//		
		//		CoachingClass coachingClass2 = findCoachingClassByNameAndLocation(coachingClass.getName(), coachingClass.getLocation());
		//		if(coachingClass2 == null){
		//			//create mode
		//			coachingClass2 = coachingClass;
		//		}
		//		else{
		//			//update mode
		//			coachingClass2 = map(coachingClass, coachingClass2)	;
		//			
		//			
		//		}
		//		coachingClass2 = coachingClassRepository.save(coachingClass2);
		//		return coachingClass2;
		//	}
		
		private CoachingClass map(CoachingClass coachingClass, CoachingClass coachingClass2)
			{
				if (coachingClass.getcStreams().trim().length() > 0 && !coachingClass2.getcStreams().contains(coachingClass.getcStreams()))
					{
						if (coachingClass2.getcStreams().trim().length() > 0)
							{
								coachingClass2.setcStreams(", " + coachingClass.getcStreams());
							}
						else
							{
								coachingClass2.setcStreams(coachingClass.getcStreams());
							}
					}
					
				if (coachingClass.getrExams().trim().length() > 0 && !coachingClass2.getrExams().contains(coachingClass.getrExams()))
					{
						if (coachingClass2.getrExams().trim().length() > 0)
							{
								coachingClass2.setrExams(", " + coachingClass.getrExams());
							}
						else
							{
								coachingClass2.setrExams(coachingClass.getrExams());
							}
					}
					
				if (coachingClass.getJuniorCollegesPartnerShip().trim().length() > 0 && !coachingClass2.getJuniorCollegesPartnerShip().contains(coachingClass.getJuniorCollegesPartnerShip()))
					{
						if (coachingClass2.getJuniorCollegesPartnerShip().trim().length() > 0)
							{
								coachingClass2.setJuniorCollegesPartnerShip(", " + coachingClass.getJuniorCollegesPartnerShip());
							}
						else
							{
								coachingClass2.setJuniorCollegesPartnerShip(coachingClass.getJuniorCollegesPartnerShip());
							}
					}
					
				if (coachingClass.getTypesOfCoursesOffered().trim().length() > 0 && !coachingClass2.getTypesOfCoursesOffered().contains(coachingClass.getTypesOfCoursesOffered()))
					{
						if (coachingClass2.getTypesOfCoursesOffered().trim().length() > 0)
							{
								coachingClass2.setTypesOfCoursesOffered(", " + coachingClass.getTypesOfCoursesOffered());
							}
						else
							{
								coachingClass2.setTypesOfCoursesOffered(coachingClass.getTypesOfCoursesOffered());
							}
					}
					
				coachingClass2.setAddress(coachingClass.getAddress());
				coachingClass2.setAverageBatchSize(coachingClass.getAverageBatchSize());
				coachingClass2.setCity(coachingClass.getCity());
				coachingClass2.setImage(coachingClass.getImage());
				coachingClass2.setLocation(coachingClass.getLocation());
				coachingClass2.setPhoneNumber(coachingClass.getPhoneNumber());
				coachingClass2.setSchedule(coachingClass.getSchedule());
				coachingClass2.setSummary(coachingClass.getSummary());
				coachingClass2.setTypeOfProgram(coachingClass2.getTypeOfProgram());
				coachingClass2.setWebsite(coachingClass.getWebsite());
				coachingClass2.setYearFounded(coachingClass.getYearFounded());
				return coachingClass2;
			}
			
	}
