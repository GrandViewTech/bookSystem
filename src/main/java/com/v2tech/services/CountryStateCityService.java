package com.v2tech.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CountryStateCity;
import com.v2tech.domain.util.CountryStateResult;
import com.v2tech.repository.CountryStateCityRepository;

@Service
@Transactional
public class CountryStateCityService
	{
		@Autowired
		private CountryStateCityRepository countryStateCityRepository;
		
		public void saveCountryStateCity(CountryStateCity countryStateCity)
			{
				countryStateCity = validateCountryStateCity(countryStateCity);
				CountryStateCity existing = countryStateCityRepository.findByCountryNameStateNameAndCityNameAndRegionNameAndZipCode(countryStateCity.getCountry(), countryStateCity.getState(), countryStateCity.getCity(), countryStateCity.getRegion(), countryStateCity.getZipcode());
				if (existing == null)
					{
						countryStateCity.setReferenceId(UUID.randomUUID().toString());
						countryStateCityRepository.save(countryStateCity);
					}
				else
					{
						existing.setCountryCode(countryStateCity.getCountryCode());
						countryStateCityRepository.save(existing);
					}
			}
			
		public void saveCountryStateCity(List<CountryStateCity> countryStateCities)
			{
				List<CountryStateCity> udpatedCountryStateCities = new ArrayList<CountryStateCity>();
				for (CountryStateCity countryStateCity : countryStateCities)
					{
						try
							{
								countryStateCity = validateCountryStateCity(countryStateCity);
								CountryStateCity existing = countryStateCityRepository.findByCountryNameStateNameAndCityNameAndRegionNameAndZipCode(countryStateCity.getCountry(), countryStateCity.getState(), countryStateCity.getCity(), countryStateCity.getRegion(), countryStateCity.getZipcode());
								if (existing == null)
									{
										countryStateCity.setReferenceId(UUID.randomUUID().toString());
										udpatedCountryStateCities.add(countryStateCity);
									}
								else
									{
										existing.setCountryCode(countryStateCity.getCountryCode());
										udpatedCountryStateCities.add(existing);
									}
							}
						catch (Exception exception)
							{
								exception.printStackTrace();
							}
					}
				if (udpatedCountryStateCities.size() > 0)
					{
						countryStateCityRepository.save(udpatedCountryStateCities);
					}
			}
			
		public Set<String> findDistinctCountry()
			{
				return countryStateCityRepository.findByDistinctCountry();
			}
			
		public Set<String> findByDistinctState()
			{
				return countryStateCityRepository.findByDistinctState();
			}
			
		public Set<String> findByDistinctCity()
			{
				return countryStateCityRepository.findByDistinctCity();
			}
			
		public Map<String, List<String>> findByDistinctStateAndCityForGivenCountry(String country)
			{
				List<CountryStateResult> countryStateResults = countryStateCityRepository.findByDistinctStateForGivenCountry(country);
				Map<String, List<String>> result = new LinkedHashMap<>();
				for (CountryStateResult countryStateResult : countryStateResults)
					{
						String key = countryStateResult.getState();
						String value = countryStateResult.getCity();
						if (result.containsKey(key))
							{
								List<String> values = result.get(key);
								if (values.contains(value) == false)
									{
										values.add(value);
									}
								result.put(key, values);
							}
						else
							{
								List<String> values = new ArrayList<String>();
								values.add(value);
								result.put(key, values);
							}
					}
				return result;
			}
			
		public Set<String> findByDistinctCityForGivenCountryAndState(String country, String state)
			{
				return countryStateCityRepository.findByDistinctCityForGivenCountryAndState(country, state);
			}
			
		private CountryStateCity validateCountryStateCity(CountryStateCity countryStateCity)
			{
				String cityName = countryStateCity.getCity();
				String stateName = countryStateCity.getState();
				String countryName = countryStateCity.getCountry();
				String regionName = countryStateCity.getRegion();
				String zipcode = countryStateCity.getZipcode();
				if ((cityName == null) || (stateName == null) || (countryName == null) || (regionName == null) || (zipcode == null) || (cityName.trim().length() == 0) || (cityName.trim().length() == 0) || (stateName.trim().length() == 0) || (zipcode.trim().length() == 0) || (regionName.trim().length() == 0))
					{
						throw new V2GenericException("cityName : " + cityName + " | stateName : " + stateName + " | countryName : " + countryName + " | regionName : " + regionName + " | zipcode : " + zipcode + " cannot be null or empty or zero");
					}
				countryStateCity.setCity(cityName.trim());
				return countryStateCity;
			}
	}
