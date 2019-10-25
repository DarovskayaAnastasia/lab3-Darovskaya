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

        JavaPairRDD<String, Long> nameIDpair = airportsFile.mapToPair(s -> { String airportID = parseLine(s, 0);
                                                                             Long airportName = (parseLine(s, 1));
                                                                             return new Tuple2<>(airportID, airportName)});

    }

    private static String parseLine(String line, int numberOfRow) throws IOException {

        String result = new String;

        CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
        for (CSVRecord record : parser) {
            result = record.get(numberOfRow);
        }

        return result;
    }

}
