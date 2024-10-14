package hadoop_project;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Counter;

public class M_Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    static enum CountersEnum {
        INPUT_WORDS
    }

    public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");

        for (String word : words) {
            Text outputKey = new Text(word.toUpperCase().trim());

            IntWritable outputValue = new IntWritable(1);

            con.write(outputKey, outputValue);

            Counter counter = con.getCounter(CountersEnum.class.getName(),
                    CountersEnum.INPUT_WORDS.toString());
            counter.increment(1);

        }
    }

}