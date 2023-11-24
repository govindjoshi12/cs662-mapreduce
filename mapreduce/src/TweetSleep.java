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

public class TweetSleep {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String tweet = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(tweet, "\n");

            String time = tokenizer.nextToken();
            String user = tokenizer.nextToken();

            // Set hour 
            word.set(tweet.substring(13, 15));

            // Tweet content can span multiple lines
            while(tokenizer.hasMoreTokens()) {
                String content = tokenizer.nextToken().toLowerCase();
                if(content.contains("sleep")) {
                    // only collect if tweet contains "sleep"
                    output.collect(word, one);
                    break;
                }
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
        JobConf conf = new JobConf(TweetSleep.class);
        conf.setJobName("tweetsleep");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.set("textinputformat.record.delimiter", "\n\n");

        conf.setOutputFormat(TextOutputFormat.class); 

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1])); 

        JobClient.runJob(conf); 
    }
}
