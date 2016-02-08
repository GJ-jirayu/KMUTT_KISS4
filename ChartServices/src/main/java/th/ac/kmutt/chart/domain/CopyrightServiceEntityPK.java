package th.ac.kmutt.chart.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by imake on 20/10/2015.
 */

@Embeddable
public class CopyrightServiceEntityPK implements Serializable {

    @Column(name = "TYPE")
    private Integer type;
    @Column(name = "YEAR")
    private Integer year;
    @Column(name = "MONTH")
    private Integer month;


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


    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyrightServiceEntityPK that = (CopyrightServiceEntityPK) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        return result;
    }
}
