package com.veron.controller.api;

import com.veron.dto.EnterpriseDto;
import com.veron.entity.Enterprise;
import com.veron.services.interfaces.EnterpriseInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/enterprises/")
@RequiredArgsConstructor
public class EnterpriseApi {
    private final EnterpriseInterfaces enterpriseInterfaces;
    @PostMapping("create")
    private Map<String,String> createEnterprise(@RequestBody EnterpriseDto enterpriseDto, MultipartFile file){
        return enterpriseInterfaces.createEnterprise(enterpriseDto,file);
    }

    @GetMapping("get-all")
    public List<Enterprise> getAllEnterprise(){
        return enterpriseInterfaces.getAllEnterprise();
    }


    @DeleteMapping("delete-by-id/{idEnterprise}")
    public Map<String,String> deleteByIdEnterprise(@PathVariable("idEnterprise") int idEnterprise){
        return enterpriseInterfaces.deleteByIdEnterprise(idEnterprise);
    }

}
