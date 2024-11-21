package com.veron.controller.api;

import com.veron.entity.Country;
import com.veron.services.interfaces.CountryInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries/")
@RequiredArgsConstructor
public class CountryApi {
    private final CountryInterfaces countryInterfaces;

    @GetMapping("get-all")
    public List<Country> getAllCountries(){
        return countryInterfaces.countryInterfaces();
    }
}
