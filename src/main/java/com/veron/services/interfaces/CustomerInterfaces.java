package com.veron.services.interfaces;

import com.veron.dto.TiersDto;
import com.veron.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerInterfaces {
    Map<String, String> createCustomer(TiersDto tiersDto);

    List<Customer> getAllCustomer();

    Map<String, String> updateCustomByRef(String refCustomer,String fullName,String email,String phoneNumber,String adresse);

    Customer getByRefCustomer(String refCustomer);
}
