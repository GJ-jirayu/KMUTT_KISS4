package th.ac.kmutt.chart.fusion.model;

import java.io.Serializable;

/**
 * Created by imake on 27/10/2015.
 */
public class FilterFusionM  implements Serializable {
    private String[] filters; // key_value

    public String[] getFilters() {
        return filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }
}
