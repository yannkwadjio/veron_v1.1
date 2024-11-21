package com.veron.controller.api;

import com.veron.dto.SpendingFamilyDto;
import com.veron.entity.SpendingFamily;
import com.veron.services.interfaces.SpendingFamilyInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/spending-families/")
@RequiredArgsConstructor
public class SpendingFamilyApi {
    private final SpendingFamilyInterfaces spendingFamilyInterfaces;

    @PostMapping("create")
    public Map<String,String> createSpendingFamily(@RequestBody SpendingFamilyDto spendingFamilyDto){
        return spendingFamilyInterfaces.createSpendingFamily(spendingFamilyDto);
    }

    @GetMapping("get-all")
    public List<SpendingFamily> getAllSpendingFamily(){
        return spendingFamilyInterfaces.getAllSpendingFamily();
    }
}
