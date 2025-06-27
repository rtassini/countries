package com.itau.countries.client.restcountries;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "restcountries", url = "https://restcountries.com/v3.1")
public interface RestCountriesClient {

    @GetMapping("/all")
    List<Countries> getAllCountries(@SpringQueryMap Map<String, String> queryParameters);

    @GetMapping("/name/{name}?fullText=true")
    List<Countries> getCountriesByName(@SpringQueryMap Map<String, String> queryParameters, @PathVariable String name);

    @GetMapping("/region/{region}?fullText=true")
    List<Countries> getCountriesByRegion(@SpringQueryMap Map<String, String> queryParameters, @PathVariable String region);

    @GetMapping("/capital/{capital}?fullText=true")
    List<Countries> getCountriesByCapital(@SpringQueryMap Map<String, String> queryParameters, @PathVariable String capital);

    @GetMapping("/subregion/{subregion}?fullText=true")
    List<Countries> getCountriesBySubregion(@SpringQueryMap Map<String, String> queryParameters, @PathVariable String subregion);
}
