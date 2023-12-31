## MP3: MapReduce with Hadoop and Docker

This repository contains all the code to generate the plots containing the tweet data. Pre-generated plots can be found in the plots folder. 

Each Tweet has the following format:
```
T 2009-06-01 00:00:00
U http://twitter.com/testuser
W Post content
Empty line
```

- ```plots/TweetTime.png``` plots total tweets per hour (task 2)
- ```plots/TweetSleep.png``` plots total tweets containing the word sleep per hour (task 3)

#### Running MapReduce
1. To create the docker image, make sure you have pulled the required required based image with ```docker pull sequenceiq/hadoop-docker:2.7.0```
2. Place the tweets txt file in ```./mapreduce/data/```

```NOTE: We are not required to build our own image, but I use my dockerfile to add the necessary environment variables to the image on creation```

3. Create a container and enter its terminal. Make sure scripts are executable to run them ```./run_docker.sh```
4. The script mounts the mapreduce folder in ```/mnt/mapreduce```
5. Change the directory to ```/mnt/mapreduce```
6. Run ```./put-data-hdfs.sh <name of tweet file in data folder>```

Now we can compile and run the mapreduce applications

Run 
```./compile-mp.sh TweetTime```
```./compile-mp.sh TweetSleep```

then 
```./run-mp.sh TweetTime```
```./run-mp.sh TweetSleep```

This will place the mapreduce output in ```out/<task name>/```

Now return to a terminal on the host machine. I used python3 and matplotlib to plot the data. Make sure you have the required python packages with 
```pip install -r requirements.txt```

Run ```./tweetplot.py```

The plots will be generated and placed in ```plots/```