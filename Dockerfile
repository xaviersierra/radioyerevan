FROM ghcr.io/graalvm/graalvm-ce:21.2.0
RUN gu install native-image
# Installing maven
RUN curl https://dlcdn.apache.org/maven/maven-3/3.8.3/binaries/apache-maven-3.8.3-bin.tar.gz \
    --output /tmp/apache-maven-3.8.3.tar.gz
RUN tar xf /tmp/apache-maven-3.8.3.tar.gz -C /opt
RUN ln -s /opt/apache-maven-3.8.3 /opt/maven
ENV PATH="/opt/maven/bin:${PATH}"
# Installing musl
RUN curl https://musl.libc.org/releases/musl-1.2.2.tar.gz --output /tmp/musl-1.2.2.tar.gz
RUN tar xf /tmp/musl-1.2.2.tar.gz -C /opt/
WORKDIR /opt/musl-1.2.2
RUN ./configure --disable-shared --prefix=/usr/local/musl
RUN make
RUN make install
ENV PATH="/usr/local/musl/bin:${PATH}"
# Installing zlib
RUN curl https://zlib.net/zlib-1.2.11.tar.gz --output /tmp/zlib-1.2.11.tar.gz
RUN tar xf /tmp/zlib-1.2.11.tar.gz -C /opt/
WORKDIR /opt/zlib-1.2.11/
ENV CC="musl-gcc"
RUN ./configure --static --prefix=/usr/local/musl
RUN make
RUN make install
ENV LD_LIBRARY_PATH="/usr/lib:${LD_LIBRARY_PATH}"
ENV PKG_CONFIG_PATH="/usr/lib/pkgconfig:${PKG_CONFIG_PATH}"
WORKDIR /radioyerevan