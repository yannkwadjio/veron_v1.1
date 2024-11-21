package com.veron.controller.api;

import com.veron.entity.MissingCash;
import com.veron.services.interfaces.MissingCashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/missing-cash/")
@RequiredArgsConstructor
public class MissingCashApi {
    private final MissingCashInterfaces missingCashInterfaces;

    @PostMapping("create")
    public Map<String,String> createMissingCash(@RequestBody MissingCash missingCash){
     return  missingCashInterfaces.createMissingCash(missingCash);
    }

    @GetMapping("get-all")
    public List<MissingCash> getAllMissingCash(){
        return missingCashInterfaces.getAllMissingCash();
    }

    @GetMapping("get-by-ref/{refMissingCash}")
    public MissingCash getByRefMissing(@PathVariable String refMissingCash){
        return missingCashInterfaces.getByRefMissing(refMissingCash);
    }

    @GetMapping("get-by-date")
    public List<MissingCash> getAllMissingByDate(@RequestParam LocalDate startDate,LocalDate endDate){
        return missingCashInterfaces.getAllMissingByDate(startDate,endDate);
    }


}
