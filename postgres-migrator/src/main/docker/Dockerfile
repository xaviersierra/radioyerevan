FROM openjdk:11.0.12-jre-bullseye
WORKDIR '/app/'
COPY 'postgres-migrator/target/lib/postgresql-42.2.24.jar' '/app/lib/postgresql-42.2.24.jar'
COPY 'postgres-migrator/target/lib/flyway-core-7.14.1.jar' '/app/lib/flyway-core-7.14.1.jar'
COPY 'postgres-migrator/target/lib/checker-qual-3.5.0.jar' '/app/lib/checker-qual-3.5.0.jar'
COPY 'postgres-migrator/target/postgres-migrator.jar' '/app/postgres-migrator.jar'
CMD ["java", "-jar", "/app/postgres-migrator.jar"] 
