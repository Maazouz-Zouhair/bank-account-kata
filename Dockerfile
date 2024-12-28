# Build stage
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests=true

# Runtime stage 
FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
# Copy the jar file into the container
COPY --from=build /build/target/bankaccount-*.jar /app/bankaccount-${APP_VERSION}.jar

# Expose the application port
EXPOSE 8080

ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${PROFILE}

ENV EMAIL_HOSTNAME=missing_hostname
ENV EMAIL_USERNAME=missing_username
ENV EMAIL_PASSWORD=missing_password

# Command to run the application
CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} bankaccount-${JAR_VERSION}.jar
