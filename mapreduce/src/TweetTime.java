package src;

import java.io.IOException;
import java.util.*;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class TweetTime {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            if(line.length() > 0 && line.charAt(0) == 'T') {

                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                // String dateStr = line.substring(2);
                // LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
                
                // // Realized I don't actually need to use datetime
                // word.set(Integer.toString(date.getHour()));

                word.set(line.substring(13, 15));
                output.collect(word, one);
            }
        }
    }


    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int sum = 0;
            while(values.hasNext()) {
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));
        }

    }

    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(TweetTime.class);
        conf.setJobName("tweettime");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class); 

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1])); 

        JobClient.runJob(conf); 
    }
}
