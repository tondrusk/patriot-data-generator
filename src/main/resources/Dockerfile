FROM ubuntu:latest

RUN apt-get update -y && \
    apt-get install default-jre -y

ADD ./target/generator-1.0-SNAPSHOT.jar generator.jar

CMD java -jar generator.jar
