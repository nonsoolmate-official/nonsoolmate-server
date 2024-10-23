FROM amd64/eclipse-temurin:17-jdk

WORKDIR /app

COPY ./build/libs/nonsoolmate-batch-0.0.1-SNAPSHOT.jar /app/nonsoolmate-batch.jar

ARG SPRING_PROFILES_ACTIVE

ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "nonsoolmate-batch.jar"]
