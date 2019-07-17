FROM openjdk:8
WORKDIR /usr/src
ENV MONGO_DATABASE=keepnote
ENV MONGO_PORT=27017
ENV MONGO_HOST=localhost
EXPOSE 8084
ADD ./target/CategoryService.jar /usr/src/CategoryService.jar
ENTRYPOINT ["java","-jar","/usr/src/CategoryService.jar"]