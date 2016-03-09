package th.ac.kmutt.chart.form;

import java.io.Serializable;
import java.util.List;

import th.ac.kmutt.chart.model.FilterM;

public class FilterForm extends CommonForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6259325217543717053L;

    private String instanceId;
    private String[] filterGlobals;
    private List<FilterM> filterList;

    public FilterForm() {
        super();
    }

    public String[] getFilterGlobals() {
        return filterGlobals;
    }

    public void setFilterGlobals(String[] filterGlobals) {
        this.filterGlobals = filterGlobals;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

	public List<FilterM> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<FilterM> filterList) {
		this.filterList = filterList;
	}
    
}
