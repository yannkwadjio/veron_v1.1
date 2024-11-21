package com.veron.services.services;

import com.veron.entity.MvtSales;
import com.veron.repository.MvtSalesRepository;
import com.veron.services.interfaces.MvtSalesInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MvtSalesServices implements MvtSalesInterfaces {
    private final MvtSalesRepository mvtSalesRepository;

    @Override
    public List<MvtSales> getMvtSaleByDate(LocalDate startDate, LocalDate endDate) {
        return mvtSalesRepository.findBySaleDateBetween(startDate,endDate);
    }
}
