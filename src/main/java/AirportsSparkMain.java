import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class AirportsSparkMain {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf(Hadoop ).setAppName(Hadoop "example");
        JavaSparkContext sc = new JavaSparkContext(Hadoop conf);
    }
}
