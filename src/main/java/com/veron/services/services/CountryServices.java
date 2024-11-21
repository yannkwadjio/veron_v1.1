package com.veron.services.services;

import com.veron.entity.Country;
import com.veron.repository.CountryRepository;
import com.veron.services.interfaces.CountryInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServices implements CountryInterfaces {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> countryInterfaces() {
        return countryRepository.findAll();
    }
}
