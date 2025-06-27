package com.itau.countries.service.strategy;

import com.itau.countries.client.restcountries.Countries;

import java.util.List;
import java.util.Map;

public interface FilterStrategy {

    Map<String, String> queryParameters = Map.of(
            "fields", "name,capital,region,subregion"
    );

    List<Countries> searchBy(String filter);
}
