import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Map;

public class AirportsSparkMain {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> airportsFile = sc.textFile("L_AIRPORT_ID.csv");
        JavaRDD<String> onTimeFile = sc.textFile("664600583_T_ONTIME_sample.csv");

        JavaPairRDD<Integer, String> nameIdPair = airportsFile.mapToPair(s -> {
            Integer airportID = Integer.parseInt(parseLine(s, Common.AIRPORT_ID_ROW));
            String airportName = parseLine(s, Common.AIRPORT_NAME_ROW);
            return new Tuple2<>(airportID, airportName);
        });

        Map<Integer, String> nameIdMap = nameIdPair.collectAsMap();

        JavaPairRDD<Tuple2<Integer, Integer>, String> arrivalDepartureDelayPair = onTimeFile.mapToPair(s -> {
            Integer arrivalID = Integer.parseInt(parseLine(s, Common.ARRIVAL_AIRPORT_ID_ROW));
            Integer departureID = Integer.parseInt(parseLine(s, Common.DEPARTURE_AIRPORT_ID_ROW));
            String delay = parseLine(s, Common.DELAY_ROW);
            return new Tuple2<>(new Tuple2<>(arrivalID, departureID), delay);
        });

        JavaPairRDD<> collectedAirports = 



//        final Broadcast<Map<String, AirportData>> airportsBroadcasted = sc.broadcast(stringAirportDataMap);



    }

    private static String parseLine(String line, int numberOfRow) throws IOException {

        String result = "";

        CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
        for (CSVRecord record : parser) {
            result = record.get(numberOfRow);
        }

        return result;
    }


}
