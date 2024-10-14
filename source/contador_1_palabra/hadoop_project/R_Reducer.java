package hadoop_project;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Reducer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import java.util.Iterator;

public class R_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    HashMap<String, Integer> topWords = new HashMap<String, Integer>();

    public void reduce(Text key, Iterable<IntWritable> values,
            Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        if (topWords.containsKey(key + "")) {
            topWords.put(key.toString(), topWords.get(key + "") + sum);
        } else {
            topWords.put(key.toString(), sum);
        }

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        ValueComparator bvc = new ValueComparator(topWords);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);

        sorted_map.putAll(topWords);
        Iterator itr = sorted_map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry mp = (Map.Entry) itr.next();
            Text OutPutKey = new Text(mp.getKey() + "");
            IntWritable OutPutValue = new IntWritable(Integer.parseInt(mp.getValue() + ""));
            context.write(OutPutKey, OutPutValue);
        }

    }
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } 
    }
}
