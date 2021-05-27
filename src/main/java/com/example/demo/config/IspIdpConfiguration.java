package com.example.demo.config;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;

@Configuration
public class IspIdpConfiguration {

	private Saml2X509Credential idpCertificate() throws IOException, CertificateException {

		try (var is = idpCertificateRes.getInputStream()) {
			X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(is);
			return Saml2X509Credential.verification(certificate);
		}
	}

	private Saml2X509Credential ispCertificate() throws IOException, CertificateException {

		try (var is = ispCertificateRes.getInputStream()) {
			X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(is);
			return Saml2X509Credential.verification(certificate);
		}
	}

	@Value("classpath:credentials/public.crt")
	private Resource ispCertificateRes;
	@Value("classpath:credentials/dockeridpcert1.crt")
	private Resource idpCertificateRes;
	@Value("classpath:credentials/private.key")
	private Resource ispPrivateKeyRes;

	private Saml2X509Credential ispKeyPair() throws IOException, CertificateException {
		X509Certificate ispCertificate = ispCertificate().getCertificate();

		try (var is = ispPrivateKeyRes.getInputStream()) {
			RSAPrivateKey ispRsa = RsaKeyConverters.pkcs8().convert(is);
			return Saml2X509Credential.signing(ispRsa, ispCertificate);
		}
	}

	@Profile("docker")
	@Bean
	public RelyingPartyRegistrationRepository relyingPartyRegistration1() throws CertificateException, IOException {
		var assertingPartyEntityId = "http://localhost:8080/simplesaml/saml2/idp/metadata.php";
		var singleSignOnServiceLocation = "http://localhost:8080/simplesaml/saml2/idp/SSOService.php";
		var assertionConsumerServiceLocation = "http://host.docker.internal:8081/interni-sp/login/saml2/sso/internilogs";
		var assertingPartyVerificationCredential = idpCertificate();
		var relyingPartyEntityId = "http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs";
		var registrationId = "internilogs";
		Saml2X509Credential relyingPartySigningCredential = ispKeyPair();

		RelyingPartyRegistration registration = RelyingPartyRegistration.withRegistrationId(registrationId)
				.entityId(relyingPartyEntityId)// futile
				.assertionConsumerServiceLocation(assertionConsumerServiceLocation)// futile
				.signingX509Credentials(c -> c.add(relyingPartySigningCredential))
				.assertingPartyDetails(details -> details.entityId(assertingPartyEntityId)
						.singleSignOnServiceLocation(singleSignOnServiceLocation)
						.verificationX509Credentials(c -> c.add(assertingPartyVerificationCredential)))
				.build();

		return new InMemoryRelyingPartyRegistrationRepository(registration);

	}
}