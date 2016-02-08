package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public class AnnotationsFusion implements Serializable {
    @SerializedName("groups")
    private List<GroupsFusion> groups;

    public List<GroupsFusion> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsFusion> groups) {
        this.groups = groups;
    }
}
