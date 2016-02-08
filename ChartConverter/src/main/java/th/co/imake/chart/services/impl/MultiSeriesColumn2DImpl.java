package th.co.imake.chart.services.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import th.ac.kmutt.chart.fusion.gson.*;
import th.co.imake.chart.services.MultiSeriesColumn2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imake on 30/01/2016.x
 */

@Service("mscolumn2d")
public class MultiSeriesColumn2DImpl implements MultiSeriesColumn2D {
    @Override
    public String getCategories(String[] categoryList) {
        Gson gson =new Gson();
        CategoriesFusion categoriesFusionM =new CategoriesFusion();

        if(categoryList!=null) {
            List<Category> CategoryList =new ArrayList<Category>(categoryList.length);
            for (String category : categoryList) {
                Category categoryM = new Category();
                categoryM.setLabel(category);
                CategoryList.add(categoryM);
            }
            categoriesFusionM.setCategory(CategoryList);
        }
        return gson.toJson(categoriesFusionM);
    }

    @Override
    public String getDataset(String seriesName,String[] dataList) {
        Gson gson =new Gson();
        DatasetFusion datasetFusion =new DatasetFusion();
        datasetFusion.setSeriesname(seriesName);
        if(dataList!=null) {
            List<DataFusion> dataFusionList =new ArrayList<DataFusion>(dataList.length);
            for (String data : dataList) {
                DataFusion dataFusion = new DataFusion();
                dataFusion.setValue(data);
                dataFusionList.add(dataFusion);
            }
            datasetFusion.setData(dataFusionList);
        }
        return gson.toJson(datasetFusion);
    }

    @Override
    public String getTrendlines(List<LineFusion> lineFusions) {
        Gson gson =new Gson();
        TrendlineFusion trendlineFusion =new TrendlineFusion();
        trendlineFusion.setLine(lineFusions);

        return gson.toJson(trendlineFusion);
    }
    // Multi-series Column 2D mscolumn2d
    //Real-time Angular angulargauge

}
