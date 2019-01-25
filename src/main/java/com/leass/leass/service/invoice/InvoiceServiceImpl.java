package com.leass.leass.service.invoice;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;
import com.leass.leass.repository.AgreementSpecification;
import com.leass.leass.repository.InvoiceRepository;
import com.leass.leass.repository.InvoiceSpecification;
import com.leass.leass.service.ExporterService;
import com.leass.leass.service.agreement.AgreementDto;
import com.leass.leass.service.agreement.AgreementService;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    InvoiceSpecification invoiceSpecification;

    @Autowired
    AgreementService agreementService;

    @Autowired
    ExporterService exporterService;


    private InvoiceDto convertToInvoiceDto(Invoice invoice) {
        InvoiceDto invoiceDto = modelMapper.map(invoice, InvoiceDto.class);
        return invoiceDto;
    }

    private Invoice convertToInvoiceEntity(InvoiceDto invoiceDto){
        Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
        return invoice;
    }


    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> findAll() {
        return new ArrayList<>(invoiceRepository.findAll());
    }

    @Override
    public List<Invoice> findByAgreementId(Long agreementId) {
        InvoiceCriteria invoiceCriteria = new InvoiceCriteria();
        invoiceCriteria.setAgreementId(agreementId);
        invoiceCriteria.setPaid(true);
        List<Invoice> invoices = invoiceRepository.findAll(invoiceSpecification.invoiceByQuery(invoiceCriteria));
        return invoices;
    }


    @Override
    public void saveDto(InvoiceDto invoiceDto) {
        Invoice invoice = convertToInvoiceEntity(invoiceDto);
        save(invoice);
    }

    @Override
    public InvoiceDto getInvoiceById(Long id) {
        Optional<Invoice> agreement = invoiceRepository.findById(id);
        return convertToInvoiceDto(agreement.get());
    }

    @Override
    public Invoice getOne(Long id) {
        Invoice invoice = invoiceRepository.getOne(id);
        return invoice;
    }

    @Override
    public String generateInvoiceNumber(String type, String month, String year) {

        Long seq = invoiceRepository.getNextSeriesId();
        return seq.toString();
    }

    @Override
    public void generateFirstInvoice(AgreementDto agreement) throws IOException {
        BigDecimal amount = agreement.getFirstInvoiceAmount();
        BigDecimal vatValue = percentFromValue(new BigDecimal("23"), amount, 2);
        BigDecimal netValue = amount.subtract(vatValue);
        DateTime date = new DateTime();
        Invoice invoice = new Invoice();
        invoice.setCreateDate(new Date());
        invoice.setIdentifier(nextInvoiceNumber("S", date.getMonthOfYear() + "", date.getYear() + ""));
        invoice.setGrossValue(amount);
        invoice.setFinalGrossValue(amount);
        invoice.setOperationBalance(amount);
        invoice.setVatValue(vatValue);
        invoice.setNetValue(netValue);
        invoice.setPaidValue(new BigDecimal("0.00"));
        invoice.setAgreement(agreementService.getOne(agreement.getId()));
        save(invoice);
        exporterService.write("",invoice);
    }

    public String nextInvoiceNumber(String type, String month, String year) {
        String invNumb = generateInvoiceNumber(type, month, year);
        String sequenceName = invNumb + "_" + type + "_" + year + "_" + month;
        return sequenceName;
    }

    public static BigDecimal percentFromValue(BigDecimal percentage, BigDecimal amount, int scale) {
        if (percentage == null || amount == null) {
            return null;
        }
        return amount.multiply(percentage).divide(new BigDecimal("100"), scale, BigDecimal.ROUND_HALF_UP);
    }
}
