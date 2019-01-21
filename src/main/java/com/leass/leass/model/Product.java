package com.leass.leass.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String referenceNumber;

    private String description;

    private Boolean isRent;

    @Column(name = "estimated_time_of_lease")
    private Date estimatedTimeOfLease;

    public boolean isSelected(Long productId){
        if (productId != null) {
            return productId.equals(id);
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRent() {
        return isRent;
    }

    public void setRent(Boolean rent) {
        isRent = rent;
    }

    public Date getEstimatedTimeOfLease() {
        return estimatedTimeOfLease;
    }

    public void setEstimatedTimeOfLease(Date estimatedTimeOfLease) {
        this.estimatedTimeOfLease = estimatedTimeOfLease;
    }
}
