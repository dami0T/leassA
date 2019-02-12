package com.leass.leass.service.agreement;

import com.leass.leass.model.Agreement;

import java.util.List;

public interface AgreementValidator {

    List<String> validate(AgreementDto agreement);
}
