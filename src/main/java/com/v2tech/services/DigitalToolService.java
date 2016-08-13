package com.v2tech.services;

import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.DigitalTool;
import com.v2tech.repository.DigitalToolRepository;

@Service
public class DigitalToolService
	{
		
		@Autowired
		DigitalToolRepository digitalToolRepository;
		
		public DigitalTool getDigitalToolByName(String name)
			{
				Set<DigitalTool> tools = digitalToolRepository.findDigitalToolByName(name);
				if (tools.size() == 0)
					{
						return null;
					}
				else if (tools.size() > 1)
					{
						throw new V2GenericException("2 tools with same name found");
					}
				else
					{
						DigitalTool[] tools2 = new DigitalTool[tools.size()];
						tools2 = (DigitalTool[]) tools.toArray(tools2);
						return tools2[0];
					}
					
			}
			
		public void deleteTool(String name)
			{
				DigitalTool digitalTool = getDigitalToolByName(name);
				if (digitalTool != null)
					{
						digitalToolRepository.delete(digitalTool.getId());
					}
					
			}
			
		public void markKeywordAndSearchParams(DigitalTool digitalTool)
			{
				DigitalTool digitalTool2 = getDigitalToolByName(digitalTool.getName());
				if (digitalTool2.getName() == null)
					{
						//do nothing
						return;
					}
					
				digitalTool2.setKeyword(digitalTool.getKeyword());
				digitalTool2.setSearchable(digitalTool.getSearchable());
				digitalToolRepository.save(digitalTool2);
			}
			
		public DigitalTool saveOrUpdate(DigitalTool digitalTool)
			{
				if (digitalTool.getName() == null || digitalTool.getName().trim().length() == 0)
					{
						throw new V2GenericException("Tool Name can not be null/empty");
					}
					
				DigitalTool digitalTool2 = getDigitalToolByName(digitalTool.getName());
				if (digitalTool2 == null)
					{
						//create
						digitalTool.setKeyword(digitalTool.getName() + "," + digitalTool.getCareerStreams() + "," + digitalTool.getExams() + "," + digitalTool.getYearFounded());
						digitalToolRepository.save(digitalTool);
					}
				else
					{
						//update
						digitalTool.setId(digitalTool2.getId());
						digitalTool.setKeyword(digitalTool.getName() + "," + digitalTool.getCareerStreams() + "," + digitalTool.getExams() + "," + digitalTool.getYearFounded());
						DozerBeanMapper beanMapper = new DozerBeanMapper();
						beanMapper.map(digitalTool, digitalTool2);
						digitalToolRepository.save(digitalTool2);
					}
				return digitalTool2;
			}
			
		public Set<DigitalTool> searchDigitalToolByGenericKeyword(String keyword, Integer limit)
			{
				keyword = "(?i).*" + keyword + ".*";
				return digitalToolRepository.searchDigitalToolByGenericKeyword(keyword, limit);
			}
	}
