FROM java:8-alpine
VOLUME /tmp
ENV JAVA_XMX=512m
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE 8080
HEALTHCHECK --interval=10s --timeout=3s CMD curl --fail http://localhost:8080 || exit 1
ENTRYPOINT ["sh", "-c", "java -Xmx${JAVA_XMX} -jar /app.jar"]
