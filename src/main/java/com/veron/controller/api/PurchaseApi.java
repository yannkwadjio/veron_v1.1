package com.veron.controller.api;

import com.veron.entity.Purchase;
import com.veron.services.interfaces.PurchaseInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchases/")
@RequiredArgsConstructor
public class PurchaseApi {
    private final PurchaseInterfaces purchaseInterfaces;

    @PostMapping("create")
    public Map<String,String> createPurchase(@RequestParam("refCashValue") String refCashValue,@RequestParam("listProduct") Map<String,String> listProduct,@RequestParam("paymentMethodValue") String paymentMethodValue,@RequestParam("supplierValue") String supplierValue,@RequestParam("totalPriceValue") String totalPriceValue,@RequestParam("remiseValue") String remiseValue){
        return purchaseInterfaces.createPurchase(refCashValue,listProduct,paymentMethodValue,supplierValue,totalPriceValue,remiseValue);
    }

    @GetMapping("get-purchase-by-date")
    List<Purchase> getAllPurchaseByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        return purchaseInterfaces.getAllPurchaseByDate(startDate,endDate);
    }

}
