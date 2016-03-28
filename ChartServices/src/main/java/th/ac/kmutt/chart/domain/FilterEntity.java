package th.ac.kmutt.chart.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by imake on 20/10/2015.
 */
@Entity
@Table(name = "FILTER", schema = "", catalog = "CHART_DB")
public class FilterEntity  implements Serializable {
    private Integer filterId;
    private String filterName;
    private String columnName;
    private String ref;
    private Integer serviceId;
    private String activeFlag;
    private String type;
    private String defaultValue;
    private String sqlQuery;
    /*
    private Collection<ChartFilterInstanceEntity> chartFilterInstancesByFilterId;
    private Collection<FilterInstanceEntity> filterInstancesByFilterId;
    private Collection<ServiceFilterMappingEntity> serviceFilterMappingsByFilterId;
    private Collection<FilterValueEntity> filterValueEntity;*/
    
    @Id
    @Column(name = "FILTER_ID")
    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    @Basic
    @Column(name = "FILTER_NAME")
    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @Basic
    @Column(name = "COLUMN_NAME")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "REF")
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Basic
    @Column(name = "SERVICE_ID")
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "ACTIVE_FLAG")
    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }
 
	@Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	@Basic
    @Column(name = "SUBSTITUTE_DEFAULT")
    public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Basic
    @Column(name = "SQL_QUERY")
	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterEntity that = (FilterEntity) o;

        if (filterId != null ? !filterId.equals(that.filterId) : that.filterId != null) return false;
        if (filterName != null ? !filterName.equals(that.filterName) : that.filterName != null) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (ref != null ? !ref.equals(that.ref) : that.ref != null) return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
        if (activeFlag != null ? !activeFlag.equals(that.activeFlag) : that.activeFlag != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filterId != null ? filterId.hashCode() : 0;
        result = 31 * result + (filterName != null ? filterName.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (ref != null ? ref.hashCode() : 0);
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
/*
    @OneToMany(mappedBy = "filterByFilterId")
    public Collection<ChartFilterInstanceEntity> getChartFilterInstancesByFilterId() {
        return chartFilterInstancesByFilterId;
    }

    public void setChartFilterInstancesByFilterId(Collection<ChartFilterInstanceEntity> chartFilterInstancesByFilterId) {
        this.chartFilterInstancesByFilterId = chartFilterInstancesByFilterId;
    }

    @OneToMany(mappedBy = "filterByFilterId")
    public Collection<FilterInstanceEntity> getFilterInstancesByFilterId() {
        return filterInstancesByFilterId;
    }

    public void setFilterInstancesByFilterId(Collection<FilterInstanceEntity> filterInstancesByFilterId) {
        this.filterInstancesByFilterId = filterInstancesByFilterId;
    }

    @OneToMany(mappedBy = "filterByFilterId")
    public Collection<ServiceFilterMappingEntity> getServiceFilterMappingsByFilterId() {
        return serviceFilterMappingsByFilterId;
    }

    public void setServiceFilterMappingsByFilterId(Collection<ServiceFilterMappingEntity> serviceFilterMappingsByFilterId) {
        this.serviceFilterMappingsByFilterId = serviceFilterMappingsByFilterId;
    }
    */

 /*   @OneToMany(mappedBy = "filterEntity")
    public Collection<FilterValueEntity> getFilterValueEntity() {
        return filterValueEntity;
    }

    public void setFilterValueEntity(Collection<FilterValueEntity> filterValueEntity) {
        this.filterValueEntity = filterValueEntity;
    }*/
}
