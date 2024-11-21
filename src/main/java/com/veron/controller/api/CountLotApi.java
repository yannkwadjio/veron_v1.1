package com.veron.controller.api;

import com.veron.entity.CountLot;
import com.veron.services.interfaces.CountLotInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/counts-lot/")
@RequiredArgsConstructor
public class CountLotApi {
    private final CountLotInterfaces countLotInterfaces;

    @GetMapping("get-all")
    public List<CountLot> getAllCountLot(){
        return countLotInterfaces.getAllCountLot();
    }
}
