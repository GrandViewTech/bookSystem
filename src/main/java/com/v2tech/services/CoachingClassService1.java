package com.v2tech.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CoachingClass;
import com.v2tech.repository.CoachingClassRepository;

@Service
public class CoachingClassService1
	{
		
		@Autowired
		CoachingClassRepository coachingClassRepository;
		
		public CoachingClass findByNameAndBranchAndZipCode(String name, String branch, String zipCode)
			{
				Set<CoachingClass> classes = coachingClassRepository.findByNameAndBranchAndZipCode(name, branch, zipCode);
				if (classes.size() == 0)
					{
						return null;
					}
				else
					{
						
						CoachingClass classArray[] = new CoachingClass[classes.size()];
						CoachingClass class1 = (classes.toArray(classArray))[0];
						return class1;
					}
			}
			
		public void updateKeyword(CoachingClass coachingClass)
			{
				CoachingClass coachingClass2 = findByNameAndBranchAndZipCode(coachingClass.getName(), coachingClass.getBranch(), coachingClass.getZip());
				if (coachingClass2 == null)
					{
						throw new V2GenericException("No CoachingClass available to update the keyword");
					}
				coachingClass2.setKeyword(coachingClass.getKeyword());
				coachingClass2.setSearchable(coachingClass.getSearchable());
				coachingClassRepository.save(coachingClass2);
			}
			
		public CoachingClass saveOrUpdate(CoachingClass coachngClass)
			{
				if (coachngClass == null)
					{
						throw new V2GenericException("No data");
					}
					
				if (coachngClass.getBranch() == null)
					{
						if (coachngClass.getCity() == null)
							{
								if (coachngClass.getZip() == null)
									{
										throw new V2GenericException("No location info present");
									}
								else
									{
										coachngClass.setCity("NA");
										coachngClass.setBranch("NA");
									}
									
							}
						else
							{
								coachngClass.setBranch(coachngClass.getCity());
							}
					}
					
				if (coachngClass.getZip() == null)
					{
						coachngClass.setZip("NA");
					}
					
				CoachingClass coachingClass = findByNameAndBranchAndZipCode(coachngClass.getName(), coachngClass.getBranch(), coachngClass.getZip());
				if (coachingClass == null)
					{
						//This is a new record
						coachngClass.setKeyword(coachngClass.getZip() + "," + coachngClass.getBranch() + "," + coachngClass.getCity() + "," + coachngClass.getName() + "," + coachngClass.getcStreams() + "," + coachngClass.getAverageBatchSize() + "," + coachngClass.getrExams() + "," + coachngClass.getCourses());
						coachingClass = coachingClassRepository.save(coachngClass);
						return coachingClass;
					}
				else
					{
						//This is a update operation
						
						if (!coachingClass.getcStreams().contains(coachngClass.getcStreams()))
							{
								coachingClass.setcStreams(coachingClass.getcStreams() + "," + coachngClass.getcStreams());
							}
							
						if (!coachingClass.getCourses().contains(coachngClass.getCourses()))
							{
								coachingClass.setCourses(coachingClass.getCourses() + "," + coachngClass.getCourses());
							}
							
						if (!coachingClass.getrExams().contains(coachngClass.getrExams()))
							{
								coachingClass.setrExams(coachingClass.getrExams() + "," + coachngClass.getrExams());
							}
							
						if (!coachingClass.getTargetStudents().contains(coachngClass.getTargetStudents()))
							{
								coachingClass.setTargetStudents(coachingClass.getTargetStudents() + "," + coachngClass.getTargetStudents());
							}
							
						if (!coachingClass.getTypesOfCoursesOffered().contains(coachngClass.getTypesOfCoursesOffered()))
							{
								coachingClass.setTypesOfCoursesOffered(coachingClass.getTypesOfCoursesOffered() + "," + coachngClass.getTypesOfCoursesOffered());
							}
						coachingClass.setAddedBy(coachngClass.getAddedBy());
						coachingClass.setAddress(coachngClass.getAddress());
						coachingClass.setAverageBatchSize(coachngClass.getAverageBatchSize());
						coachingClass.setCourseMaterial(coachngClass.getCourseMaterial());
						coachingClass.setDescription(coachngClass.getDescription());
						coachingClass.setDuration(coachngClass.getDuration());
						coachingClass.setJuniorCollegesPartnerShip(coachngClass.getJuniorCollegesPartnerShip());
						coachingClass.setMedium(coachngClass.getMedium());
						coachingClass.setSchedule(coachngClass.getSummary());
						coachingClass.setTypeOfProgram(coachngClass.getTypeOfProgram());
						coachingClass.setWebsite(coachngClass.getWebsite());
						coachingClass.setYearFounded(coachngClass.getYearFounded());
						
						//coachingClass.setKeyword(coachngClass.getZip()+","+coachngClass.getBranch()+","+coachngClass.getCity()+","+coachngClass.getName()+","+coachngClass.getcStreams()+","+coachngClass.getAverageBatchSize()+","+coachngClass.getrExams()+","+coachngClass.getCourses());
						
						coachingClass = coachingClassRepository.save(coachingClass);
						return coachingClass;
					}
			}
			
		public Set<CoachingClass> searchCoachingClassByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return coachingClassRepository.searchCoachingClassByGenericKeyword(keyword, limit);
			}
			
		public Set<CoachingClass> findCoachingClassByKeywordAndCity(String keyword, String city, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				city = "(?i)" + city.trim();
				return coachingClassRepository.findCoachingClassByKeywordAndCity(keyword, city, limit);
			}
			
		public Set<CoachingClass> findCoachingClassByCity(String city, Integer limit)
			{
				city = "(?i)" + city.trim();
				return coachingClassRepository.findCoachingClassByCity(city, limit);
			}
			
	}
