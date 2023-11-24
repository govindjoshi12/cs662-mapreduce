#!/bin/sh
hdfs dfs -mkdir /user/root/data
hdfs dfs -mkdir /user/root/output
hdfs dfs -put ./data/$1 /user/root/data