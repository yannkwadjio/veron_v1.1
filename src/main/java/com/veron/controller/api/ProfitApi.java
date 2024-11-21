package com.veron.controller.api;

import com.veron.entity.Profit;
import com.veron.services.interfaces.ProfitInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profit/")
@RequiredArgsConstructor
public class ProfitApi {
    @SuppressWarnings("unused")
    private final ProfitInterfaces profitInterfaces;

    @GetMapping("get-all-by-date")
    public List<Profit> getAllProfitByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return profitInterfaces.getAllProfitByDate(startDate,endDate);
    }

}
