FROM scratch
WORKDIR '/app/'
COPY 'joke-publisher/target/publisher' '/app/publisher'
COPY 'joke-publisher/src/main/resources/quartz.properties' '/app/quartz.properties'
CMD ["/app/publisher"]