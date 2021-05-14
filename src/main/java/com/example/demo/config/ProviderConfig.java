package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXValidationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
public class ProviderConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;
    
    @Bean PKIXTrustEvaluator CertPathPKIXTrustEvaluator() {
    	org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator certPathPKIXTrustEvaluator=
    	
    			new org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator();
    	CertPathPKIXValidationOptions 
    	certPathPKIXValidationOptions=
    			new CertPathPKIXValidationOptions();
    	//certPathPKIXValidationOptions.setForceRevocationEnabled(true);
    //	certPathPKIXValidationOptions.setRevocationEnabled(true);
    	certPathPKIXTrustEvaluator.setPKIXValidationOptions(certPathPKIXValidationOptions);
    	certPathPKIXValidationOptions.setAnyPolicyInhibit(true);
    	return certPathPKIXTrustEvaluator;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver =
                new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrationRepository);

      
        Saml2MetadataFilter filter = new Saml2MetadataFilter(
                relyingPartyRegistrationResolver,
                new OpenSamlMetadataResolver());
        
        http
            .saml2Login(withDefaults())
            .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class)
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/**").authenticated();
    } 
    //private ExtendedMetadata ExtendedMetadata;

}
