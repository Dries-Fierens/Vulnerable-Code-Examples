FROM java:8
COPY ./pom.xml ./pom.xml
COPY ./src ./src
EXPOSE 8080
ADD target/Vulnerable-Code-Examples-1.0-SNAPSHOT.jar Vulnerable-Code-Examples-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Vulnerable-Code-Examples-1.0-SNAPSHOT.jar"]