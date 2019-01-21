package com.leass.leass.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "agreement")
public class Agreement implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "agreement_number", unique = true, nullable = false)
    private String agreementNumber;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "sign_date")
    private Date signDate;

    @Column(name = "close_date")
    private Date closeDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal liabilities;

    private BigDecimal currentBalance;

    private BigDecimal currentBalanceLeft;

    private BigDecimal amountOfInstallments;

    @Column(name = "first_invoice_amount")
    private BigDecimal firsInvoiceAmount;

    private int month;

    @Column(name = "last_create_invoice_date")
    private Date lastCreateInvoiceDate;

    private Agreement create(){
        return new Agreement();
    }

    public boolean isSelected(Long agreementId){
        if (agreementId != null) {
            return agreementId.equals(id);
        }
        return false;
    }

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

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(BigDecimal liabilities) {
        this.liabilities = liabilities;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalanceLeft() {
        return currentBalanceLeft;
    }

    public void setCurrentBalanceLeft(BigDecimal currentBalanceLeft) {
        this.currentBalanceLeft = currentBalanceLeft;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Date getLastCreateInvoiceDate() {
        return lastCreateInvoiceDate;
    }

    public void setLastCreateInvoiceDate(Date lastCreateInvoiceDate) {
        this.lastCreateInvoiceDate = lastCreateInvoiceDate;
    }

    public BigDecimal getFirsInvoiceAmount() {
        return firsInvoiceAmount;
    }

    public void setFirsInvoiceAmount(BigDecimal firsInvoiceAmount) {
        this.firsInvoiceAmount = firsInvoiceAmount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getAmountOfInstallments() {
        return amountOfInstallments;
    }

    public void setAmountOfInstallments(BigDecimal amountOfInstallments) {
        this.amountOfInstallments = amountOfInstallments;
    }
}
