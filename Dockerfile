FROM ghcr.io/graalvm/graalvm-ce:21.3.0
# Installing maven
RUN curl https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz \
    --output /tmp/apache-maven-3.8.4.tar.gz
RUN tar xf /tmp/apache-maven-3.8.4.tar.gz -C /opt
RUN ln -s /opt/apache-maven-3.8.4 /opt/maven
ENV PATH="/opt/maven/bin:${PATH}"

WORKDIR /radioyerevan
