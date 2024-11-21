package com.veron.controller.api;

import com.veron.dto.SellingServiceCategoryDto;
import com.veron.entity.SellingServiceCategory;
import com.veron.services.interfaces.SellingServiceCategoryInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/selling-services-category/")
@RequiredArgsConstructor
public class SellingServiceCategoryApi {
    private final SellingServiceCategoryInterfaces sellingServiceCategoryInterfaces;

    @PostMapping("create")
    public Map<String,String> createSellingServiceCategory(@RequestBody SellingServiceCategoryDto sellingServiceCategoryDto){
        return sellingServiceCategoryInterfaces.createSellingServiceCategory(sellingServiceCategoryDto);
    }

    @GetMapping("get-all")
    public List<SellingServiceCategory> getAllSellingServiceCategory(){
        return sellingServiceCategoryInterfaces.getAllSellingServiceCategory();
    }
}
