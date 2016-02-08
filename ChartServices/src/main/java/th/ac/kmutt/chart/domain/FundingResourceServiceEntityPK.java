package th.ac.kmutt.chart.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by imake on 20/10/2015.
 */

@Embeddable
public class FundingResourceServiceEntityPK implements Serializable {
    @Column(name = "TYPE",insertable = false,updatable = false)
    private Integer type;

    @Column(name = "YEAR",insertable = false,updatable = false)
    private Integer year;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundingResourceServiceEntityPK that = (FundingResourceServiceEntityPK) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }
}
