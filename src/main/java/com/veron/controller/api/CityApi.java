package com.veron.controller.api;

import com.veron.entity.City;
import com.veron.services.interfaces.CityInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities/")
@RequiredArgsConstructor
public class CityApi {
    private final CityInterfaces cityInterfaces;

    @GetMapping("get-all")
    public List<City> getAllCities(){
        return cityInterfaces.getAllCities();
    }
}
