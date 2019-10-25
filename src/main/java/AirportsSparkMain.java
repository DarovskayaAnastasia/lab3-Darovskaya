import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class AirportsSparkMain {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> airportsFile = sc.textFile("L_AIRPORT_ID.csv");
        JavaRDD<String> onTimeFile = sc.textFile("664600583_T_ONTIME_sample.csv");

        JavaPairRDD<String, Long> nameIDpair = airportsFile.mapToPair(s -> {} new Tuple2<>(airportID, airportName));

    }
}
