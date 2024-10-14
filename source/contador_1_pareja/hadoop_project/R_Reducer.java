package hadoop_project;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class R_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private TreeMap<Text, Integer> countMap = new TreeMap<>();

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        countMap.put(new Text(key), sum);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        Map<Text, Integer> sortedMap = sortByValues(countMap);
        int counter = 0;
        for (Map.Entry<Text, Integer> entry : sortedMap.entrySet()) {
            if (counter++ == 20) {
                break;
            }
            context.write(entry.getKey(), new IntWritable(entry.getValue()));
        }
    }

    private static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0)
                return 1;
            else
                return compare;
        };

        Map<K, V> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
