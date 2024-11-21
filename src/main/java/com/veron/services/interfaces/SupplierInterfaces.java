package com.veron.services.interfaces;

import com.veron.dto.TiersDto;
import com.veron.entity.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierInterfaces {
    Map<String, String> createSupplier(TiersDto tiersDto);

    List<Supplier> getAllSupplier();
}
