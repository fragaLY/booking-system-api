FROM gradle:jdk8-alpine as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM java:8-alpine
ENV JAVA_XMX=1g
COPY --from=builder /home/gradle/src/build/libs/booking-system-1.0.0.jar /opt/
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Xmx${JAVA_XMX} -jar /opt/booking-system-1.0.0.jar"]