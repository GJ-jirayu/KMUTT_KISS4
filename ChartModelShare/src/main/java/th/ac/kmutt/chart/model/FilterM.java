package th.ac.kmutt.chart.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import th.ac.kmutt.chart.xstream.common.ImakeXML;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 20/10/2015.
 */
@XStreamAlias("FilterM")
public class FilterM extends ImakeXML implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer filterId;
    private String filterName;
    private String columnName;
    private String ref;
    private Integer serviceId;
    private String activeFlag;
    private String type;
    private List<FilterValueM> filterValues;
    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FilterValueM> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(List<FilterValueM> filterValues) {
        this.filterValues = filterValues;
    }
}
