package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.domain.CountryStateCity;
import com.v2tech.services.CountryStateCityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:appContext.xml" })
@Transactional
public class TestCountryStateCity
	{
		
		@Autowired
		private CountryStateCityService countryStateCityService;

		@Test
		public void createCountryStateCity()
			{
				CountryStateCity countryStateCity = new CountryStateCity();
				countryStateCity.setRegion("Andheri");
				countryStateCity.setCountry("India");
				countryStateCity.setCountryCode("IN");
				countryStateCity.setCity("Mumbai");
				countryStateCity.setState("Maharashtra");
				countryStateCity.setZipcode(new Integer(RandomStringUtils.randomNumeric(6)));
				countryStateCityService.saveCountryStateCity(countryStateCity);
			}

		@Test
		public void createCountryStateCityList()
			{
				List<CountryStateCity> countryStateCities = new ArrayList<CountryStateCity>();
				int number = 50;
				int counter = 0;
				
				String[] states = new String[]{"Maharashtra" , "Punjab" , "Orissa" , "Gujarat" , "Rajasthan" , "Goa","West Bengal"};
				for(String state : states)
				{
					while (counter < number)
						{
							CountryStateCity countryStateCity = new CountryStateCity();
							countryStateCity.setRegion(state+"_Region" + new Integer(RandomStringUtils.randomNumeric(2)));
							countryStateCity.setCountry("India");
							countryStateCity.setCountryCode("IN");
							countryStateCity.setCity(state+"_City " + new Integer(RandomStringUtils.randomNumeric(2)));
							countryStateCity.setState(state);
							countryStateCity.setZipcode(new Integer(RandomStringUtils.randomNumeric(6)));
							countryStateCities.add(countryStateCity);
							counter++;
						}
				}
				countryStateCityService.saveCountryStateCity(countryStateCities);
			}

		@Test
		public void findDistinctCountry()
			{
				Set<String> countries = countryStateCityService.findDistinctCountry();
				for (String country : countries)
					{
						System.out.println(country);
					}
			}

		@Test
		public void findByDistinctState()
			{
				Set<String> states = countryStateCityService.findByDistinctState();
				for (String state : states)
					{
						System.out.println(state);
					}
			}

		@Test
		public void findByDistinctCity()
			{
				Set<String> cities = countryStateCityService.findByDistinctCity();
				for (String city : cities)
					{
						System.out.println(city);
					}
			}
			
		@Test
		public void findByDistinctCityForGivenCountryAndState()
			{
				String country = "India";
				String state = "Maharashtra";
				Set<String> cities = countryStateCityService.findByDistinctCityForGivenCountryAndState(country, state);
				for (String city : cities)
					{
						System.out.println(city);
					}
			}
	}
