package com.veron.controller.api;

import com.veron.entity.MvtStock;
import com.veron.services.interfaces.MvtStockInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mvt-stock/")
@RequiredArgsConstructor
public class MvtStockApi {
    @SuppressWarnings("unused")
    private final MvtStockInterfaces mvtStockInterfaces;

    @GetMapping("get-all-mvt-by-date")
    public List<MvtStock> getAllMvtStockByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return mvtStockInterfaces.getAllMvtStockByDate(startDate,endDate);
    }


}
