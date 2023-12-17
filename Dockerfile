FROM openjdk:17
ADD target/tms-dockerfile.jar tms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/tms.jar"]