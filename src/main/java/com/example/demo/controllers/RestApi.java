package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.LogCie;








@RestController
@RequestMapping("logsapi")
public class RestApi {

	  @Autowired
	    private MessageSource messageSource;
	@Autowired
	  @Qualifier("localeMessageSourceAccessor")
	   private MessageSourceAccessor messageSourceAccessor;
	  
	  @Autowired
	  @Qualifier("frMessageSourceAccessor")
	   private MessageSourceAccessor frMessageSourceAccessor;
	
private static final String HELLO_WORLD_MESSAGE="helloworld.message";

	 @GetMapping({"/free/helloSys"})
		public ResponseEntity<String> helloSys() {
			 
			 String hello=messageSourceAccessor.getMessage(HELLO_WORLD_MESSAGE);
			return new ResponseEntity<>(hello,HttpStatus.OK);
		}
	 @GetMapping({"/free/helloFrSys"})
		public ResponseEntity<String> helloFrSys() {
			 
			 String hello=frMessageSourceAccessor.getMessage(HELLO_WORLD_MESSAGE);
			return new ResponseEntity<>(hello,HttpStatus.OK);
		}
	 @GetMapping({"/free/helloClient"})
		public ResponseEntity<String> helloClient(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
			 
			 String hello=messageSource.getMessage(HELLO_WORLD_MESSAGE, null, locale);  
			return new ResponseEntity<>(hello,HttpStatus.OK);
		}
	 @GetMapping({"/free/helloClientPar"})  
		public ResponseEntity<String> helloClientPar(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required=false) Locale locale) {
			 
			 String hello=messageSource.getMessage("book.warpeace.first",new Object[]{1,new Date()} , locale);  
			return new ResponseEntity<>(hello,HttpStatus.OK);
		}
	 
	
	 @GetMapping({"/cielogs"})  
		public ResponseEntity<List<LogCie>> cielogs(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal,@RequestParam(value = "cf", required=true)String cf) {
		
		 
	         String user=(String)principal.getAttribute("email").get(0);	 
			 LogCie log1=LogCie.builder().username(user).cieSerial("serial1").ipAddress("198.8.7.7").userAgent("Mozilla/5.0").
					 accessTime(new Date()).build();
			 LogCie log2=LogCie.builder().username(user).cieSerial("serial3").ipAddress("198.8.28.7").userAgent("Mozilla/5.0").
					 accessTime(new Date()).build();
			 List<LogCie> logs=new ArrayList<>();
			 logs.add(log1);
			 logs.add(log2);
			return new ResponseEntity<>(logs,HttpStatus.OK);
		}

}
