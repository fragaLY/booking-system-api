FROM java:8-alpine
VOLUME /tmp
ENV JAVA_XMX=512m
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Xmx${JAVA_XMX} -jar /app.jar"]