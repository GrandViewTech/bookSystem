package com.v2tech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.domain.OnetReportData;
import com.v2tech.repository.OnetReportDataRepository;

@Service
public class OnetReportDataService {
	
	@Autowired
	OnetReportDataRepository onetReportDataRepository;
	
	public OnetReportData findByUserDetails(String user, String socialMedia){
		List<OnetReportData> reps = onetReportDataRepository.findOnetReportForUser(user, socialMedia);
		if(reps.size() == 0){
			return null;
		}
		else{
			return reps.get(0);
		}
	}
	
	
	public OnetReportData saveOrUpdate(OnetReportData onetReportData){
		OnetReportData onetReportData2 = findByUserDetails(onetReportData.getUser(), onetReportData.getSocialMediaType());
		
		if(onetReportData2 == null){
			onetReportDataRepository.save(onetReportData);
		}
		else{
			//update
			onetReportData2.setAnswers(onetReportData.getAnswers());
			onetReportDataRepository.save(onetReportData2);
		}
		return onetReportData2;
	}
	
	

}
