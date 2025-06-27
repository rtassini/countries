package com.itau.countries.services;

import com.itau.countries.client.restcountries.Countries;
import com.itau.countries.client.restcountries.Name;
import com.itau.countries.client.restcountries.RestCountriesClient;
import com.itau.countries.service.CountriesService;
import com.itau.countries.service.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CountriesServieGetCountriesByFilterTest {


    private FilterByNameStrategyImpl filterByNameStrategy;

private Countries Brazil;

    @BeforeEach
    void setUp() {
        filterByNameStrategy = mock(FilterByNameStrategyImpl.class);
        Brazil = new Countries(new Name("Brazil", "Federative Republic of Brazil"), List.of("Brasília"), "Americas", "South America");
    }

    @Test
    @DisplayName("Should return filtered countries when valid filter and value are provided")
    void returnsFilteredCountriesWhenValidFilterAndValueProvided() {
        FilterStrategy filterStrategy = mock(FilterStrategy.class);
        List<Countries> filteredCountries = List.of(Brazil);
        Map<String, FilterStrategy> strategies = Map.of("name", filterStrategy);

        when(filterStrategy.searchBy("Brazil")).thenReturn(filteredCountries);

        CountriesService service = new CountriesService(null, filterByNameStrategy, null, null, null);
        service.filterStrategies = strategies;

        List<Countries> result = service.getCountriesByFilter("name", "Brazil");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when invalid filter is provided")
    void throwsIllegalArgumentExceptionWhenInvalidFilterProvided() {
        CountriesService service = new CountriesService(null, null, null, null, null);
        service.filterStrategies = Map.of("name", mock(FilterStrategy.class));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getCountriesByFilter("invalid", "value"));
        assertTrue(exception.getMessage().contains("Invalid filter parameter"));
    }

    @Test
    @DisplayName("Should throw RuntimeException when filter strategy throws exception")
    void throwsRuntimeExceptionWhenFilterStrategyThrowsException() {
        FilterStrategy filterStrategy = mock(FilterStrategy.class);
        Map<String, FilterStrategy> strategies = Map.of("name", filterStrategy);

        when(filterStrategy.searchBy("Brazil")).thenThrow(new RuntimeException("DB error"));

        CountriesService service = new CountriesService(null, null, null, null, null);
        service.filterStrategies = strategies;

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.getCountriesByFilter("name", "Brazil"));
        assertTrue(exception.getMessage().contains("Failed to fetch countries data by filter"));
    }
}
