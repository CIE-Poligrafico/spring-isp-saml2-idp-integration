package com.example.demo.controllers;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;








@Controller
public class RestController {

	  @Autowired
	    private MessageSource messageSource;
	@Autowired
	  @Qualifier("localeMessageSourceAccessor")
	   private MessageSourceAccessor messageSourceAccessor;
	  
	  @Autowired
	  @Qualifier("frMessageSourceAccessor")
	   private MessageSourceAccessor frMessageSourceAccessor;
	
	 @GetMapping("/logs")
	 public String logs(){
		 return "index";
	 }

	 @GetMapping({"/free/helloSys"})
		public ResponseEntity<String> helloSys() {
			 
			 String hello=messageSourceAccessor.getMessage("helloworld.message");
			return new ResponseEntity<String>(hello,HttpStatus.OK);
		}
	 @GetMapping({"/free/helloFrSys"})
		public ResponseEntity<String> helloFrSys() {
			 
			 String hello=frMessageSourceAccessor.getMessage("helloworld.message");
			return new ResponseEntity<String>(hello,HttpStatus.OK);
		}
	 @GetMapping({"/free/helloClient"})
		public ResponseEntity<String> helloClient(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
			 
			 String hello=messageSource.getMessage("helloworld.message", null, locale);  
			return new ResponseEntity<String>(hello,HttpStatus.OK);
		}
	 @GetMapping({"/free/helloClientPar"})  
		public ResponseEntity<String> helloClientPar(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required=false) Locale locale) {
			 
			 String hello=messageSource.getMessage("book.warpeace.first",new Object[]{1,new Date()} , locale);  
			return new ResponseEntity<String>(hello,HttpStatus.OK);
		}

}
