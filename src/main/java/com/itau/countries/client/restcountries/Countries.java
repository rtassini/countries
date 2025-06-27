package com.itau.countries.client.restcountries;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public record Countries(Name name, List<String> capital, String region, String subregion) {


    @NotNull
    @Override
    public String toString() {
        return "Countries{" +
                "name=" + name +
                ", capital='" + capital + '\'' +
                ", region='" + region + '\'' +
                ", subregion='" + subregion + '\'' +
                '}';
    }
}
