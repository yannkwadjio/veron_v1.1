package com.veron.services.interfaces;

import com.veron.entity.MvtStock;

import java.time.LocalDate;
import java.util.List;

public interface MvtStockInterfaces {
    List<MvtStock> getAllMvtStockByDate(LocalDate startDate, LocalDate endDate);
}
