import java.io.Serializable;

public class DelaysData implements Serializable {
    private Float max;
    private Float percent;

    private DelaysData() {
        this.max = 0f;
        this.percent = 0f;
    }

    private DelaysData(max, percent) {
        this.max = max;
        this.percent = percent;
    }

    
}