package com.example.demo.config;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

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
	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
	    ApplicationContext applicationContext = event.getApplicationContext();
	    RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
	        .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
	    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
	        .getHandlerMethods();
	    map.forEach((key, value) -> log.info("**ENDPOINT EXPOSED KEY_{}****VALUE_ {}*****", key, value));
	}
	

}
