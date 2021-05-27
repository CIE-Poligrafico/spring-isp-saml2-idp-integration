package com.example.demo.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppConfig {
	
	@Autowired
    private MessageSource messageSource;
	@Bean
	MessageSourceAccessor localeMessageSourceAccessor() {
	return new MessageSourceAccessor(messageSource, Locale.getDefault());
	}
	@Bean("frMessageSourceAccessor")
	MessageSourceAccessor frLocaleMessageSourceAccessor() {
	return new MessageSourceAccessor(messageSource, Locale.FRANCE);
	}


}
