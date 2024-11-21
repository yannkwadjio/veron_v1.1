package com.veron.services.interfaces;

import com.veron.dto.MvtCashDto;
import com.veron.entity.MvtCash;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MvtCashInterfaces {
    Map<String, String> createMvtCash(MvtCashDto mvtCashDto);

    List<MvtCash> getAllByMvtCash();

    List<MvtCash> getAllMvtCashByDate(LocalDate startDate, LocalDate endDate);

    MvtCash getMvtByRef(String refOperationCash);
}
