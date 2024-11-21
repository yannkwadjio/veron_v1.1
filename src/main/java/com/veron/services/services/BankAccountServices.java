package com.veron.services.services;

import com.veron.dto.update.BankAccountDto;
import com.veron.entity.BankAccount;
import com.veron.repository.BankAccountRepository;
import com.veron.services.interfaces.BankAccountInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServices implements BankAccountInterfaces {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public Map<String, String> createBankAccount(BankAccountDto bankAccountDto) {
        Map<String,String> response=new HashMap<>();
        Optional<BankAccount> exBankAccount=bankAccountRepository.findByBankAccountNumber(bankAccountDto.getBankAccountNumber());
        if(exBankAccount.isEmpty()){
            if(!bankAccountDto.getBankAccountNumber().isEmpty()){
                if(!bankAccountDto.getBank().isEmpty()){
                    BankAccount bankAccount=new BankAccount();
                    bankAccount.setBank(bankAccountDto.getBank().toUpperCase());
                    bankAccount.setBankAccountNumber(bankAccountDto.getBankAccountNumber());
                    bankAccount.setBalanceCredit(0);
                    bankAccount.setBalance(0);
                    bankAccount.setEntity("BANQUE");
                    bankAccount.setUserCreated("admin-veron@gmail.com");
                    bankAccountRepository.save(bankAccount);
                    response.put("message","Compte créé avec succès");
                }else{
                    response.put("message","Banque non renseignée");
                }
            }else{
                response.put("message","Nº de compte non renseigné");
            }
        }else{
            response.put("message","Ce numéro de compte existe déjà");
        }
        return response;
    }

    @Override
    public List<BankAccount> getAllBankAcoount() {
        return bankAccountRepository.findAll();
    }
}
