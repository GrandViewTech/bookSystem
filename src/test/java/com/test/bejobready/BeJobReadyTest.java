package com.test.bejobready;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.SocialMediaType;
import com.v2tech.domain.User;
import com.v2tech.webservices.BeJobReadyWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class BeJobReadyTest {
	@Autowired
	BeJobReadyWebService beJobReadyWebService;
	
	@Test
	public void testBeJobReady(){
		User user = new User();
		user.setUser("jatin.sutaria@thev2technologies.com");
		user.setSocialMediaType(SocialMediaType.NONE);
		//String templateType = "CV";
		String templateType = "COVER";
		beJobReadyWebService.sendAcknlMailForJobReadyService(user, templateType, "test");
	}
}
