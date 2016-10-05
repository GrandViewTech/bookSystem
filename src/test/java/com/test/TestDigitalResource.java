package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.CoachingClass;
import com.v2tech.domain.DigitalTool;
import com.v2tech.repository.DigitalToolRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestDigitalResource {
	@Autowired
	DigitalToolRepository digitalToolRepository;
	
	
	@Test
	@Rollback(value=false)
	public void testDigitalToolDuplicates(){
		Result<DigitalTool> classes = digitalToolRepository.findAll();
		Map<String,DigitalTool> map = new HashMap<>();
		Iterator<DigitalTool> itr = classes.iterator();
		List<DigitalTool> list = new ArrayList<>();
			while(itr.hasNext()){
				DigitalTool bk = itr.next();
				if(map.get(bk.getName()) == null){
					map.put(bk.getName(), bk);
				}
				else{
					list.add(bk);
				}
			}
		for(DigitalTool bk : list){
			System.out.println(bk.getName());
			digitalToolRepository.delete(bk);
		}
	}
	

}
