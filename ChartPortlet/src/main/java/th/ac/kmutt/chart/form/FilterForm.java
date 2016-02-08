package th.ac.kmutt.chart.form;

import java.io.Serializable;

public class FilterForm extends CommonForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6259325217543717053L;

    private String instanceId;
    private String[] filterGlobals;

    public FilterForm() {
        super();
        // TODO Auto-generated constructor stub
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
}
