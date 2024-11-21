package com.veron.services.interfaces;

import com.veron.entity.MissingCash;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MissingCashInterfaces {
    Map<String, String> createMissingCash(MissingCash missingCash);

    List<MissingCash> getAllMissingCash();

    List<MissingCash> getAllMissingByDate(LocalDate startDate, LocalDate endDate);

    MissingCash getByRefMissing(String refMissingCash);
}
