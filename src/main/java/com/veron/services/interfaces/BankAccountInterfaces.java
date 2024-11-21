package com.veron.services.interfaces;

import com.veron.dto.update.BankAccountDto;
import com.veron.entity.BankAccount;

import java.util.List;
import java.util.Map;

public interface BankAccountInterfaces {
    Map<String, String> createBankAccount(BankAccountDto bankAccountDto);

    List<BankAccount> getAllBankAcoount();
}
