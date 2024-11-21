package com.veron.controller.api;

import com.veron.entity.Invoice;
import com.veron.services.interfaces.InvoiceInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices/")
@RequiredArgsConstructor
public class InvoiceApi {
    @SuppressWarnings("unused")
    private final InvoiceInterfaces invoiceInterfaces;


    @GetMapping("get-by-ref-Invoice/{refInvoice}")
    public Invoice getByRefInvoice(@PathVariable("refInvoice") String refInvoice){
        return invoiceInterfaces.getByRefInvoice(refInvoice);
    }

    @DeleteMapping("/delete-invoice/{refInvoice}")
    public void deleteInvoice(@PathVariable String refInvoice){
        invoiceInterfaces.deleteInvoice(refInvoice);
    }

}
