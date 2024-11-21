package com.veron.services.interfaces;

import com.veron.dto.EnterpriseDto;
import com.veron.entity.Enterprise;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface EnterpriseInterfaces {
    Map<String, String> createEnterprise(EnterpriseDto enterpriseDto, MultipartFile file);

    List<Enterprise> getAllEnterprise();

    Map<String, String> deleteByIdEnterprise(int idEnterprise);



}
