package com.leass.leass.service.payment;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Invoice;
import com.leass.leass.model.Payment;
import com.leass.leass.repository.PaymentRepository;
import com.leass.leass.service.agreement.AgreementService;
import com.leass.leass.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AgreementService agreementService;

    @Autowired
    InvoiceService invoiceService;

    @Override
    public Payment load(Long id) {
        Payment payment = paymentRepository.getOne(id);
        return payment;
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(paymentRepository.findAll());
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void passPayment(Payment payment, List<Long> invoiceId) {
        BigDecimal baseValue = payment.getAmount();
        BigDecimal toPass = new BigDecimal(BigInteger.ZERO);
        Agreement agreement = agreementService.getOne(payment.getAgreement().getId());
        for (Long aLong : invoiceId) {
            Invoice invoice = invoiceService.getOne(aLong);
            BigDecimal invoiceValue = invoice.getFinalGrossValue();
            if (baseValue.compareTo(invoiceValue) > 0) {
                BigDecimal excessAmount = baseValue.subtract(invoiceValue);
                toPass = baseValue.subtract(excessAmount);
                Payment payment1 = new Payment();
                payment1.setAmount(excessAmount);
                payment1.setAgreement(agreement);
                payment1.setTransactionDescription(payment.getTransactionDescription());
                payment1.setDateOfPayment(new Date());
                save(payment1);
            } else {
                toPass = invoiceValue.subtract(baseValue);
            }
            invoice.setOperationBalance(invoice.getOperationBalance().subtract(toPass));
            if (invoice.getPaidValue() != null) {
                invoice.setPaidValue(invoice.getPaidValue().add(toPass));
            }
            payment.setAmount(new BigDecimal(BigInteger.ZERO));
            invoiceService.save(invoice);
            agreement.setCurrentBalance(agreement.getCurrentBalance().subtract(toPass));
            agreement.setCurrentBalanceLeft(agreement.getCurrentBalanceLeft().add(toPass));
            agreementService.save(agreement);
        }


    }
}
