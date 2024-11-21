package com.veron.controller.api;

import com.veron.dto.TiersDto;
import com.veron.entity.Customer;
import com.veron.services.interfaces.CustomerInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers/")
@RequiredArgsConstructor
public class CustomerApi {
    private final CustomerInterfaces customerInterfaces;
    @PostMapping("create")
    public Map<String,String> createCustomer(@RequestBody TiersDto tiersDto){
        return customerInterfaces.createCustomer(tiersDto);
    }

    @GetMapping("get-all")
    public List<Customer> getAllCustomer(){
        return customerInterfaces.getAllCustomer();
    }


    @GetMapping("get-by-ref/{refCustomer}")
    public Customer getByRefCustomer(@PathVariable("refCustomer") String refCustomer){
        return customerInterfaces.getByRefCustomer(refCustomer);
    }

    @PutMapping("update-by-id")
    public Map<String,String> updateCustomByRef(@RequestParam String refCustomer,@RequestParam String fullName,@RequestParam String email,@RequestParam String phoneNumber,@RequestParam String adresse){
        return customerInterfaces.updateCustomByRef(refCustomer,fullName,email,phoneNumber,adresse);
    }
}
