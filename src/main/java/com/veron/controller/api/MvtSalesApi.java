package com.veron.controller.api;

import com.veron.entity.MvtSales;
import com.veron.services.interfaces.MvtSalesInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mvt-sales/")
@RequiredArgsConstructor
public class MvtSalesApi {
    private final MvtSalesInterfaces mvtSalesInterfaces;

    @GetMapping("get-by-date")
    public List<MvtSales> getMvtSaleByDate(@RequestParam LocalDate startDate,LocalDate endDate){
        return mvtSalesInterfaces.getMvtSaleByDate(startDate,endDate);
    }

}
