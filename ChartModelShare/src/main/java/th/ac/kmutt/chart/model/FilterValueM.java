package th.ac.kmutt.chart.model;

import th.ac.kmutt.chart.xstream.common.ImakeXML;

import java.io.Serializable;

/**
 * Created by imake on 28/10/2015.
 */
public class FilterValueM extends ImakeXML implements Serializable {
    private String keyMapping;
    private String valueMapping;

    public String getKeyMapping() {
        return keyMapping;
    }

    public void setKeyMapping(String keyMapping) {
        this.keyMapping = keyMapping;
    }

    public String getValueMapping() {
        return valueMapping;
    }

    public void setValueMapping(String valueMapping) {
        this.valueMapping = valueMapping;
    }
}
