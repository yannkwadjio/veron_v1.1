package com.veron.controller.api;

import com.veron.services.interfaces.EntiteInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entities/")
@RequiredArgsConstructor
public class EntiteApi {
    private final EntiteInterfaces entiteInterfaces;
    @GetMapping("get-all")
    public List<String> getAllEntite(){
        return entiteInterfaces.getAllEntite();
    }
}
