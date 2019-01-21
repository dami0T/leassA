package com.leass.leass.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;



    @Column(name = "net_value")
    private BigDecimal netValue;                            // wartość netto

    @Column(name = "gross_value")
    private BigDecimal grossValue;                          // wartość brutto

    @Column(name = "vat_value")
    private BigDecimal vatValue;                            // kwota VAT

    @Column(name = "paid_value")
    private BigDecimal paidValue;                           // ile zapłacono do tej pory

    @Column(name = "final_gross_value")
    private BigDecimal finalGrossValue;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    @Column(name = "invoice_number")
    private String identifier;

    @Column(name = "create_date")
    private Date createDate;                                // data utworzenia faktury

    @Column(name = "operation_balance")
    private BigDecimal operationBalance;                    // saldo operacyjne


//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sysInvoice", orphanRemoval=true)
//    private List<InvoiceLine> lines = new LinkedList<>();


    private Integer dpd;                                    // DPD faktury


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

    public Integer getDpd() {
        return dpd;
    }

    public void setDpd(Integer dpd) {
        this.dpd = dpd;
    }


    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }
}
