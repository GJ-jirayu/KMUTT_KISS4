package th.ac.kmutt.chart.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by imake on 27/10/2015.
 */
@Embeddable
public class ChartFilterInstanceEntityPK  implements Serializable {
    @Column(name = "INSTANCE_ID")
    private String instanceId;

    @Column(name = "FILTER_ID")
    private Integer filterId;

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
