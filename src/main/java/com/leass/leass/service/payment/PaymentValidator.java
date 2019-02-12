package com.leass.leass.service.payment;

import com.leass.leass.model.Payment;

import java.util.List;

public interface PaymentValidator {

    List<String> validate(Payment payment);
}
