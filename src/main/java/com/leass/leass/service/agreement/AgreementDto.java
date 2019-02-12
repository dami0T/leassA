package com.leass.leass.service.agreement;

import com.leass.leass.service.client.ClientDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class AgreementDto {

    private Long id;

    private String agreementNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dissolveDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date closeDate;

    private Long clientId;

    private Long productId;

    private String clientName;

    private BigDecimal liabilities;

    private BigDecimal CurrentBalance;

    private BigDecimal currentBalanceLeft;

    private BigDecimal firstInvoiceAmount;

    private BigDecimal interest;

    private BigDecimal amountOfInstallments;

    private Integer month;

    private Integer monthLeft;

    private byte[] content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getDissolveDate() {
        return dissolveDate;
    }

    public void setDissolveDate(Date dissolveDate) {
        this.dissolveDate = dissolveDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BigDecimal getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(BigDecimal liabilities) {
        this.liabilities = liabilities;
    }

    public BigDecimal getCurrentBalance() {
        return CurrentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        CurrentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalanceLeft() {
        return currentBalanceLeft;
    }

    public void setCurrentBalanceLeft(BigDecimal currentBalanceLeft) {
        this.currentBalanceLeft = currentBalanceLeft;
    }

    public BigDecimal getFirstInvoiceAmount() {
        return firstInvoiceAmount;
    }

    public void setFirstInvoiceAmount(BigDecimal firstInvoiceAmount) {
        this.firstInvoiceAmount = firstInvoiceAmount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getAmountOfInstallments() {
        return amountOfInstallments;
    }

    public void setAmountOfInstallments(BigDecimal amountOfInstallments) {
        this.amountOfInstallments = amountOfInstallments;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getMonthLeft() {
        return monthLeft;
    }

    public void setMonthLeft(Integer monthLeft) {
        this.monthLeft = monthLeft;
    }
}
