package th.ac.kmutt.chart.builder;

import java.util.List;

public interface Chart {
	public void setData(List<Object[]> data);
	public String build();
}
