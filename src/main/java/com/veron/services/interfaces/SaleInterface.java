package com.veron.services.interfaces;

import com.veron.dto.SaleDto;
import com.veron.entity.Sale;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SaleInterface {
    Map<String, String> createSale(SaleDto saleDto);

    List<Sale> getAllSales();

    List<Sale> getAllSaleByDate(LocalDate startDate, LocalDate endDate);
}
