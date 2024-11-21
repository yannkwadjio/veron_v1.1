package com.veron.services.interfaces;

import com.veron.dto.SellingServiceDto;
import com.veron.entity.SellingService;

import java.util.List;
import java.util.Map;

public interface SellingServiceInterfaces {
    Map<String, String> createService(SellingServiceDto sellingServiceDto);

    List<SellingService> getAllServices();

    Map<String, String> deleteServiceSelling(int idService);

    SellingService getSellingServiceByName(String name);


    Map<String, String> updateSellingServiceByName(String name,double price,String description);

}
