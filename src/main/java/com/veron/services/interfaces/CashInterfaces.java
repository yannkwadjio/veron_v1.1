package com.veron.services.interfaces;

import com.veron.dto.CashDto;
import com.veron.dto.update.CashUpdateDto;
import com.veron.entity.Cash;

import java.util.List;
import java.util.Map;

public interface CashInterfaces {
    Map<String, String> createCasher(CashDto cashDto);

    List<Cash> getAllCashers();

    Map<String, String> updateByRefCash(CashUpdateDto cashUpdateDto, String refCash);

    Cash getByCash(String refCash);

    Map<String, String> updateBalance(String agency, String cash, double balance, double newBalance);
}
