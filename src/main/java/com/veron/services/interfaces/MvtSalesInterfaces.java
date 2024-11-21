package com.veron.services.interfaces;

import com.veron.entity.MvtSales;

import java.time.LocalDate;
import java.util.List;

public interface MvtSalesInterfaces {
    List<MvtSales> getMvtSaleByDate(LocalDate startDate, LocalDate endDate);
}
