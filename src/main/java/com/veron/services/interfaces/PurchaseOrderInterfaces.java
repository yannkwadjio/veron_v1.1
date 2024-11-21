package com.veron.services.interfaces;

import com.veron.dto.PurchaseOrderDto;
import com.veron.entity.PurchaseOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PurchaseOrderInterfaces {
    Map<String, String> createPurchaseOrder(PurchaseOrderDto purchaseOrderDto);

    List<PurchaseOrder> getAllPurchaseOrder();

    PurchaseOrder getPurchaseOrderByRef(String refPurchase);

    List<PurchaseOrder> getPurchaseOrderByDate(LocalDate startDate, LocalDate endDate);
}
