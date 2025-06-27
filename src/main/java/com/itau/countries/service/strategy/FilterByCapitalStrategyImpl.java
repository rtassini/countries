package com.itau.countries.service.strategy;

import com.itau.countries.client.restcountries.Countries;
import com.itau.countries.client.restcountries.RestCountriesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FilterByCapitalStrategyImpl implements FilterStrategy{

    private final RestCountriesClient restCountriesClient;

    public FilterByCapitalStrategyImpl(RestCountriesClient restCountriesClient) {
        this.restCountriesClient = restCountriesClient;
    }

    @Override
    public List<Countries> searchBy(String value) {
    log.info("Searching countries by capital: {}", value);
        return restCountriesClient.getCountriesByCapital(queryParameters, value);
    }
}
