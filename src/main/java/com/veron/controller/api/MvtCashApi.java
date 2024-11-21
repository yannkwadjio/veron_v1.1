package com.veron.controller.api;

import com.veron.dto.MvtCashDto;
import com.veron.entity.MvtCash;
import com.veron.services.interfaces.MvtCashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/mvt-cash/")
@RequiredArgsConstructor
public class MvtCashApi {
    private final MvtCashInterfaces mvtCashInterfaces;

    @PostMapping("create")
    public Map<String,String> createMvtCash(@RequestBody MvtCashDto mvtCashDto){
        return mvtCashInterfaces.createMvtCash(mvtCashDto);
    }

    @GetMapping("get-all")
    public List<MvtCash> getAllByMvtCash(){
        return mvtCashInterfaces.getAllByMvtCash();
    }

    @GetMapping("get-mvt-cash-by-date")
    public List<MvtCash> getAllMvtCashByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return mvtCashInterfaces.getAllMvtCashByDate(startDate,endDate);
    }

    @GetMapping("get-mvt-by-ref/{refOperationCash}")
    public MvtCash getMvtByRef(@PathVariable String refOperationCash){
        return mvtCashInterfaces.getMvtByRef(refOperationCash);
    }
}
