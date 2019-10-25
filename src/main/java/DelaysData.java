import java.io.Serializable;

public class DelaysData implements Serializable {
    private Float max;
    private Float percent;

    public DelaysData(max, percent) {
        this.max = max;
        this.percent = percent;
    }


}