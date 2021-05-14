FROM centos
RUN yum install -y java-11-openjdk-devel
VOLUME /tmp
ADD target/saml-service-provider-0.0.1-SNAPSHOT.jar ispapp.jar
RUN sh -c 'touch /myapp.jar'
ENTRYPOINT ["java","-Djava.security.egd= file:/dev/ . /urandom","-Dspring.profiles.active=default","-jar","/ispapp.jar"]