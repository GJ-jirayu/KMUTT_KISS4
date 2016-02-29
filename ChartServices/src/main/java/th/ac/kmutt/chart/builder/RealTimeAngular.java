package th.ac.kmutt.chart.builder;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class RealTimeAngular extends CommonChart implements Chart {
	List<Object[]> data;
	public RealTimeAngular(){
		super("RealTimeAngular");
	}
	@Override
	public void setData(List<Object[]> data){
		this.data = data; 
	}
	@Override
	public String build() {
		JSONObject chartJson = super.getChartJson(); // retriveJSONObject
		try{
			JSONObject dials = chartJson.getJSONObject("dials");
			JSONArray da = dials.getJSONArray("dial");
			((JSONObject)da.get(0)).put("value", data.get(0));
		}catch(Exception ex){
		}
		return chartJson.toString();
	}
}
