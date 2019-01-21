package com.leass.leass.shedule;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;
import com.leass.leass.model.InvoiceLine;
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

import java.math.BigDecimal;
import java.util.*;

@Component
public class SheduleServiceImpl implements SheduleService{

    @Autowired
    AgreementService agreementService;

    @Autowired
    InvoiceService invoiceService;


    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Scheduled(cron = "0 0 1 * * *")
    public void createInvoiceMonth() {

        Date currentDate = new Date();
        List<Agreement> agreements = agreementService.findAll();
        DateTime date = new DateTime();


        for (Agreement agreement : agreements) {
            if (plusDays(agreement.getLastCreateInvoiceDate() == null ? agreement.getCreateDate() : agreement.getLastCreateInvoiceDate() , 1).after(currentDate)) {
                BigDecimal basic = agreement.getLiabilities().divide(new BigDecimal(agreement.getMonth()), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal interest = basic.multiply(agreement.getInterest());
                BigDecimal vatValue = percentFromValue(new BigDecimal("23"), basic, 2);
                BigDecimal netValue = basic.subtract(vatValue);

                BigDecimal amount = basic.add(interest);

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

                invoiceService.save(invoice);
                agreement.setLastCreateInvoiceDate(currentDate);
                // przenieść do wpłat
//                agreement.setCurrentBalance(agreement.getCurrentBalance().subtract(amount));
//                agreement.setCurrentBalanceLeft(amount);
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
