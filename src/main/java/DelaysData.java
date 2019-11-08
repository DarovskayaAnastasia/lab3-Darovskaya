import org.apache.jute.compiler.JVector;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

public class DelaysData implements Serializable {
    private Vector delay = Vector();
    private Float max;
    private Float percent;
    private Integer arrivalAirportID;
    private Integer departureAirportID;
    private String arrivalAirportName;
    private String departureAirportName;

    public DelaysData(String delay) {

    }

//
//    public DelaysData() {
//        this.max = 0f;
//        this.percent = 0f;
//        this.arrivalAirportID = 0;
//        this.departureAirportID = 0;
//        this.arrivalAirportName = null;
//        this.departureAirportName = null;
//    }
//
//    public DelaysData(String line) {
//
//        this.arrivalAirportID = 0;
//        this.departureAirportID = 0;
//        this.arrivalAirportName = null;
//        this.departureAirportName = null;
//
//        Float totalCount = 0f;
//        Float canceledAndDelayedCount = 0f;
//        Float max = Float.MIN_VALUE;
//        String[] delays = line.split(" ");
//        for (String delay : delays) {
//            totalCount++;
//            if (!delay.equals("")) {
//                Float floatDelay = Float.parseFloat(delay);
//                if (floatDelay != 0) {
//                    canceledAndDelayedCount++;
//                    if (floatDelay > max) {
//                        max = floatDelay;
//                    }
//                }
//            }
//        }
//
//        this.max = max;
//        this.percent = canceledAndDelayedCount*100/totalCount;
//
//    }
//
//    public Float getMax() {
//        return this.max;
//    }
//
//    public Float getPercent() {
//        return  this.percent;
//    }
//
//    public void setAirports(Tuple2<Integer, Integer> airportsIDs, Map<Integer, String> airportsNames) {
//         arrivalAirportID = airportsIDs._1();
//         departureAirportID = airportsIDs._2();
//         arrivalAirportName = airportsNames.get(arrivalAirportID);
//         departureAirportName = airportsNames.get(departureAirportID);
//    }

    @Override
    public String toString() {
        return "Arrival airport: " + arrivalAirportName + "\n Departure airport: " + departureAirportName + "\n max: " + max + "; percent: " + percent;
    }

}