package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;

public class SingleRelyingPartyRegistrationResolver implements Converter<HttpServletRequest, RelyingPartyRegistration> {

    @Autowired
    RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;
    @Override
    public RelyingPartyRegistration convert(HttpServletRequest request) {
        return relyingPartyRegistrationRepository.findByRegistrationId("internilogs");
    }

}
