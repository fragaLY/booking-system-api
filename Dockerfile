FROM java:8-alpine
VOLUME /tmp
ENV JAVA_XMX=512m
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Xmx${JAVA_XMX} -jar /app.jar"]
