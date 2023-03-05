FROM maven:3.6.3-jdk-11-slim
ENV APP_HOME=/usr/
WORKDIR $APP_HOME

COPY src/main/resources/application.yml application.yml
COPY target/*.jar app.jar
CMD ["java","-jar","app.jar"]