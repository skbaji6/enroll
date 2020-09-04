FROM maven:3-jdk-8
 
ADD . /enroll
WORKDIR /enroll
 
# Just echo so we can see, if everything is there :)
RUN ls -l
 
# Run Maven build
RUN mvn clean install
 
 
# 2. Just using the build artifact and then removing the build-container
FROM openjdk:8-jdk
  
VOLUME /tmp
 
# Add Spring Boot app.jar to Container
COPY --from=0 "/enroll/target/enroll-*-SNAPSHOT.jar" app.jar
 
# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
