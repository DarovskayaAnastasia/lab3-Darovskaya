import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public class AirportsSparkMain {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> airportsFile = sc.textFile("L_AIRPORT_ID.csv");
        JavaRDD<String> onTimeFile = sc.textFile("664600583_T_ONTIME_sample.csv");

//        JavaPairRDD<String, Long> nameIDpair = airportsFile.mapToPair(s -> {} new Tuple2<>(airportID, airportName));

    }

    private String[] parseLine(String line) throws IOException {

        String[] result = new String[2];

        CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
        for (CSVRecord record : parser) {
            result[0] = record.get(0);
            result[1] = record.get(1);
        }

        return result;
    }

}
