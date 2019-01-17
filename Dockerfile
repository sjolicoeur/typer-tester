FROM openjdk:8-alpine

COPY target/uberjar/typer-tester.jar /typer-tester/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/typer-tester/app.jar"]
