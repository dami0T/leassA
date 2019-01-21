package com.leass.leass.service.invoice;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceDto {

    Long id;
    private BigDecimal netValue;                            // wartość netto

    private BigDecimal grossValue;                          // wartość brutto

    private BigDecimal vatValue;                            // kwota VAT

    private BigDecimal paidValue;                           // ile zapłacono do tej pory

    private BigDecimal finalGrossValue;

    private String identifier;

    private Date createDate;                                // data utworzenia faktury

    private BigDecimal operationBalance;                    // saldo operacyjne


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public BigDecimal getPaidValue() {
        return paidValue;
    }

    public void setPaidValue(BigDecimal paidValue) {
        this.paidValue = paidValue;
    }

    public BigDecimal getFinalGrossValue() {
        return finalGrossValue;
    }

    public void setFinalGrossValue(BigDecimal finalGrossValue) {
        this.finalGrossValue = finalGrossValue;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getOperationBalance() {
        return operationBalance;
    }

    public void setOperationBalance(BigDecimal operationBalance) {
        this.operationBalance = operationBalance;
    }
}
