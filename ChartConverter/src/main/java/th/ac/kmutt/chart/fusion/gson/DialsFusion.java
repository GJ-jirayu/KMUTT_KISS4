package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public class DialsFusion implements Serializable {
    @SerializedName("dial")
    private List<Dial> dial;

    public List<Dial> getDial() {
        return dial;
    }

    public void setDial(List<Dial> dial) {
        this.dial = dial;
    }
}
