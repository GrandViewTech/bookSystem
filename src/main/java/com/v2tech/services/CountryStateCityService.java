package com.v2tech.services;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.plutext.jaxb.svg11.G;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.v2tech.base.V2GenericException;
import com.v2tech.domain.CountryStateCity;
import com.v2tech.domain.util.CountryStateResult;
import com.v2tech.domain.util.GoogleApiResponse;
import com.v2tech.domain.util.PinCodeApiResponse;
import com.v2tech.repository.CountryStateCityRepository;

@Service
@Transactional
@PropertySource("classpath:bookSys.properties")
public class CountryStateCityService
	{
		
		@Value("${googleMapApiKey}")
		
		private String						googleMapApiKey;
		@Autowired
		private CountryStateCityRepository	countryStateCityRepository;
		
		private static ObjectMapper			objectMapper				= null;
		private static ObjectReader			googleApiResponseReader		= null;
		private static ObjectReader			pinCodeApiResponseReader	= null;
		
		static
			{
				try
					{
						objectMapper = new ObjectMapper();
						googleApiResponseReader = objectMapper.readerFor(GoogleApiResponse.class);
						pinCodeApiResponseReader = objectMapper.readerFor(PinCodeApiResponse.class);
					}
					
				catch (Exception exception)
					{
						exception.printStackTrace();
					}
			}
			
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
										String pincode = countryStateCity.getZipcode();
										GoogleApiResponse googleApiResponse = findGeoLocationByPinCode(pincode, 1);
										if (googleApiResponse != null)
											{
												countryStateCity.setLongitude(googleApiResponse.getLongitude());
												countryStateCity.setLatitude(googleApiResponse.getlatitude());
											}
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
						String key = countryStateResult.getCity();
						String value = countryStateResult.getRegion();
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
			
		/*
		 * type 1 for pincode
		 * type 2 for address ( Branch Name)
		 * 
		 */
		public GoogleApiResponse findGeoLocationByPinCode(String pincode, int type)
			{
				try
					{
						if (type == 3)
							{
								String url = "http://www.getpincode.info/api/pincode?q=" + URLEncoder.encode( pincode,StandardCharsets.UTF_8.name());
								URL obj = new URL(url);
								HttpURLConnection con = (HttpURLConnection) obj.openConnection();
								con.setRequestMethod("GET");
								con.setRequestProperty("Content-Type", "application/json");
								int responseCode = con.getResponseCode();
								PinCodeApiResponse response = null;
								if (responseCode == 200)
									{
										response = pinCodeApiResponseReader.readValue(con.getInputStream());
									}
								if (response != null)
									{
										pincode = response.getPincode();
									}
							}
						String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode( pincode,StandardCharsets.UTF_8.name()) + "&key=" + googleMapApiKey;
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						con.setRequestMethod("GET");
						con.setRequestProperty("Content-Type", "application/json");
						int responseCode = con.getResponseCode();
						GoogleApiResponse response = null;
						if (responseCode == 200)
							{
								response = googleApiResponseReader.readValue(con.getInputStream());
							}
						return response;
					}
				catch (Exception exception)
					{
						throw new RuntimeException(exception.getLocalizedMessage(), exception);
					}
			}
	}
