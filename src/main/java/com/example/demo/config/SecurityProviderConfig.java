package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;



@Configuration
//@EnableWebSecurity
public class SecurityProviderConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;
    

   
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver =
    	        new DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrationRepository);
    	Saml2MetadataFilter metadataFilter = new Saml2MetadataFilter(
    	        relyingPartyRegistrationResolver,
    	        new OpenSamlMetadataResolver());   
    	
    	http 

        .authorizeRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
        
        //.saml2Login(withDefaults())
        .saml2Login(saml2 -> saml2
        .relyingPartyRegistrationRepository(relyingPartyRegistrationRepository))
        .addFilterBefore(metadataFilter, Saml2WebSsoAuthenticationFilter.class)
//        .logout(logout -> logout
//                .logoutSuccessHandler(myCustomSuccessHandler())
//                .logoutRequestMatcher(myRequestMatcher())
//            )
       
             
//            .logout()
//            .invalidateHttpSession(true)
//            .deleteCookies("PHPSESSIDIDP","JSESSIONID","SimpleSAMLAuthTokenIdp")
//            .clearAuthentication(true)
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login?logout")
//    
//            .permitAll()
            
           // .logoutUrl("/mylogout")
            
           // .addLogoutHandler(new ProperCookieClearingLogoutHandler("PHPSESSIDIDP","JSESSIONID","SimpleSAMLAuthTokenIdp"))
            
            
            ;
    
    } 
//    static final class ProperCookieClearingLogoutHandler implements LogoutHandler {
//        private final List<String> cookiesToClear;
//
//        public ProperCookieClearingLogoutHandler(String... cookiesToClear) {
//            Assert.notNull(cookiesToClear, "List of cookies cannot be null");
//            this.cookiesToClear = Arrays.asList(cookiesToClear);
//        }
//
//        public void logout(HttpServletRequest request, HttpServletResponse response,
//                Authentication authentication) {
//            for (String cookieName : cookiesToClear) {
//                Cookie cookie = new Cookie(cookieName, null);
//                String cookiePath = request.getContextPath() + "/";
//                if (!StringUtils.hasLength(cookiePath)) {
//                    cookiePath = "/";
//                }
//                cookie.setPath(cookiePath);
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//    }
//    
    @Override
    
        public void configure(WebSecurity webSecurity) throws Exception {
    
    	webSecurity. 
    	ignoring()
    
                    .antMatchers(AUTH_WHITELIST)
                    //.clearAuthentication(true)
                    //.logoutUrl("/mylogout")
                    //.invalidateHttpSession(true)
                    //.deleteCookies("PHPSESSIDIDP","JSESSIONID","SimpleSAMLAuthTokenIdp")
                    ;
    
        }
    
    private final static String[] AUTH_WHITELIST = {
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
            "/swagger-ui.html"
            //,
            //"/mylogout*"
            //,"/saml2*/*"//TODO Test
            //"/webjars/**"
    };
}
