package th.ac.kmutt.chart.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by imake on 27/10/2015.
 */
@Embeddable
public class FilterInstanceEntityPK implements Serializable {
    @Column(name = "INSTANCE_ID")
    private String instanceId;

    @Basic
    @Column(name = "FILTER_ID")
    private Integer filterId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }
}
