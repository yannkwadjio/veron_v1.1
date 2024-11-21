package com.veron.services.interfaces;

import com.veron.dto.ApproStoreDto;
import com.veron.dto.FounitureOutDto;
import com.veron.dto.ProductStockDto;
import com.veron.entity.ProductStock;

import java.util.List;
import java.util.Map;

public interface ProductStockInterfaces {
    Map<String, String> createProductStock(ProductStockDto productStockDto);

    List<ProductStock> getAllProductStock();

    Map<String, String> createApproStock(ApproStoreDto approStoreDto);

    Map<String, String> createOutStock(FounitureOutDto founitureOutDto);
}
