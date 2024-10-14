package hadoop_project;

import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import javax.naming.Context;

public class M_Mapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text wordPair = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer iterator = new StringTokenizer(value.toString());
        int numTokens = iterator.countTokens();

        if (numTokens < 2) {
            return; 
        }

        String[] words = new String[numTokens];

        for (int i = 0; iterator.hasMoreTokens(); i++) {
            words[i] = iterator.nextToken();
        }

        Arrays.sort(words);

        for (int h = 0; h < numTokens - 1; h++) {
            for (int k = h + 1; k < numTokens; k++) {
                if (!words[h].equals(words[k])) {
                    wordPair.set(words[h] + " " + words[k]);
                    context.write(wordPair, one);
                }
            }
        }
    }

}
