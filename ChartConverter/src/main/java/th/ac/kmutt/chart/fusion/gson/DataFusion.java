package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by imake on 19/10/2015.
 */
public class DataFusion implements Serializable {
    private String label;
    private String value;
    private String tooltext;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTooltext() {
        return tooltext;
    }

    public void setTooltext(String tooltext) {
        this.tooltext = tooltext;
    }
}
