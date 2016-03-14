package th.ac.kmutt.chart.builder;

import java.util.List;

import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import th.ac.kmutt.chart.constant.ServiceConstant;


public class Pie3D extends CommonChart implements Chart {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	List<Object[]> data;
	public Pie3D(){
		super("VerticalLed");  
	}
	@Override
	public void setData(List<Object[]> data){
		this.data = data;
	}
	@Override
	public String build(){
		JSONObject chartJson = super.getChartJson(); // retriveJSONObject		
		try{			
			chartJson.put("value", data.get(0));
			logger.info("\n super.getChartJson(): "+chartJson.toString()+"\n");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return chartJson.toString();
	}
}
