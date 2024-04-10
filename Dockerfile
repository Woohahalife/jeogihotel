FROM openjdk17:alpine

COPY ./build/libs/KDT_BE7_Mini-Project-0.0.1-SNAPSHOT.jar accommodation-backend.jar

CMD ["java", "-jar", "accommodation-backend.jar"]