package com.veron.exceptions;

import com.veron.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MvtCreditNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMvtCreditNotFoundException(MvtCreditNotFoundException mcx){
      ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), mcx.getMessage());
    return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AgencyNotFountException.class)
    public ResponseEntity<ErrorResponse> handleAgencyNotFountException(AgencyNotFountException agencyx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), agencyx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CashNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCashNotFountException(CashNotFoundException cashx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), cashx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStoreNotFountException(StoreNotFoundException storex){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), storex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePurchaseNotFountException(PurchaseNotFoundException purchasex){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), purchasex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductStockNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductStockNotFountException(ProductStockNotFoundException psx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), psx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MvtCashNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMvtCashNotFountException(MvtCashNotFoundException mvx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), mvx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SaleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSaleNotFoundException(SaleNotFoundException salex){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), salex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EnterpriseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEnterpriseNotFoundException(EnterpriseNotFoundException entex){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), entex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PayementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PayementNotFoundException payx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), payx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvoiceNotFoundException(InvoiceNotFoundException invx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), invx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerNoFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNoFoundException(CustomerNoFoundException cusx){
        ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(), cusx.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }


}
