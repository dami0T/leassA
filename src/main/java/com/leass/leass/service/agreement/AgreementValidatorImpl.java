package com.leass.leass.service.agreement;

import com.leass.leass.model.Agreement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgreementValidatorImpl implements AgreementValidator{
    @Override
    public List<String> validate(AgreementDto agreement) {
        List<String> errors = new ArrayList<>();
        if (agreement.getAgreementNumber().isEmpty()){
            errors.add("Nie podano numeru umowy");
        }
        if (agreement.getClientId() == null){
            errors.add("Należy wprowadzić klienta");
        }
        if (agreement.getAmountOfInstallments() == null){
            errors.add("Należy podać kwotę raty");
        }
        if (agreement.getMonth() == null){
            errors.add("Należy podać okres wynajmu");
        }
        if (agreement.getProductId() == null){
            errors.add("Nie wybrano produktu");
        }
        return errors;
    }
}
