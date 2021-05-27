package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;

@Configuration
//@EnableWebSecurity
public class SecurityProviderConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(
				relyingPartyRegistrationRepository);
		var metadataFilter = new Saml2MetadataFilter(relyingPartyRegistrationResolver,
				new OpenSamlMetadataResolver());

		http

				.authorizeRequests(authorize -> authorize.anyRequest().authenticated())

				.saml2Login(saml2 -> saml2.relyingPartyRegistrationRepository(relyingPartyRegistrationRepository))
				.addFilterBefore(metadataFilter, Saml2WebSsoAuthenticationFilter.class);

	}
     @Override
	public void configure(WebSecurity webSecurity) throws Exception {

		webSecurity.ignoring()

				.antMatchers(AUTH_WHITELIST);

	}

	private final static String[] AUTH_WHITELIST = { "/free/**",
//    		"/"+docPath+"*",
//    		"/"+docPath+"*/**",
			"/docs/**", "/v2/**", "/webjars/**", "/health/**", "/info/**", "/actuator/**", "/swagger*/**",
			"/configuration/**", "/swagger*", "/swagger*/**", "/h2-console/**", "/swagger-resources/**",
			"/v3/api-docs*/**", "/v2/api-docs",
			// "/configuration/ui",
			// "/swagger-resources/**",
			"/configuration/security", "/swagger-ui.html"
			// ,
			// "/mylogout*"
			// ,"/saml2*/*"//TODO Test
			// "/webjars/**"
	};
}
