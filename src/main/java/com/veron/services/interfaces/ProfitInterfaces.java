package com.veron.services.interfaces;

import com.veron.entity.Profit;

import java.time.LocalDate;
import java.util.List;

public interface ProfitInterfaces {
    List<Profit> getAllProfitByDate(LocalDate startDate, LocalDate endDate);
}
