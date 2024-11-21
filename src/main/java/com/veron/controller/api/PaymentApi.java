package com.veron.controller.api;

import com.veron.services.interfaces.PaymentInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments/")
@RequiredArgsConstructor
public class PaymentApi {
    private final PaymentInterfaces paymentInterfaces;

    @PostMapping("create")
    public Map<String,String> createPayment(@RequestParam String refInvoice,@RequestParam String customer,@RequestParam double payment,@RequestParam String paymentMethod,@RequestParam String receiveAccount,@RequestParam String refCash,@RequestParam String motif,@RequestParam double amount){
        return paymentInterfaces.createPayment(refInvoice,customer,payment,paymentMethod,receiveAccount,refCash,motif,amount);
    }
}
