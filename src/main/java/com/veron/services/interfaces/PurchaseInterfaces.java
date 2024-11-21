package com.veron.services.interfaces;


import com.veron.entity.Purchase;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PurchaseInterfaces {
    Map<String, String> createPurchase(String refCashValue,Map<String, String> listProduct,String paymentMethodValue,String supplierValue,String totalPriceValue,String remiseValue);

    List<Purchase> getAllPurchaseByDate(LocalDate startDate, LocalDate endDate);
}
