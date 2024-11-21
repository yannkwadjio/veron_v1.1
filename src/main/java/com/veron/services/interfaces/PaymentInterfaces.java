package com.veron.services.interfaces;

import java.util.Map;

public interface PaymentInterfaces {

    Map<String, String> createPayment(String refInvoice, String customer, double payment, String paymentMethod, String receiveAccount, String refCash, String motif,double amount);
}
