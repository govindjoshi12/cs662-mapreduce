hdfs dfs -rm -r -skipTrash /user/root/output/$1
rm -r out/$1
cd build/$1/
hadoop jar $1.jar src.$1 /user/root/data/$2 /user/root/output/$1
cd ../../
hdfs dfs -copyToLocal /user/root/output/$1 out/