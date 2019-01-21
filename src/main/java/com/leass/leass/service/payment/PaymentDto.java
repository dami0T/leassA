package com.leass.leass.service.payment;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDto {

    private String transactionDescription;

    private Agreement agreement;

    private Date dateOfPayment;

    private Date accountingDate;

    private BigDecimal amount;

    private BigDecimal vatValue;

//    private Invoice invoice;

    private String senderAccountNumber;

    private BigDecimal operationBalance;
}
