package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.opensaml.security.x509.PKIXTrustEngine;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SamlServiceProviderApplication {

	public static void main(String[] args) {
		 System.out.println(System.getenv("HOSTNAME"));
		 System.setProperty("HOSTNAME", "ciao");
		// System.getenv().put("HOSTNAME", "ciao");
		 //System.out.println(System.getenv("HOSTNAME"));
		InetAddress myHost=null; 
		try {
			 myHost = InetAddress.getLocalHost();
			 System.out.println(myHost.getHostName());
		 } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
ConfigurableApplicationContext configurableApplicationContext=SpringApplication.run(SamlServiceProviderApplication.class, args);
//		PKIXTrustEngine certPathPKIXTrustEvaluator=
//		configurableApplicationContext.getBean(PKIXTrustEngine.class);
//		try {
//			myHost = InetAddress.getLocalHost();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(myHost.getHostName());
	}

}
