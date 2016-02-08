package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public class TrendlineFusion implements Serializable {

    @SerializedName("line")
    private List<LineFusion> line;

    public List<LineFusion> getLine() {
        return line;
    }

    public void setLine(List<LineFusion> line) {
        this.line = line;
    }
}
