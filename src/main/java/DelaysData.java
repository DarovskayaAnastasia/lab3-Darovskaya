import java.io.Serializable;

public class DelaysData implements Serializable {
    private Float maxDelay;
    private Float countCancelledAndDelayed;
    private Float countAll;

//    private Float max;
//    private String arrivalAirportName;
//    private String departureAirportName;
    public DelaysData() {
        this.maxDelay = 0f;
        this.countAll = 0f;
        this.countCancelledAndDelayed = 0f;
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
    }

    public Float getMaxDelay() {
        return this.maxDelay;
    }

    public void calculateDelays(DelaysData b) {
        this.maxDelay = (this.maxDelay > b.maxDelay)? this.maxDelay : b.maxDelay;
        this.countCancelledAndDelayed += this.countCancelledAndDelayed + b.countCancelledAndDelayed;
        this.countAll += this.countAll + b.countAll;

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
//
//    @Override
//    public String toString() {
//        return "Arrival airport: " + arrivalAirportName + "\n Departure airport: " + departureAirportName + "\n max: " + max + "; percent: " + percent;
//    }

}