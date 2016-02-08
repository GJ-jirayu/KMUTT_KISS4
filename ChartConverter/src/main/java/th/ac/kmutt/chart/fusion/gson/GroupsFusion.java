package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public class GroupsFusion implements Serializable {
    @SerializedName("x")
    private  String label_x;

    @SerializedName("y")
    private String label_y;
    private String showbelow;
    private String scaletext;
    @SerializedName("items")
    private List<Item> items;

    public String getLabel_x() {
        return label_x;
    }

    public void setLabel_x(String label_x) {
        this.label_x = label_x;
    }

    public String getLabel_y() {
        return label_y;
    }

    public void setLabel_y(String label_y) {
        this.label_y = label_y;
    }

    public String getShowbelow() {
        return showbelow;
    }

    public void setShowbelow(String showbelow) {
        this.showbelow = showbelow;
    }

    public String getScaletext() {
        return scaletext;
    }

    public void setScaletext(String scaletext) {
        this.scaletext = scaletext;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
