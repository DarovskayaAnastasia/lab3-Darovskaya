import java.io.Serializable;

public class DelaysData implements Serializable {
    private Float max;
    private Float percent;

    public DelaysData() {
        this.max = 0f;
        this.percent = 0f;
    }

    public DelaysData(String line) {
        Integer count = null;
        Integer canceledAndDelayed
        Float max = Float.MIN_VALUE;
        String[] delays = line.split(" ");
        for (String delay : delays) {
            count++;
            if (!delay.equals(" ")) {
                if (Float.parseFloat(delay) > max) {
                    max = Float.parseFloat(delay);
                }
            }
        }

    }

    public Float getMax() {
        return this.max;
    }

    public Float getPercent() {
        return  this.percent;
    }

}