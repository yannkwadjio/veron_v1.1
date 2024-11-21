package com.veron.controller.api;

import com.veron.dto.CrCashDto;
import com.veron.entity.CrCash;
import com.veron.services.interfaces.CrCashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cr-cash/")
@RequiredArgsConstructor
public class CrCashApi {
    private final CrCashInterfaces crCashInterfaces;

    @PostMapping("create")
    public Map<String,String> createCrCash(@RequestBody CrCashDto crCashDto){
        return crCashInterfaces.createCrCash(crCashDto);
    }

    @GetMapping("get-all")
    public List<CrCash> getAllCrCash(){
        return crCashInterfaces.getAllCrCash();
    }

    @GetMapping("get-by-date")
    public List<CrCash> getAllCrCashByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return crCashInterfaces.getAllCrCashByDate(startDate,endDate);
    }

    @GetMapping("get-by-id/{idCrCash}")
    public CrCash getByIdCrCash(@PathVariable int idCrCash){
        return crCashInterfaces.getByIdCrCash(idCrCash);
    }

    @DeleteMapping("delete-by-id")
    public Map<String,String> deleteCrashById(@RequestParam int idCrCash){
        return crCashInterfaces.deleteCrashById(idCrCash);
    }

}
