package com.itau.countries.service.strategy;

import com.itau.countries.client.restcountries.Countries;
import com.itau.countries.client.restcountries.RestCountriesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FilterBySubRegionStrategyImpl implements FilterStrategy{

    private final RestCountriesClient restCountriesClient;

    public FilterBySubRegionStrategyImpl(RestCountriesClient restCountriesClient) {
        this.restCountriesClient = restCountriesClient;
    }

    @Override
    public List<Countries> searchBy(String value) {
    log.info("Searching countries by subregion: {}", value);
        return restCountriesClient.getCountriesByName(queryParameters, value);
    }
}
