package com.itau.countries.service;

import com.itau.countries.client.restcountries.Countries;
import com.itau.countries.client.restcountries.RestCountriesClient;
import com.itau.countries.service.strategy.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CountriesService {

    private final RestCountriesClient restCountriesClient;
    private final FilterByNameStrategyImpl filterByNameStrategy;
    private final FilterByRegionStrategyImpl filterByRegionStrategy;
    private final FilterByCapitalStrategyImpl filterByCapitalStrategy;
    private final FilterBySubRegionStrategyImpl filterBySubRegionStrategy;
    Map<String, FilterStrategy> filterStrategies = null;

    public CountriesService(RestCountriesClient restCountriesClient, FilterByNameStrategyImpl filterByNameStrategy,
                            FilterByRegionStrategyImpl filterByRegionStrategy, FilterByCapitalStrategyImpl filterByCapitalStrategy,
                            FilterBySubRegionStrategyImpl filterBySubRegionStrategy) {
        this.restCountriesClient = restCountriesClient;
        this.filterByNameStrategy = filterByNameStrategy;
        this.filterByRegionStrategy = filterByRegionStrategy;
        this.filterByCapitalStrategy = filterByCapitalStrategy;
        this.filterBySubRegionStrategy = filterBySubRegionStrategy;
    }

    @PostConstruct
    public void inicializarMap(){
        log.info("Initializing filter strategies");
        filterStrategies = Map.of(
                "name", filterByNameStrategy,
                "region", filterByRegionStrategy,
                "capital", filterByCapitalStrategy,
                "subregion", filterBySubRegionStrategy
        );
    }

    @Cacheable(value = "countriesCache", key = "'allCountries'")
    public List<Countries> getCountries() {
        log.info("Fetching countries data");
        try {
            Map<String, String> queryParameters = Map.of(
                    "fields", "name,capital,region,subregion"
            );
            return restCountriesClient.getAllCountries(queryParameters);
        } catch (Exception e) {
            log.error("Error fetching countries data", e);
            throw new RuntimeException("Failed to fetch countries data", e);
        }

    }



    public List<Countries> getCountries(String filter, String value) {
        log.info("Fetching countries data by {}: {}", filter, value);
        if (!filterStrategies.containsKey(filter)) {
            log.error("Invalid filter parameter: {}", filter);
            throw new IllegalArgumentException("Invalid filter parameter. Allowed values are: name, capital, region, subregion.");
        }

        try {
            FilterStrategy strategy = filterStrategies.get(filter);
            return strategy.searchBy(value);
        } catch (Exception e) {
            log.error("Error fetching countries data", e);
            throw new RuntimeException("Failed to fetch countries data by filter", e);
        }
    }
}
