FROM openjdk:8
WORKDIR /usr/src
ENV MONGO_DATABASE=dockerplugin
ENV MONGO_PORT=27017
ENV MONGO_HOST=localhost
EXPOSE 8091
ADD ./target/DockerPlugin.jar /usr/src/DockerPlugin.jar
ENTRYPOINT ["java","-jar","/usr/src/DockerPlugin.jar"]