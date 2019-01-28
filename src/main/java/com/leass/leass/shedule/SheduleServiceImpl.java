package com.leass.leass.shedule;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;
import com.leass.leass.model.InvoiceLine;
import com.leass.leass.service.ExporterService;
import com.leass.leass.service.agreement.AgreementDto;
import com.leass.leass.service.agreement.AgreementService;
import com.leass.leass.service.invoice.InvoiceService;
import org.assertj.core.util.DateUtil;
import org.assertj.core.util.VisibleForTesting;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class SheduleServiceImpl implements SheduleService{

    @Autowired
    AgreementService agreementService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ExporterService exporterService;


    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Scheduled(cron = "0 0 5 * * *")
    public void createInvoiceMonth() throws IOException {

        Date currentDate = new Date();
        List<Agreement> agreements = agreementService.findAll();
        DateTime date = new DateTime();
        String postName = "/home/damian/Pulpit/";
        String fileName = "";
        for (Agreement agreement : agreements) {
            if (plusDays(agreement.getLastCreateInvoiceDate() == null ? agreement.getCreateDate() : agreement.getLastCreateInvoiceDate() , 1).after(currentDate)
                    && agreement.getMonthLeft() > 0) {
                BigDecimal amount = agreement.getAmountOfInstallments();
                BigDecimal vatValue = percentFromValue(new BigDecimal("23"), amount, 2);
                BigDecimal netValue = amount.subtract(vatValue);


                if (agreement.getCreateDate().after(currentDate)) ;
                Invoice invoice = new Invoice();
                invoice.setCreateDate(new Date());
                invoice.setIdentifier(nextInvoiceNumber("S", date.getMonthOfYear() + "", date.getYear() + ""));
                invoice.setGrossValue(amount);
                invoice.setFinalGrossValue(amount);
                invoice.setOperationBalance(amount);
                invoice.setPaidValue(new BigDecimal("0.00"));
                invoice.setVatValue(vatValue);
                invoice.setNetValue(netValue);
                invoice.setAgreement(agreement);


                fileName = postName+ invoice.getAgreement().getClient().getId() +"/"+ "Fa-Vat_"+ invoice.getIdentifier() +".txt";

                invoice.setFileUrl(fileName);
                exporterService.write(fileName, invoice);
                invoiceService.save(invoice);
                agreement.setLastCreateInvoiceDate(currentDate);
                agreement.setMonthLeft(agreement.getMonthLeft() -1);
                agreementService.save(agreement);
            }
        }

    }

    public String nextInvoiceNumber(String type, String month, String year) {
        String invNumb = invoiceService.generateInvoiceNumber(type, month, year);
        String sequenceName = invNumb + "_" + type + "_" + year + "_" + month;
        return sequenceName;
    }

    public static Date plusDays(Date startDate, Integer numberOfDays) {
        if (startDate == null) return null;
        DateTime date = new DateTime(startDate.getTime());
        return date.plusDays(numberOfDays).toDate();
    }

    public static BigDecimal percentFromValue(BigDecimal percentage, BigDecimal amount, int scale) {
        if (percentage == null || amount == null) {
            return null;
        }
        return amount.multiply(percentage).divide(new BigDecimal("100"), scale, BigDecimal.ROUND_HALF_UP);
    }
}
