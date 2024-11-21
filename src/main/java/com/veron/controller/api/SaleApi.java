package com.veron.controller.api;

import com.veron.dto.SaleDto;
import com.veron.entity.Sale;
import com.veron.services.interfaces.SaleInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sales/")
@RequiredArgsConstructor
public class SaleApi {
    private final SaleInterface saleInterface;

    @PostMapping("create")
    public Map<String,String> createSale(@RequestBody SaleDto saleDto){
        return saleInterface.createSale(saleDto);
    }

    @GetMapping("get-all")
    public List<Sale> getAllSales(){
       return saleInterface.getAllSales();
    }

    @GetMapping("get-mvt-sale-by-date")
    public List<Sale> getAllSaleByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return saleInterface.getAllSaleByDate(startDate,endDate);
    }
}
