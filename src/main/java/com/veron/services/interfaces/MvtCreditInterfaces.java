package com.veron.services.interfaces;

import com.veron.dto.MvtCreditDto;
import com.veron.entity.MvtCredit;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MvtCreditInterfaces {
    Map<String, String> createMvtCredit(MvtCreditDto mvtCreditDto);

    List<MvtCredit> getAllMvtCreditByDate(LocalDate startDateCredit, LocalDate endDateCredit);
}
