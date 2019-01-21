package com.leass.leass.service.agreement;

import com.leass.leass.model.Agreement;

import java.util.List;

public interface AgreementService {

    void save(Agreement agreement);

    List<Agreement> findAll();

    List<Agreement> findByQuery(AgreementCriteria agreementCriteria);

    void saveDto(AgreementDto agreementDto);

    AgreementDto getAgreementById(Long id);

    Agreement getOne(Long id);

    AgreementDto createAgreement(AgreementDto agreementDto, String value);
}
