FROM daocloud.io/ld00000/jdk-8:0.6.0

MAINTAINER <xiongmin@k2data.com.cn>

ENV K2_WORK_DIR /usr/local/k2Asset
ENV K2_JAR_NAME k2Asset-eureka.jar

RUN mkdir -p $K2_WORK_DIR

COPY ./target/$K2_JAR_NAME $K2_WORK_DIR/$K2_JAR_NAME

WORKDIR $K2_WORK_DIR

ENTRYPOINT java -Dspring.profiles.active=prod $JAVA_OPTS -jar k2Asset-eureka.jar com.k2data.k2app.Application

EXPOSE 8761

CMD []
