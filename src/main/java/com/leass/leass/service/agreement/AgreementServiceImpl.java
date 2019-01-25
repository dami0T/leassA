package com.leass.leass.service.agreement;

import com.leass.leass.model.Agreement;
import com.leass.leass.model.Client;
import com.leass.leass.model.Product;
import com.leass.leass.repository.AgreementRepository;
import com.leass.leass.repository.AgreementSpecification;
import com.leass.leass.repository.ClientRepository;
import com.leass.leass.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AgreementSpecification agreementSpecification;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void save(Agreement agreement) {
        agreementRepository.save(agreement);
    }

    private AgreementDto convertToAgreementDto(Agreement agreement) {
        AgreementDto agreementDto = modelMapper.map(agreement, AgreementDto.class);
        if (agreement.getClient() != null) {
            agreementDto.setClientName(agreement.getClient().getShortName());
        }
        if (agreement.getFirsInvoiceAmount() != null) {
            agreementDto.setFirstInvoiceAmount(agreement.getFirsInvoiceAmount());
        }
        return agreementDto;
    }

    private Agreement convertToAgreementEntity(AgreementDto agreementDto) {
        Agreement agreement = modelMapper.map(agreementDto, Agreement.class);
        return agreement;
    }

    @Override
    public List<Agreement> findAll() {
        return new ArrayList<>(agreementRepository.findAll());
    }

    @Override
    public List<Agreement> findByQuery(AgreementCriteria agreementCriteria) {
        return agreementRepository.findAll(agreementSpecification.agreementByQuery(agreementCriteria));
    }


    @Override
    public void saveDto(AgreementDto agreementDto) {
        Agreement agreement = convertToAgreementEntity(agreementDto);

        if (agreementDto.getClientId() != null) {
            Client client = clientRepository.getOne(agreementDto.getClientId());
            agreement.setClient(client);
        }

        if (agreementDto.getProductId() != null) {
            Product product = productRepository.getOne(agreementDto.getProductId());
            agreement.setProduct(product);
        }

        save(agreement);
    }

    @Override
    public AgreementDto getAgreementById(Long id) {
        Optional<Agreement> agreement = agreementRepository.findById(id);
        return convertToAgreementDto(agreement.get());
    }

    @Override
    public Agreement getOne(Long id) {
        Agreement agreement = agreementRepository.getOne(id);
        return agreement;
    }

    @Override
    public AgreementDto createAgreement(AgreementDto agreementDto, String value) {
        BigDecimal amount = new BigDecimal(value).multiply(new BigDecimal(agreementDto.getMonth()));
        Agreement agreement = convertToAgreementEntity(agreementDto);
        agreement.setLiabilities(amount);
        agreement.setCurrentBalance(amount);
        agreement.setCurrentBalanceLeft(new BigDecimal(BigInteger.ZERO));
        agreement.setFirsInvoiceAmount(agreementDto.getFirstInvoiceAmount());
        agreement.setCreateDate(new Date());
        agreement.setMonthLeft(agreementDto.getMonth());
        save(agreement);
        agreementDto = convertToAgreementDto(agreement);
        return agreementDto;
    }
}
