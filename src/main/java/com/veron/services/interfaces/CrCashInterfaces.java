package com.veron.services.interfaces;

import com.veron.dto.CrCashDto;
import com.veron.entity.CrCash;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CrCashInterfaces {

    Map<String, String> createCrCash(CrCashDto crCashDto);

    List<CrCash> getAllCrCash();

    List<CrCash> getAllCrCashByDate(LocalDate startDate, LocalDate endDate);

    Map<String, String> deleteCrashById(int idCrCash);

    CrCash getByIdCrCash(int idCrCash);
}
