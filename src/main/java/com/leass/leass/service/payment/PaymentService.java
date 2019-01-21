package com.leass.leass.service.payment;

import com.leass.leass.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Payment load(Long id);

    List<Payment> findAll();

    void save(Payment payment);

    void passPayment(Payment payment, List<Long> invoiceId);
}
