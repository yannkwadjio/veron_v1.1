package com.veron.controller.api;

import com.veron.dto.SpentDto;
import com.veron.entity.Spent;
import com.veron.services.interfaces.SpentInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/spents/")
@RequiredArgsConstructor
public class SpentApi {
    private final SpentInterfaces spentInterfaces;

    @PostMapping("create")
    public Map<String,String> createSpent(@RequestBody SpentDto spentDto){
        return spentInterfaces.createSpent(spentDto);
    }


    @GetMapping("get-all")
    public List<Spent> getAllSpents(){
        return spentInterfaces.getAllSpents();
    }

}
