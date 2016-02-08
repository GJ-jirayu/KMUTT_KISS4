package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 21/10/2015.
 */
public class DatasetFusion implements Serializable {
    private String seriesname;
    @SerializedName("data")
    private List<DataFusion> data;

    public List<DataFusion> getData() {
        return data;
    }

    public void setData(List<DataFusion> data) {
        this.data = data;
    }

    public String getSeriesname() {
        return seriesname;
    }

    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname;
    }
}
