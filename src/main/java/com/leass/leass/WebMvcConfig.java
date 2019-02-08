package com.leass.leass;

import com.leass.leass.repository.AgreementSpecification;
import com.leass.leass.repository.InvoiceSpecification;
import com.leass.leass.repository.ProductSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AgreementSpecification agreementSpecification(){return new AgreementSpecification();}

    @Bean
    public InvoiceSpecification invoiceSpecification(){return new InvoiceSpecification();}

    @Bean
    public ProductSpecification productSpecification(){return new ProductSpecification();}
}
