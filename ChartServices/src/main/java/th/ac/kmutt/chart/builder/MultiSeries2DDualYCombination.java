package th.ac.kmutt.chart.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import th.ac.kmutt.chart.constant.ServiceConstant;


public class MultiSeries2DDualYCombination extends CommonChart implements Chart {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	List<Object[]> data;
	public MultiSeries2DDualYCombination(){
		super("MultiSeries2DDualYCombination");  
	}
	@Override
	public void setData(List<Object[]> data){
		this.data = data;
	}
	
	@Override
	public String build(){
		JSONObject chartJson = super.getChartJson(); // retriveJSONObject
		try{
			/* Generate json category */	
			JSONArray categoryJson = new JSONArray();			
			ArrayList<String> categoryList = new ArrayList<String>();
			ArrayList<String> uniqueCategoryList = new ArrayList<String>();
			for( Object[] resultRow : this.data){
				if(!categoryList.contains(resultRow[0].toString())){ 
					uniqueCategoryList.add(resultRow[0].toString());
					JSONObject attr = new JSONObject();
					attr.put("label", resultRow[0]);
					categoryJson.put(attr);
				} 
				categoryList.add(resultRow[0].toString());
			}
			JSONObject jsonObjCategory = new JSONObject().put("category", categoryJson);
			JSONArray jsonArrCategory = new JSONArray().put(jsonObjCategory);
			logger.info("\n -- jsonArrCategory --> "+jsonArrCategory+"\n");
			
			
			/* Generate json Series object */
			JSONArray seriesJson = new JSONArray();			
			ArrayList<String> seriesList = new ArrayList<String>();
			ArrayList<String> uniqueSeriesList = new ArrayList<String>();
			for( Object[] resultRow : this.data){
				if(!seriesList.contains(resultRow[1].toString())){ 
					uniqueSeriesList.add(resultRow[1].toString());
					JSONObject attr = new JSONObject();
					attr.put("seriesname", resultRow[1].toString());
					attr.put("renderAs", resultRow[3].toString());
					attr.put("parentYAxis", resultRow[4].toString());					
					seriesJson.put(attr);
				}
				seriesList.add(resultRow[1].toString());
			}
			logger.info("\n -- seriesJson --> "+seriesJson+"\n");
			
			
			/* Generate dataset */
			int index = 0;
			for( Object resultUniqueSeries : uniqueSeriesList){				
				JSONArray objectData = new JSONArray();
				/*for (Object[] resultData : this.data) {
					if (resultData[1].equals(resultUniqueSeries)) {
						JSONObject attr = new JSONObject();
						attr.put("value", resultData[2]);
						objectData.put(attr);
					} 
				}*/
				
				for (Object uniqueCategory : uniqueCategoryList) {
					for (Object[] resultData : this.data) {
						if (resultData[1].equals(resultUniqueSeries)) {
							if(resultData[0].equals(uniqueCategory)){
								JSONObject attr = new JSONObject();
								attr.put("value", resultData[2]);
								objectData.put(attr);
							}
						} 
					}
					
				}
				logger.info("\n -- objectData --> "+objectData+"\n"); 
				
				
				seriesJson.getJSONObject(index).put("data", objectData);
				index++;
			}
			
			chartJson = chartJson.put("categories", jsonArrCategory).put("dataset", seriesJson);

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return chartJson.toString();
	}
}
