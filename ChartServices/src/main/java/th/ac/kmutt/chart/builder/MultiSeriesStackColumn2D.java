package th.ac.kmutt.chart.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import th.ac.kmutt.chart.constant.ServiceConstant;


public class MultiSeriesStackColumn2D extends CommonChart implements Chart {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	List<Object[]> data;
	public MultiSeriesStackColumn2D(){
		super("MultiSeriesStackColumn2D"); 
	}
	
	@Override
	public void setData(List<Object[]> data){
		this.data = data;
	}
	
	@Override
	public String build(){
		JSONObject chartJson = super.getChartJson(); // retriveJSONObject	
		try{
			/* Generate Category */	
			JSONArray categoryJson = new  JSONArray();
			List<String> makeCategories = new ArrayList<String>();
			for( Object[] resultRow : this.data){
				makeCategories.add(resultRow[0].toString());
			}
			Set<String> setUniqueCategory = new TreeSet<String>(makeCategories);
			List<Object> UniqueCategory = new ArrayList<Object>(setUniqueCategory);
			Object[] objUniqueCategory = UniqueCategory.toArray();
			
			JSONObject jsonObjCategory = new  JSONObject();
			JSONArray jsonArrCategory = new JSONArray();
			for( Object resultRow : objUniqueCategory){
				JSONObject attr = new JSONObject();
				attr.put("label", resultRow);
				categoryJson.put(attr);
			}
			jsonObjCategory = jsonObjCategory.put("category", categoryJson);
			jsonArrCategory = jsonArrCategory.put(jsonObjCategory);
			
			
			/* Generate Sub Category */	
			JSONArray subCategoryJson = new  JSONArray();
			List<String> makeSubCategories = new ArrayList<String>();
			for( Object[] resultRow : this.data){
				makeSubCategories.add(resultRow[1].toString());
			}
			Set<String> setUniqueSubCategory = new TreeSet<String>(makeSubCategories);
			List<Object> UniqueSubCategory = new ArrayList<Object>(setUniqueSubCategory);
			Object[] objUniqueSubCategory = UniqueSubCategory.toArray();
			
			JSONObject jsonObjSubCategory = new  JSONObject();
			JSONArray jsonArrSubCategory = new JSONArray();
			for( Object resultRow : objUniqueSubCategory){
				JSONObject attr = new JSONObject();
				attr.put("label", resultRow);
				subCategoryJson.put(attr);
			}
			jsonObjSubCategory = jsonObjSubCategory.put("subCategory", subCategoryJson);
			jsonArrSubCategory = jsonArrSubCategory.put(jsonObjSubCategory);
			
			/* Generate new Series */
			List<String> makeSeries = new ArrayList<String>();
			for( Object[] resultRow : this.data){
				makeSeries.add(resultRow[2].toString());
			}
			Set<String> setUniqueSeries= new TreeSet<String>(makeSeries);
			List<Object> UniqueSeries = new ArrayList<Object>(setUniqueSeries);
			Object[] objUniqueSeries = UniqueSeries.toArray();
			
			
			/* Generate Data group set */
			//JSONObject tempDataSet = new JSONObject();			
			JSONArray mainDataSet = new JSONArray();
			Integer subCategorySize = jsonArrSubCategory.getJSONObject(0).getJSONArray("subCategory").length();
			
			for(int i = 0 ; i < subCategorySize; i++){
				JSONObject subObjDataSet = new JSONObject();
				String subCategoryStr = jsonArrSubCategory.getJSONObject(0)
						.getJSONArray("subCategory").getJSONObject(i).getString("label");
				JSONArray subDataSet = new JSONArray();
				
				for( Object strSeries : objUniqueSeries){
					JSONObject seriesObjDataSet = new JSONObject();
					JSONArray arrData = new JSONArray();
						
					for(Object strCatrgory : objUniqueCategory){
						for(Object[] resultData : this.data){						
							if(resultData[0].toString().equals(strCatrgory) 
									&& resultData[2].toString().equals(strSeries)
									&& resultData[1].toString().equals(subCategoryStr)){
								JSONObject attr = new JSONObject();	
								attr.put("value",resultData[3]);
								arrData.put(attr);
							}						
						}
					}
					
					seriesObjDataSet.put("seriesname", strSeries);
					seriesObjDataSet.put("data", arrData);
					subDataSet.put(seriesObjDataSet);
				}
	
				subObjDataSet.put("dataset", subDataSet);
				mainDataSet.put(subObjDataSet);
			}
			
			//tempDataSet = tempDataSet.put("dataset", mainDataSet);
			
			chartJson = chartJson.put("categories", jsonArrCategory).put("dataset", mainDataSet);
			logger.info("\n TempData: "+chartJson+"\n");
			//logger.info("\n"+jsonArrSubCategory+"\n");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return chartJson.toString();
	}
}
