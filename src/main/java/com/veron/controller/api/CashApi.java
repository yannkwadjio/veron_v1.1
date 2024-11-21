package com.veron.controller.api;

import com.veron.dto.CashDto;
import com.veron.dto.update.CashUpdateDto;
import com.veron.entity.Cash;
import com.veron.services.interfaces.CashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cashes/")
@RequiredArgsConstructor
public class CashApi {
    private final CashInterfaces cashInterfaces;

    @PostMapping("create")
    public Map<String,String> createCasher(@RequestBody CashDto cashDto){
        return cashInterfaces.createCasher(cashDto);
    }

    @GetMapping("get-all")
    public List<Cash> getAllCashers(){
        return cashInterfaces.getAllCashers();
    }

    @GetMapping("get-by-cash/{refCash}")
    public Cash getByCash(@PathVariable("refCash") String refCash){
        return cashInterfaces.getByCash(refCash);
    }


    @PutMapping("update/{refCash}")
    public Map<String,String> updateByRefCash(@RequestBody CashUpdateDto cashUpdateDto,@PathVariable("refCash") String refCash){
        return cashInterfaces.updateByRefCash(cashUpdateDto,refCash);
    }

    @PutMapping("/update-balance")
    public Map<String,String> updateBalance(@RequestParam String agency,@RequestParam String cash,@RequestParam double balance,@RequestParam double newBalance){
        return cashInterfaces.updateBalance(agency,cash,balance,newBalance);
    }
}
