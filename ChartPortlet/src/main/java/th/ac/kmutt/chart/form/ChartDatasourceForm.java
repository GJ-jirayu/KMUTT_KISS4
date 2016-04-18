package th.ac.kmutt.chart.form;

import java.io.Serializable;
import java.util.List;

import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.model.ServiceM;

public class ChartDatasourceForm extends CommonForm implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8926189943620440470L;
	private String datasourceName;
	private List<ServiceM> datasources;
	private Integer datasourceId;
    private String sqlString;
    private List<FilterM> filterList;
    private String sqlMessage;
    
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public List<ServiceM> getDatasources() {
		return datasources;
	}
	public void setDatasources(List<ServiceM> datasources) {
		this.datasources = datasources;
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
	public String getSqlMessage() {
		return sqlMessage;
	}
	public void setSqlMessage(String sqlMessage) {
		this.sqlMessage = sqlMessage;
	}
	public Integer getDatasourceId() {
		return datasourceId;
	}
	public void setDatasourceId(Integer datasourceId) {
		this.datasourceId = datasourceId;
	}
}
