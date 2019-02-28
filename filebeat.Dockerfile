FROM docker.elastic.co/beats/filebeat:6.6.0
COPY src/main/resources/filebeat.yml filebeat.yml /usr/share/filebeat/filebeat.yml
VOLUME /logs/
