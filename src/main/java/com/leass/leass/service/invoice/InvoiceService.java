package com.leass.leass.service.invoice;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;
import com.leass.leass.service.agreement.AgreementDto;

import java.io.IOException;
import java.util.List;

public interface InvoiceService {

    void save(Invoice invoice);

    List<Invoice> findAll();

    List<Invoice> findByAgreementId(Long agreementId);

    void saveDto(InvoiceDto invoiceDto);

    InvoiceDto getInvoiceById(Long id);

    Invoice getOne(Long id);

    String generateInvoiceNumber(String type, String month, String year);

    void generateFirstInvoice(AgreementDto agreement) throws IOException;

}
