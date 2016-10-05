package com.v2tech.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.Source;
import com.v2tech.domain.Subject;
import com.v2tech.repository.SourceRepository;

@Service
public class SourceService {
	@Autowired
	SourceRepository sourceRepository;
	
	public Source saveOrUpdateSource(Source source){
		if(source.getSource() == null || source.getSource().trim().length() < 2){
			throw new V2GenericException("Invalid Source Name");
		}
		
		Source source2 = findSourceByName(source.getSource());
			if(source2 == null){
				return sourceRepository.save(source);
			}
		return source2;
	}
	
	public Source findSourceByName(String src){
		Set<Source> sources = sourceRepository.findSourceByName(src);
		
		if(sources.size() == 0){
			return null;
		}
		else if(sources.size() > 1){
			throw new V2GenericException("More than 1 source exist");
		}
		else{
			Source[] sources2 = new Source[sources.size()];
			sources2 = (Source[]) sources.toArray(sources2);
			return sources2[0];
		}
		
	}

}
