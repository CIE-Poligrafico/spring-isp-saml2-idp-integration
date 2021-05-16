package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.opensaml.security.x509.PKIXTrustEngine;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator;
import org.opensaml.xmlsec.signature.support.impl.BaseSignatureTrustEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SamlServiceProviderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext=	SpringApplication.run(SamlServiceProviderApplication.class, args);
//BaseSignatureTrustEngine certPathPKIXTrustEvaluator=
//		configurableApplicationContext.getBean(BaseSignatureTrustEngine.class);
		
	}

}
