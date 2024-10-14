package hadoop_project;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class R_Runner {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setInt("top_k", 20);
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(R_Runner.class);
        job.setMapperClass(M_Mapper.class);
        job.setNumReduceTasks(1);
        job.setCombinerClass(R_Reducer.class);
        job.setReducerClass(R_Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}