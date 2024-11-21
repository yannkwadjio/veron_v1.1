package com.veron.controller.api;

import com.veron.dto.update.BankAccountDto;
import com.veron.entity.BankAccount;
import com.veron.services.interfaces.BankAccountInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bank-account/")
@RequiredArgsConstructor
public class BankAccountApi {

    private final BankAccountInterfaces bankAccountInterfaces;

    @PostMapping("create")
    public Map<String,String> createBankAccount(@RequestBody BankAccountDto bankAccountDto){
        return bankAccountInterfaces.createBankAccount(bankAccountDto);
    }

    @GetMapping("get-all")
    public List<BankAccount> getAllBankAcoount(){
        return bankAccountInterfaces.getAllBankAcoount();
    }
}
