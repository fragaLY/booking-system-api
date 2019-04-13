FROM sebp/elk
VOLUME /tmp
ARG LOGSTASH_INPUT_CONFIG
ADD ${LOGSTASH_INPUT_CONFIG} /etc/logstash/conf.d/logstash-tcp-input.conf