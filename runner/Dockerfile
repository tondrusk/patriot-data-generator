FROM openjdk:11.0-jre-slim

ADD target/device-runner.jar /var/

EXPOSE 5683/udp
CMD ["java", "-jar", "/var/device-runner.jar"]