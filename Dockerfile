FROM java:8
EXPOSE 8080
ADD target/Vulnerable-Code-Examples-1.0-SNAPSHOT.jar Vulnerable-Code-Examples-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Vulnerable-Code-Examples-1.0-SNAPSHOT.jar"]