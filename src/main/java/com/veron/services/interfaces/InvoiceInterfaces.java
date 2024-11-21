package com.veron.services.interfaces;

import com.veron.entity.Invoice;

public interface InvoiceInterfaces {
    Invoice getByRefInvoice(String refInvoice);

    void deleteInvoice(String refInvoice);
}
