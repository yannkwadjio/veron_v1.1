package com.veron.controller.api;

import com.veron.dto.TiersDto;
import com.veron.entity.Supplier;
import com.veron.services.interfaces.SupplierInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/suppliers/")
@RequiredArgsConstructor
public class SupplierApi {
private final SupplierInterfaces supplierInterfaces;

@PostMapping("create")
    public Map<String,String> createSupplier(@RequestBody TiersDto tiersDto){
    return supplierInterfaces.createSupplier(tiersDto);
}

@GetMapping("get-all")
    public List<Supplier> getAllSupplier(){
    return supplierInterfaces.getAllSupplier();
}
}
