import scala.Tuple2;

import java.io.Serializable;
import java.util.Map;

public class DelaysData implements Serializable {
    private Float maxDelay;
    private Float countCancelledAndDelayed;
    private Float countAll;
    private String arrivalAirportName;
    private String departureAirportName;
    private Integer arrivalAirportID;
    private Integer departureAirportID;


    public DelaysData() {
        this.maxDelay = 0f;
        this.countAll = 0f;
        this.countCancelledAndDelayed = 0f;
        this.arrivalAirportName = "";
        this.departureAirportName = "";
        this.arrivalAirportID = 0;
        this.departureAirportID = 0;
    }

    public DelaysData(String delay) {
        countCancelledAndDelayed = 0f;
        if (!delay.isEmpty()) {
            this.maxDelay = Float.parseFloat(delay);
            if (maxDelay != 0f) {
                countCancelledAndDelayed = 1f;
            }
        }
        else
        {
            this.maxDelay = -1f;
            countCancelledAndDelayed = 1f;
        }
        countAll = 1f;

        this.arrivalAirportName = "";
        this.departureAirportName = "";
        this.arrivalAirportID = 0;
        this.departureAirportID = 0;
    }

    public Float getMaxDelay() {
        return this.maxDelay;
    }

    public void calculateDelays(DelaysData b) {
        this.maxDelay = (this.maxDelay > b.maxDelay)? this.maxDelay : b.maxDelay;
        this.countCancelledAndDelayed += this.countCancelledAndDelayed + b.countCancelledAndDelayed;
        this.countAll += this.countAll + b.countAll;

    }
    
    public void setAirports(Tuple2<Integer, Integer> airportsIDs, Map<Integer, String> airportsNames) {
         arrivalAirportID = airportsIDs._1();
         departureAirportID = airportsIDs._2();
         arrivalAirportName = airportsNames.get(arrivalAirportID);
         departureAirportName = airportsNames.get(departureAirportID);
    }

    @Override
    public String toString() {
        return "Arrival airport: " + arrivalAirportName + "\n Departure airport: " + departureAirportName + "\n max: " + maxDelay + "; percent: " + countCancelledAndDelayed*100/countAll;
    }

}