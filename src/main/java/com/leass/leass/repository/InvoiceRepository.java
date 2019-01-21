package com.leass.leass.repository;

import com.leass.leass.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    @Override
    List<Invoice> findAll();

    @Query(value = "select nextval('inv_seq')", nativeQuery =
            true)
    Long getNextSeriesId();
}
