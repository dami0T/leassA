package com.leass.leass.service.payment;

import com.leass.leass.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentValidatorImpl implements PaymentValidator{

    @Override
    public List<String> validate(Payment payment) {
        return null;
    }
}
