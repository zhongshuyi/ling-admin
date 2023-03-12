FROM openjdk:11-jre
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/ling-start-1.0-SNAPSHOT.jar /ling-admin.jar
# 运行过程中创建一个mall-tiny-docker-file.jar文件
RUN bash -c 'touch /ling-admin.jar'
# 声明服务运行在8080端口
EXPOSE 8080
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/ling-admin.jar"]
# 指定维护者的名字
MAINTAINER zhongshuyi
