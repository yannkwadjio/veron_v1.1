package com.veron.controller.api;

import com.veron.services.interfaces.ResetInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reset/")
@RequiredArgsConstructor
public class ResetApi {
    private final ResetInterfaces resetInterfaces;

    @DeleteMapping("delete-all")
    public void deleteAllBdd(){
        resetInterfaces.deleteAllBdd();
    }
}
