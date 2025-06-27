package com.itau.countries.controller;

import com.itau.countries.service.CountriesService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("countries")
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }


    @io.swagger.v3.oas.annotations.Operation(summary = "Get all countries", description = "Fetches a list of all countries")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Return a CEP address"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllCountries()  {
        log.info("Fetching all countries");
        try {
            return ResponseEntity.ok(countriesService.getCountries());
        } catch (Exception e) {
            log.error("Error fetching countries data", e);
            return ResponseEntity.status(500).body("Failed to fetch countries data");
        }
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get all countries", description = "Fetches a list of countries by filter and value" +
            "the filter can be 'name', 'capital', 'region', or 'subregion'")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Return a CEP address"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request, invalid filter parameter"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/filter/{filter}/value/{value}")
    public ResponseEntity<?> getCountriesByFilter(@PathVariable String filter,
                                                  @PathVariable String value ) {
        log.info("Fetching countries by {}: {}", filter, value);


        if (!filter.equalsIgnoreCase("name") && !filter.equalsIgnoreCase("capital") &&
            !filter.equalsIgnoreCase("region") && !filter.equalsIgnoreCase("subregion")) {
            return ResponseEntity.badRequest().body("Invalid filter parameter. Allowed values are: name, capital, region, subregion.");
        }
        try {
            return ResponseEntity.ok(countriesService.getCountriesByFilter(filter, value));
        } catch (Exception e) {
            log.error("Error fetching countries data", e);
            return ResponseEntity.status(500).body("Failed to fetch countries data");
        }
    }
}
