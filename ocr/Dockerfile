#httpclient dockerfile
FROM java:8
#将当前目录下的jar包复制到docker容器的/目录下
ADD ../target/ocridentify-0.0.1-SNAPSHOT-exec.jar /ocridentify.jar
#运行过程中创建一个httpclient.jar文件
RUN bash -c 'touch /ocridentify.jar'
#声明服务运行在8080端口
EXPOSE 8080
#指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar", "/ocridentify.jar"]