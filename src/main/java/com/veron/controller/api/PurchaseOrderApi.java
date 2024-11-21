package com.veron.controller.api;

import com.veron.dto.PurchaseOrderDto;
import com.veron.entity.PurchaseOrder;
import com.veron.services.interfaces.PurchaseOrderInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchases-order/")
@RequiredArgsConstructor
public class PurchaseOrderApi {

    private final PurchaseOrderInterfaces purchaseOrderInterfaces;

    @PostMapping("create")
    public Map<String,String> createPurchaseOrder(@RequestBody PurchaseOrderDto purchaseOrderDto){
        return purchaseOrderInterfaces.createPurchaseOrder(purchaseOrderDto);
    }

    @GetMapping("get-all")
    public List<PurchaseOrder> getAllPurchaseOrder(){
    return purchaseOrderInterfaces.getAllPurchaseOrder();
    }

    @GetMapping("get-by-ref/{refPurchase}")
    public PurchaseOrder getPurchaseOrderByRef(@PathVariable String refPurchase){
        return purchaseOrderInterfaces.getPurchaseOrderByRef(refPurchase);
    }

    @GetMapping("get-by-date")
    public List<PurchaseOrder> getPurchaseOrderByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return purchaseOrderInterfaces.getPurchaseOrderByDate(startDate,endDate);
    }

}
