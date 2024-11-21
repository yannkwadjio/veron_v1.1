package com.veron.services.services;

import com.veron.entity.City;
import com.veron.repository.CityRepository;
import com.veron.services.interfaces.CityInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServices implements CityInterfaces {
    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
