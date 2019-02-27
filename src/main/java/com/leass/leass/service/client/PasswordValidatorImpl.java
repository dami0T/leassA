package com.leass.leass.service.client;

import com.leass.leass.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordValidatorImpl implements PasswordValidator{



    @Override
    public List<String> validate(User user) {
        List<String> errors = new ArrayList<>();
        if (!validateCaps(user.getNewPassword())){
            errors.add("Hasło powinno zawierać wielkie i małe litery");
        }
        if (!validateLength(user.getNewPassword())){
            errors.add("Minimalno długość hasła to 8 znaków");
        }
        if (!validateSpecialChar(user.getNewPassword())){
            errors.add("Hasło powinno zawierać jeden znak specjalny");
        }
        if (!validateNumber(user.getNewPassword())){
            errors.add("Hasło powinno zawierać cyfrę");
        }
//        if (validateMatchPasswordAndRepassword(user.getNewPassword(), user.getRepassword())){
//            errors.add("Powtórzone hasło musi być takie same");
//        }
        return errors;
    }

    public Boolean validateCaps(String password) {

        return !(password.equals(password.toLowerCase()) || (password.equals(password.toUpperCase())));

    }


    public Boolean validateLength(String password) {
        Integer minPasswordLength = 8;
        return (password != null && StringUtils.isNotBlank(password) && password.length() >= minPasswordLength);
    }

    public Boolean validateSpecialChar(String password) {

        Pattern p = Pattern.compile("[A-Za-z0-9]*");
        Matcher m = p.matcher(password);
        return !m.matches();
    }


    public Boolean validateNumber(String password) {

        Pattern p = Pattern.compile(".*[0-9].*");
        Matcher m = p.matcher(password);
        return m.matches();
    }


    public Boolean validateMatchLoginAndPassword(String login, String password) {
        return (password != null && !password.equals(login));
    }


    public Boolean validateMatchPasswordAndRepassword(String password, String repass) {
        return (password != null && repass != null && password.equals(repass));
    }


    public Boolean validateOldPassword(String password, String oldPassword) {
        return (password.equals(DigestUtils.sha256Hex(oldPassword)));
    }
}
