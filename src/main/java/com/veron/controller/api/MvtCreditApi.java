package com.veron.controller.api;

import com.veron.dto.MvtCreditDto;
import com.veron.entity.MvtCredit;
import com.veron.services.interfaces.MvtCreditInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/mvt-credits/")
@RequiredArgsConstructor
public class MvtCreditApi {
    private final MvtCreditInterfaces mvtCreditInterfaces;

    @PostMapping("create")
    public Map<String,String> createMvtCredit(@RequestBody MvtCreditDto mvtCreditDto){
        return mvtCreditInterfaces.createMvtCredit(mvtCreditDto);
    }

    @GetMapping("get-all-by-date")
    public List<MvtCredit> getAllMvtCreditByDate(@RequestParam LocalDate startDateCredit, @RequestParam LocalDate endDateCredit){
        return mvtCreditInterfaces.getAllMvtCreditByDate(startDateCredit,endDateCredit);
    }
}
