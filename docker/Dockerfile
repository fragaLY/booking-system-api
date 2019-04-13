FROM java:8-alpine
VOLUME /tmp

ENV JAVA_XMX=512m
ENV JMX_HOST=localhost

ENV DEBUG_PORT=5080
ENV JMX_PORT=38080

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

EXPOSE ${DEBUG_PORT} ${JMX_PORT} 8080

ENTRYPOINT ["sh", "-c", "java -Xmx${JAVA_XMX} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/tmp/heapdump.bin -Xdebug -Xrunjdwp:server=y,transport=dt_socket,suspend=n,address=${DEBUG_PORT} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=${JMX_PORT} -Dcom.sun.management.jmxremote.rmi.port=${JMX_PORT} -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=${JMX_HOST} -jar /app.jar"]
