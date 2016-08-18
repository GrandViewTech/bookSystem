package com.v2tech.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.CountryStateCity;
import com.v2tech.domain.util.CountryStateResult;

@Repository
public interface CountryStateCityRepository extends CrudRepository<CountryStateCity, Long>
	{
		@Query("MATCH (countryStateCity:CountryStateCity) WHERE countryStateCity.country =~ {0} AND countryStateCity.state =~ {1} AND countryStateCity.city =~ {2} AND countryStateCity.region =~ {3} AND countryStateCity.zipcode =~ {4} return countryStateCity;")
		CountryStateCity findByCountryNameStateNameAndCityNameAndRegionNameAndZipCode(String countryName, String stateName, String cityName, String regionName, String zipcode);

		@Query("MATCH (countryStateCity:CountryStateCity)  return DISTINCT countryStateCity.country ORDER BY countryStateCity.country;")
		Set<String> findByDistinctCountry();

		@Query("MATCH (countryStateCity:CountryStateCity)  return DISTINCT countryStateCity.state ORDER BY countryStateCity.state;")
		Set<String> findByDistinctState();

		@Query("MATCH (countryStateCity:CountryStateCity)  return DISTINCT countryStateCity.city ORDER BY countryStateCity.city;")
		Set<String> findByDistinctCity();

		@Query("MATCH (countryStateCity:CountryStateCity)  WHERE countryStateCity.country =~ {0} AND countryStateCity.state =~ {1} return DISTINCT countryStateCity.city ORDER BY countryStateCity.city;")
		Set<String> findByDistinctCityForGivenCountryAndState(String country, String state);

		@Query("MATCH (countryStateCity:CountryStateCity)  WHERE countryStateCity.country =~ {0}  return DISTINCT countryStateCity.state as state ,countryStateCity.city as city ;")
		List<CountryStateResult> findByDistinctStateForGivenCountry(String country);
		
	}
