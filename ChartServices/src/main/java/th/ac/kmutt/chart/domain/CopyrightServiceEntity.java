package th.ac.kmutt.chart.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by imake on 20/10/2015.
 */
@Entity
@Table(name = "COPYRIGHT_SERVICE", schema = "", catalog = "CHART_DB")
// @IdClass(CopyrightServiceEntityPK.class)
public class CopyrightServiceEntity  implements Serializable {
   /**/
    // for distplay
    /*
   @Column(name = "TYPE",insertable = false,updatable = false)
    private Integer type;

    @Column(name = "YEAR",insertable = false,updatable = false)
    private Integer year;

    @Column(name = "MONTH",insertable = false,updatable = false)
    private Integer month;
*/
   @Basic
   @Column(name = "VALUE")
    private Integer value;
    @Basic
    @Column(name = "MONTH_DESC")
    private String monthDesc;

    @EmbeddedId
    private CopyrightServiceEntityPK id;


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public String getMonthDesc() {
        return monthDesc;
    }

    public void setMonthDesc(String monthDesc) {
        this.monthDesc = monthDesc;
    }


    public CopyrightServiceEntityPK getId() {
        return id;
    }

    public void setId(CopyrightServiceEntityPK id) {
        this.id = id;
    }
}
