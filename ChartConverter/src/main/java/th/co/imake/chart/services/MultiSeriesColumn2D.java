package th.co.imake.chart.services;

import th.ac.kmutt.chart.fusion.gson.LineFusion;

import java.util.List;

/**
 * Created by imake on 30/01/2016.
 */
public interface MultiSeriesColumn2D {
    public String getCategories(String[] categoryList);
    public String getDataset(String seriesName,String[] data);
    public String getTrendlines(List<LineFusion> lineFusions);
}
