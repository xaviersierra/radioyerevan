FROM openjdk:11.0.12-jre-bullseye
WORKDIR '/app/'
COPY 'joke-publisher/target/lib/postgresql-42.2.24.jar' '/app/lib/postgresql-42.2.24.jar'
COPY 'joke-publisher/target/lib/twitter4j-core-4.0.7.jar' '/app/lib/twitter4j-core-4.0.7.jar'
COPY 'joke-publisher/target/lib/checker-qual-3.5.0.jar' '/app/lib/checker-qual-3.5.0.jar'
COPY 'joke-publisher/target/lib/quartz-2.3.2.jar' '/app/lib/quartz-2.3.2.jar'
COPY 'joke-publisher/target/lib/mchange-commons-java-0.2.15.jar' '/app/lib/mchange-commons-java-0.2.15.jar'
COPY 'joke-publisher/target/lib/joke-access-1.0-SNAPSHOT.jar' '/app/lib/joke-access-1.0-SNAPSHOT.jar'
COPY 'joke-publisher/target/lib/postgres-access-1.0-SNAPSHOT.jar' '/app/lib/postgres-access-1.0-SNAPSHOT.jar'
COPY 'joke-publisher/target/lib/slf4j-api-1.7.7.jar' '/app/lib/slf4j-api-1.7.7.jar'
COPY 'joke-publisher/target/lib/slf4j-simple-1.7.7.jar' '/app/lib/slf4j-simple-1.7.7.jar'
COPY 'joke-publisher/target/joke-publisher.jar' '/app/joke-publisher.jar'
CMD ["java", "-jar", "/app/joke-publisher.jar"]