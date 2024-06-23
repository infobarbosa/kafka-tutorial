# Pull base image.
FROM ubuntu:latest

RUN  \
# Update
apt-get update -y && \
# Install Java
apt install -y openjdk-17-jre

ADD ./target/kafka-consumer-1.1.jar kafka-consumer.jar

CMD java -jar kafka-consumer.jar
