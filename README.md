# spring-isp-saml2-idp-integration

In locale aggiungere la riga nei dns

127.0.0.1 host.docker.internal  e rimuovere tutti i riferimenti host.docker.internal affinchè tomcat possa fare il bind

quindi: ipconfig /flushdns
Profilo docker(lancio sia Isp che Idp in locale):
-Lanciare il container Idp con Isp=
http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs



mediante il comando:

docker run --name=testsamlidp -p 8080:8080 -p 8443:8443 -e SIMPLESAMLPHP_SP_ENTITY_ID=http://host.docker.internal:8081/interni-sp/saml2/service-provider-metadata/internilogs -e SIMPLESAMLPHP_SP_ASSERTION_CONSUMER_SERVICE=http://host.docker.internal:8081/interni-sp/login/saml2/sso/internilogs -d kristophjunge/test-saml-idp

Idp gira su:
http://localhost:8080/simplesaml/module.php/core/frontpage_welcome.php

Lanciare l'Isp in locale:
mvn package
mvn spring-boot:run -Dspring-boot.run.profiles=docker(Oppure build e run del DockerFile dopo mvn package)


 2)Connettersi alla index dello Spring Isp all'indirizzo:
 
 http://host.docker.internal:8081/interni-sp/cielogs
 
 ( serve cambiare i dns affinchè host.docker.internal punti a 127.0.0.1 )
 
 3)Inserire una delle due credenziali di default precaricate
 
 
Per il profilo default invece(Isp ed Idp in remoto) bisogna lanciare l'Idp sulla macchina 10.128.0.10

mediante il comando docker:

sudo docker run --name=testsamlidp -p 8080:8080 -p 8443:8443 -e SIMPLESAMLPHP_SP_ENTITY_ID=http://10.128.0.11:8080/samlisp/saml2/service-provider-metadata/internilogs -e SIMPLESAMLPHP_SP_ASSERTION_CONSUMER_SERVICE=http://10.128.0.11:8080/samlisp/login/saml2/sso/internilogs -d kristophjunge/test-saml-idp

ed ovviamente sulla macchina dell'ISP  10.128.0.10

bisogna lanciare la shell comando:

/home/azureuser/samlisp/spring-isp-saml2-idp-integration/server.sh


Users caricati 2:

 
 usr=user1 
 pass=user1pass 
 
 
 usr=user2
 pass=user2pass
 
Per docker profile la documentazione swagger si trova agli indirizzi:

http://host.docker.internal:8081/interni-sp/swagger-ui/

 http://host.docker.internal:8081/interni-sp/actuator

http://10.128.0.11:8080/swagger-ui/
 
 docker compose up nella cartella microservizi C:\Users\Formazione\workspace-microservizi\elk-stack2\docker-elkstack ricordandosi di dare autorizzazione alle cartelle

Poi 

curl -XGET http://localhost:9200/_license
curl -X POST "localhost:9200/_license/start_trial?acknowledge=true&pretty"
curl -XGET http://localhost:9200/_license

..per connettersi da Dbeaver
 

