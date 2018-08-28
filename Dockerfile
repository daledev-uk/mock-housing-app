FROM openjdk:8-jdk-alpine

MAINTAINER daleellis1983@gmail.com

VOLUME /tmp

COPY docker-build-resources/UnlimitedJCEPolicyJDK8/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/

ADD target/*.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]