package th.ac.kmutt.chart.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import th.ac.kmutt.chart.xstream.common.ImakeXML;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 20/10/2015.
 *  Edited by gj +sqlString 
 */
@XStreamAlias("ServiceM")
public class ServiceM extends ImakeXML implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer serviceId;
    private String serviceName;
    private String endPoint;
    private String modelName;
    private String type;
    private String activeFlag;
    private String sqlString;
    private List<FilterM> filterList;
    
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public List<FilterM> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<FilterM> filterList) {
		this.filterList = filterList;
	}
}
