package th.ac.kmutt.chart.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by imake on 20/10/2015.
 */
@Entity
@Table(name = "FUNDING_RESOURCE_SERVICE", schema = "", catalog = "CHART_DB")
// @IdClass(FundingResourceServiceEntityPK.class)
public class FundingResourceServiceEntity  implements Serializable {
    /*private Integer type;
    private Integer year;
    */
    private Integer value;
    @EmbeddedId
    private FundingResourceServiceEntityPK id;
  /*
    @Id
    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Id
    @Column(name = "YEAR")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
*/
    @Basic
    @Column(name = "VALUE")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }



    public FundingResourceServiceEntityPK getId() {
        return id;
    }

    public void setId(FundingResourceServiceEntityPK id) {
        this.id = id;
    }
}
