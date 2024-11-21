package com.veron.controller.api;

import com.veron.dto.AgencyDto;
import com.veron.entity.Agency;
import com.veron.services.interfaces.AgencyInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/agencies/")
@RequiredArgsConstructor
public class AgencyApi {
    private final AgencyInterfaces agencyInterfaces;

    @PostMapping("create")
    public Map<String,String> createAgency(@RequestBody AgencyDto agencyDto){
        return agencyInterfaces.createAgency(agencyDto);
    }

    @GetMapping("get-all")
    public List<Agency> getAllAgency(){
        return agencyInterfaces.getAllAgency();
    }




    @DeleteMapping("delete/{idAgency}")
    public Map<String,String> deleteAgencyById(@PathVariable("idAgency") int idAgency){
        return agencyInterfaces.deleteAgencyById(idAgency);
    }
}
