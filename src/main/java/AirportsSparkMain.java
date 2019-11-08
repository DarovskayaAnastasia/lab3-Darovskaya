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

        JavaRDD<String> airportsFile = sc.textFile(AirportsFilesData.AIRPORTS_FILENAME);
        JavaRDD<String> onTimeFile = sc.textFile(AirportsFilesData.DELAYS_FILENAME);

        JavaRDD<String> airportsWithoutHeader = removeCSVHeader(airportsFile);
        JavaRDD<String> onTimeWithoutHeader = removeCSVHeader(onTimeFile);


        JavaPairRDD<Integer, String> nameIdPair = airportsWithoutHeader.mapToPair(s -> {
            Integer airportID = Integer.parseInt(parseLine(s, AirportsFilesData.AIRPORT_ID_ROW));
            String airportName = parseLine(s, AirportsFilesData.AIRPORT_NAME_ROW);
            return new Tuple2<>(airportID, airportName);
        });

        Map<Integer, String> nameIdMap = nameIdPair.collectAsMap();

        JavaPairRDD<Tuple2<Integer, Integer>, String> arrivalDepartureDelayPair = onTimeWithoutHeader.mapToPair(s -> {
            Integer arrivalID = Integer.parseInt(parseLine(s, AirportsFilesData.ARRIVAL_AIRPORT_ID_ROW));
            Integer departureID = Integer.parseInt(parseLine(s, AirportsFilesData.DEPARTURE_AIRPORT_ID_ROW));
            String delay = parseLine(s, AirportsFilesData.DELAY_ROW);
            return new Tuple2<>(new Tuple2<>(arrivalID, departureID), delay);
        });

        JavaPairRDD<Tuple2<Integer, Integer>, DelaysData> collectedAirports = arrivalDepartureDelayPair.reduceByKey(
                (a, b) -> {
                    return Float.parseFloat(a) + Float.parseFloat(b);
//                    if (!a.isEmpty() || !b.isEmpty() ) {
//                        (Float.parseFloat(a) > Float.parseFloat(b)) ? a : b;
//                    }
                }
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

    private static final String parseLine(String line, int numberOfRow) {
        if (numberOfRow == AirportsFilesData.AIRPORT_ID_ROW || numberOfRow == AirportsFilesData.AIRPORT_NAME_ROW) {
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