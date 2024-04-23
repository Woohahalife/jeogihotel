FROM openjdk:17-jdk-alpine as builder
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} accommodation-backend.jar

RUN java -Djarmode=layertools -jar accommodation-backend.jar extract

FROM openjdk:17-jdk-alpine
COPY --from=builder ./dependencies/ ./
COPY --from=builder ./spring-boot-loader/ ./
COPY --from=builder ./snapshot-dependencies/ ./
COPY --from=builder ./application/ ./
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]