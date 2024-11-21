package com.veron.services.interfaces;

import com.veron.dto.SpendingFamilyDto;
import com.veron.entity.SpendingFamily;

import java.util.List;
import java.util.Map;

public interface SpendingFamilyInterfaces {

    Map<String, String> createSpendingFamily(SpendingFamilyDto spendingFamilyDto);

    List<SpendingFamily> getAllSpendingFamily();
}
