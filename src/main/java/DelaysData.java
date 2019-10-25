import java.io.Serializable;

public class DelaysData implements Serializable {
    private Float max;
    private Float percent;

    public DelaysData() {
        this.max = 0f;
        this.percent = 0f;
    }

    public DelaysData(String line) {
        Float totalCount = 0f;
        Float canceledAndDelayedCount = 0f;
        Float max = Float.MIN_VALUE;
        String[] delays = line.split(" ");
        for (String delay : delays) {
            totalCount++;
            Float floatDelay = Float.parseFloat(delay);
            if (floatDelay != 0) {
                canceledAndDelayedCount++;
                if (!delay.equals(" ")) {
                    if (floatDelay > max) {
                        max = floatDelay;
                    }
                }
            }
        }

        this.max = max;
        this.percent = canceledAndDelayedCount*100/totalCount;

    }

    public Float getMax() {
        return this.max;
    }

    public Float getPercent() {
        return  this.percent;
    }

}