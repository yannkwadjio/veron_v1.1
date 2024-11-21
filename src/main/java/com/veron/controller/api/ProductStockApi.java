package com.veron.controller.api;

import com.veron.dto.ApproStoreDto;
import com.veron.dto.FounitureOutDto;
import com.veron.dto.ProductStockDto;
import com.veron.entity.ProductStock;
import com.veron.services.interfaces.ProductStockInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products-stock/")
@RequiredArgsConstructor
public class ProductStockApi {
    private final ProductStockInterfaces productStockInterfaces;
    @PostMapping("create")
    public Map<String,String> createProductStock(@RequestBody ProductStockDto productStockDto){
        return productStockInterfaces.createProductStock(productStockDto);
    }

    @PostMapping("appro-store")
    public Map<String,String> createApproStock(@RequestBody ApproStoreDto approStoreDto){
        return productStockInterfaces.createApproStock(approStoreDto);
    }

    @PostMapping("out-store")
    public Map<String,String> createOutStock(@RequestBody FounitureOutDto founitureOutDto){
        return productStockInterfaces.createOutStock(founitureOutDto);
    }

    @GetMapping("get-all")
    public List<ProductStock> getAllProductStock(){
      return productStockInterfaces.getAllProductStock();
    }


}
