FROM sequenceiq/hadoop-docker:2.7.0
WORKDIR /
ENTRYPOINT [ "/etc/bootstrap.sh" ] 
CMD [ "bash" ]
ENV HADOOP_CLASSPATH=/usr/java/default/lib/tools.jar
ENV PATH=$PATH:/usr/local/hadoop/bin
# RUN yum update -y
# RUN yum install -y python3
# RUN yum install -y python3-pip
# RUN hdfs dfs -ls
