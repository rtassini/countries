package com.itau.countries.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.itau.countries.client.restcountries.Countries;
import com.itau.countries.client.restcountries.Name;
import com.itau.countries.client.restcountries.RestCountriesClient;
import com.itau.countries.service.CountriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;


class CountriesServiceGetAllTest {


    private RestCountriesClient restCountriesClient;

    private CountriesService countriesService;

    private List<Countries> countriesList;

    @BeforeEach
    void setUp() {
        restCountriesClient = mock(RestCountriesClient.class);


        countriesList = new ArrayList<>();
        Countries Haiti = new Countries(new Name("Haiti", "Republic of Haiti"), List.of("Port-au-Prince"), "Americas", "Caribbean");
        countriesList.add(Haiti);
        Countries Brazil = new Countries(new Name("Brazil", "Federative Republic of Brazil"), List.of("Brasília"), "Americas", "South America");
        countriesList.add(Brazil);

    }

    @Test
    @DisplayName("Should return list of countries when restCountriesClient returns data")
    void returnsCountriesListWhenClientReturnsData() {

        when(restCountriesClient.getAllCountries(anyMap())).thenReturn(countriesList);

        countriesService = new CountriesService(restCountriesClient, null, null, null, null);
        List<Countries> result = countriesService.getCountries();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should throw RuntimeException when restCountriesClient throws exception")
    void throwsRuntimeExceptionWhenClientThrowsException() {
        when(restCountriesClient.getAllCountries(Map.of("fields", "name,capital,region,subregion")))
                .thenThrow(new RuntimeException("Service unavailable"));

        countriesService = new CountriesService(restCountriesClient, null, null, null, null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> countriesService.getCountries());
        assertTrue(exception.getMessage().contains("Failed to fetch countries data"));
    }
}