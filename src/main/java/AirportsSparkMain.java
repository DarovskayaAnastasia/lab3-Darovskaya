import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//
//import java.io.IOException;
import java.util.Map;

public class AirportsSparkMain {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> airportsFile = sc.textFile("L_AIRPORT_ID.csv");
        JavaRDD<String> onTimeFile = sc.textFile("664600583_T_ONTIME_sample.csv");

        JavaRDD<String> airportsWithoutHeader = removeCSVHeader(airportsFile);
        JavaRDD<String> onTimeWithoutHeader = removeCSVHeader(onTimeFile);


        JavaPairRDD<Integer, String> nameIdPair = airportsWithoutHeader.mapToPair(s -> {
            Integer airportID = Integer.parseInt(parseLine(s, Common.AIRPORT_ID_ROW));
            String airportName = parseLine(s, Common.AIRPORT_NAME_ROW);
            return new Tuple2<>(airportID, airportName);
        });

        Map<Integer, String> nameIdMap = nameIdPair.collectAsMap();

        JavaPairRDD<Tuple2<Integer, Integer>, String> arrivalDepartureDelayPair = onTimeWithoutHeader.mapToPair(s -> {
            Integer arrivalID = Integer.parseInt(parseLine(s, Common.ARRIVAL_AIRPORT_ID_ROW));
            Integer departureID = Integer.parseInt(parseLine(s, Common.DEPARTURE_AIRPORT_ID_ROW));
            String delay = parseLine(s, Common.DELAY_ROW);
            return new Tuple2<>(new Tuple2<>(arrivalID, departureID), delay);
        });

        JavaPairRDD<Tuple2<Integer, Integer>, String> collectedAirports = arrivalDepartureDelayPair.reduceByKey(
                (a, b) -> a + " " + b
        );

        JavaPairRDD<Tuple2<Integer, Integer>, DelaysData> reducedData = collectedAirports.mapToPair(s -> new Tuple2<>(s._1(), new DelaysData(s._2())));

        final Broadcast<Map<Integer, String>> airportsBroadcasted = sc.broadcast(nameIdMap);

        JavaRDD<DelaysData> result = reducedData.map(s -> {
            DelaysData delaysWithAirports = s._2();
            delaysWithAirports.setAirports(s._1(), airportsBroadcasted.value());
            return delaysWithAirports;
        });

        result.saveAsTextFile("output");

    }

//    private static final String parseLine(String line, int numberOfRow) throws IOException {
//
//        String result = "";
//
//        CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
//        for (CSVRecord record : parser) {
//            result = record.get(numberOfRow);
//        }
//
//        return result;
//    }

    private static final String parseLine(String line, int numberOfRow) {
        if (numberOfRow == Common.AIRPORT_ID_ROW || numberOfRow == Common.AIRPORT_NAME_ROW) {
            String[] rows = line.split("\",");
            for (int i = 0; i < rows.length; ++i) {
                rows[i] = rows[i].replaceAll("\"", "");
            }
            return rows[numberOfRow];
        } else {
            String[] rows = line.replaceAll("\"", "").split(",");
            return rows[numberOfRow];
        }
    }

    private static final JavaRDD<String> removeCSVHeader(JavaRDD<String> csvFile) {
        String header = csvFile.first();
        return csvFile.filter(line -> !line.equals(header));
    }

}
