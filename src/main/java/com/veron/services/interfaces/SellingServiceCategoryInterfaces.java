package com.veron.services.interfaces;

import com.veron.dto.SellingServiceCategoryDto;
import com.veron.entity.SellingServiceCategory;

import java.util.List;
import java.util.Map;

public interface SellingServiceCategoryInterfaces {

    Map<String, String> createSellingServiceCategory(SellingServiceCategoryDto sellingServiceCategoryDto);

    List<SellingServiceCategory> getAllSellingServiceCategory();
}
