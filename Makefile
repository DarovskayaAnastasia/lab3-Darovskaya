NAMENODE_CURRENT = home/michail/hdata/namenode/current
DATANODE_CURRENT = home/michail/hdata/datanode/current
OUTPUT = gitwatch/lab2-Darovskaya/output
TARGET = gitwatch/lab2-Darovskaya/target


.PHONY: all hadoop_stage launch_stage clean


all: hadoop_stage launch_stage

hadoop_stage:
	hdfs namenode -format
	start-dfs.sh
	start-yarn.sh

launch_stage:
	mvn package
	hadoop jar ~/hadoop-2.9.2/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.2.jar pi 2 5
	hadoop fs -copyFromLocal src/main/resources/664600583_T_ONTIME_sample.csv
	hadoop fs -copyFromLocal src/main/resources/L_AIRPORT_ID.csv
	export HADOOP_CLASSPATH=home/michail/gitwatch/lab2-Darovskaya/target/hadoop-examples-1.0-SNAPSHOT-shaded.jar
	hadoop JoinJob L_AIRPORT_ID.csv 664600583_T_ONTIME_sample.csv output
	hadoop fs -copyToLocal output

clean:
	stop-yarn.sh
	stop-dfs.sh
	rm -rf $(DATANODE_CURRENT) 
	rm -rf $(NAMENODE_CURRENT) 
	rm -rf $(TARGET)
	rm -rf $(OUTPUT)
