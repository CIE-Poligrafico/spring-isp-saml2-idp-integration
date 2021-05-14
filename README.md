# spring-isp-saml2-idp-integration

1)Lanciare il container Idp con Isp=
http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs

mediante il comando:

docker run --name=testsamlidp -p 8080:8080 -p 8443:8443 -e SIMPLESAMLPHP_SP_ENTITY_ID=http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs -e SIMPLESAMLPHP_SP_ASSERTION_CONSUMER_SERVICE=http://host.docker.internal:8081/interni-sp/login/saml2/sso/internilogs -d kristophjunge/test-saml-idp

Idp gira su:
http://localhost:8080/simplesaml/module.php/core/frontpage_welcome.php

Users caricati 2:

 
 usr=user1 
 pass=user1pass 
 
 
 usr=user2
 pass=user2pass
 
 2)Connettersi alla index dello Spring Isp all'indirizzo:
 
 http://host.docker.internal:8081/interni-sp/cielogs
 
 (se serve cambiare i dns affinchè host.docker.internal punti a 127.0.0.1 )
 
 3)Inserire una delle due credenziali di default precaricate
 
 4)L'applicativo ritorna all'Idp con errore:
 Invalid signature for SAML Response [_3190e86058c80e25b042847f410b268e533f227bbf]
 
 Dai logs ISP si evince che la asserzione dell'Idp viene correttamente validata(Successfully verified signature using KeyInfo-derived credential) ma il problema è il trust(Failed to establish trust of KeyInfo-derived credential):
 
 Assertion:
 
 
 <saml:Assertion
    xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" ID="_e047526db37ed04568e3cce2d2b4765b5dac0ecc55" IssueInstant="2021-05-14T13:50:25Z" Version="2.0">
    <saml:Issuer>http://localhost:8080/simplesaml/saml2/idp/metadata.php</saml:Issuer>
    <saml:Subject>
        <saml:NameID Format="urn:oasis:names:tc:SAML:2.0:nameid-format:transient" SPNameQualifier="http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs">_3830144120c28908504da5ee661edb27f4e1c0d362</saml:NameID>
        <saml:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
            <saml:SubjectConfirmationData InResponseTo="ARQ88646b1-38b1-4fcd-ba28-8e554c423c16" NotOnOrAfter="2021-05-14T13:55:25Z" Recipient="http://host.docker.internal:8081/interni-sp/login/saml2/sso/internilogs"></saml:SubjectConfirmationData>
        </saml:SubjectConfirmation>
    </saml:Subject>
    <saml:Conditions NotBefore="2021-05-14T13:49:55Z" NotOnOrAfter="2021-05-14T13:55:25Z">
        <saml:AudienceRestriction>
            <saml:Audience>http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs</saml:Audience>
        </saml:AudienceRestriction>
    </saml:Conditions>
    <saml:AuthnStatement AuthnInstant="2021-05-14T13:50:25Z" SessionIndex="_f14408e3ef89eceb968030a319725d0be76f104cb1" SessionNotOnOrAfter="2021-05-14T21:50:25Z">
        <saml:AuthnContext>
            <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:Password</saml:AuthnContextClassRef>
        </saml:AuthnContext>
    </saml:AuthnStatement>
    <saml:AttributeStatement>
        <saml:Attribute Name="uid" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
            <saml:AttributeValue
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">1
            </saml:AttributeValue>
        </saml:Attribute>
        <saml:Attribute Name="eduPersonAffiliation" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
            <saml:AttributeValue
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">group1
            </saml:AttributeValue>
        </saml:Attribute>
        <saml:Attribute Name="email" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
            <saml:AttributeValue
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">user1@example.com
            </saml:AttributeValue>
        </saml:Attribute>
    </saml:AttributeStatement>
</saml:Assertion>
 
 
 Logs:
 
 
  
o.a.xml.security.signature.Reference     : Verification successful for URI "#_e047526db37ed04568e3cce2d2b4765b5dac0ecc55"
o.a.xml.security.signature.Manifest      : The Reference has Type 
 
SantuarioSignatureValidationProviderImpl : Signature validated with key from supplied credential

o.o.x.s.s.impl.BaseSignatureTrustEngine  : Signature validation using candidate credential was successful

o.o.x.s.s.impl.BaseSignatureTrustEngine  : Successfully verified signature using KeyInfo-derived credential

o.o.x.s.s.impl.BaseSignatureTrustEngine  : Attempting to establish trust of KeyInfo-derived credential

o.o.x.s.s.impl.BaseSignatureTrustEngine  : Failed to establish trust of KeyInfo-derived credential

o.o.x.s.s.impl.BaseSignatureTrustEngine  : Failed to verify signature and/or establish trust using any KeyInfo-derived 
credentials
x.s.s.i.ExplicitKeySignatureTrustEngine : Attempting to verify signature using trusted credentials

x.s.s.i.ExplicitKeySignatureTrustEngine : Failed to verify signature using either KeyInfo-derived or directly trusted credentials
o.o.s.s.a.SAML20AssertionValidator       : Signature of Assertion '_e047526db37ed04568e3cce2d2b4765b5dac0ecc55' from Issuer 'http://localhost:8080/simplesaml/saml2/idp/metadata.php' was not valid

Il certificato dell'Idp è quello che nel container trovasi in: /var/www/simplesamlphp/cert/server.crt
 
 
 

