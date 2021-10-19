FROM scratch
WORKDIR '/app/'
COPY 'postgres-migrator/target/migrator' '/app/migrator'
COPY 'postgres-migrator/target/resources/migrations' '/app/migrations'
CMD ["/app/migrator"]