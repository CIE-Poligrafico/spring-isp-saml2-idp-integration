package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXValidationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class SecurityProviderConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;
    
//    @Bean PKIXTrustEvaluator CertPathPKIXTrustEvaluator() {
//    	org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator certPathPKIXTrustEvaluator=
//    	
//    			new org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator();
//    	CertPathPKIXValidationOptions 
//    	certPathPKIXValidationOptions=
//    			new CertPathPKIXValidationOptions();
//    	//certPathPKIXValidationOptions.setForceRevocationEnabled(true);
//    //	certPathPKIXValidationOptions.setRevocationEnabled(true);
//    	certPathPKIXTrustEvaluator.setPKIXValidationOptions(certPathPKIXValidationOptions);
//    	certPathPKIXValidationOptions.setAnyPolicyInhibit(true);
//    	return certPathPKIXTrustEvaluator;
//    }
//    @Value("${springfox.documentation.swagger.v2.path}")
//    private String docPath;
    private final String[] AUTH_WHITELIST = {
    		"/free/**",
//    		"/"+docPath+"*",
//    		"/"+docPath+"*/**",
    		"/docs/**",
    		"/v2/**",
    		"/webjars/**",
    		"/health/**",
    		"/info/**",
    		"/actuator/**",
    		"/swagger*/**",
    		"/configuration/**",
    		"/swagger*",
    		"/swagger*/**",
    		"/h2-console/**",
    		"/swagger-resources/**",
    		"/v3/api-docs*/**",
    		"/v2/api-docs",
            //"/configuration/ui",
            //"/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            //"/webjars/**"
    };
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver =
                new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrationRepository);

      
        Saml2MetadataFilter filter = new Saml2MetadataFilter(
                relyingPartyRegistrationResolver,
                new OpenSamlMetadataResolver());
        
        http . csrf().disable().
        authorizeRequests()
        .antMatchers(AUTH_WHITELIST).permitAll().
        and()  
        . csrf().disable()
        .saml2Login(withDefaults())
            .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class)
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/**").authenticated();
        http.headers().frameOptions().disable();    
    } 
    //private ExtendedMetadata ExtendedMetadata;

}
