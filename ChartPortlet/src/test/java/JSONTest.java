import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import junit.framework.Assert;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;
import th.ac.kmutt.chart.fusion.model.ChartFusionM;
import th.ac.kmutt.chart.fusion.model.DataSourceFusionM;

/**
 * Created by imake on 19/10/2015.
 */
// mvn test , mvn -Dtest=TestApp1 test
//mvn clean test -Dtest=JSONTest#testPrintHelloWorld2
public class JSONTest {
  //  private String json="{\"chart\": {\"caption\": \"Half Yearly Revenue Analysis\"}}";
    //private String json="{\"chart\": {\"chartValue\": \"chart Value\"}}";

            //"        \"yaxisname\": \"Revenue\",\n" +
            //"        \"numberprefix\": \"$\",\n" +
            //"        \"yaxismaxvalue\": \"250000\",\n" +
            //"        \"showborder\": \"0\",\n" +
            //"        \"theme\": \"fint\"\n" +
private String json="{\n" +
                    "        \"chart\": {\n" +
                    "          \"caption\": \"Number of visitors last week\",\n" +
                    "          \"subCaption\": \"Bakersfield Central vs Los Angeles Topanga\",\n" +
                    "          \"captionFontSize\": \"14\",\n" +
                    "          \"subcaptionFontSize\": \"14\",\n" +
                    "          \"subcaptionFontBold\": \"0\",\n" +
                    "          \"paletteColors\": \"#0075c2,#1aaf5d\",\n" +
                    "          \"bgcolor\": \"#ffffff\",\n" +
                    "          \"showBorder\": \"0\",\n" +
                    "          \"showShadow\": \"0\",\n" +
                    "          \"showCanvasBorder\": \"0\",\n" +
                    "          \"usePlotGradientColor\": \"0\",\n" +
                    "          \"legendBorderAlpha\": \"0\",\n" +
                    "          \"legendShadow\": \"0\",\n" +
                    "          \"showAxisLines\": \"0\",\n" +
                    "          \"showAlternateHGridColor\": \"0\",\n" +
                    "          \"divlineThickness\": \"1\",\n" +
                    "          \"divLineDashed\": \"1\",\n" +
                    "          \"divLineDashLen\": \"1\",\n" +
                    "          \"divLineGapLen\": \"1\",\n" +
                    "          \"xAxisName\": \"Day\",\n" +
                    "          \"showValues\": \"0\"\n" +
                    "        }" +
                    /*
                    ",\n" +
                    "        \"categories\": [\n" +
                    "          {\n" +
                    "            \"category\": [\n" +
                    "              {\n" +
                    "                \"label\": \"Mon\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"Tue\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"Wed\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"National holiday\"\n" +
                    "               // \"dashed\": \"1\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"Thu\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"Fri\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"Sat\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"label\": \"Sun\"\n" +
                    "              }\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        ],\n" +
                    "        \"dataset\": [\n" +
                    "          {\n" +
                    "            \"seriesname\": \"Bakersfield Central\",\n" +
                    "            \"data\": [\n" +
                    "              {\n" +
                    "                \"value\": \"15123\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"14233\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"25507\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"9110\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"15529\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"20803\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"19202\"\n" +
                    "              }\n" +
                    "            ]\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"seriesname\": \"Los Angeles Topanga\",\n" +
                    "            \"data\": [\n" +
                    "              {\n" +
                    "                \"value\": \"13400\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"12800\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"22800\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"12400\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"15800\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"19800\"\n" +
                    "              },\n" +
                    "              {\n" +
                    "                \"value\": \"21800\"\n" +
                    "              }\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        ]\n" +
                    "        \n" +
                    */
                    "      }";

    @Test
    public void testXstream() {
        System.out.println("aoe 1");
        //XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        /*
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.processAnnotations(ChartFusionM.class);
        ChartFusionM chartM=new ChartFusionM();
        chartM.setCaption("chart Value");
        //chartM.setChartName("chart Name");
        chartM.setChartValue("chart Value");
        System.out.println(xstream.toXML(chartM));
        //System.out.println(json);
        xstream.setMode(XStream.NO_REFERENCES);
        */
        //ChartFusionM chart=(ChartFusionM)xstream.fromXML(json);
        //System.out.println(chart.getCaption());
        //chart.setDataList(new ArrayList<DataM>());
      //  System.out.println(xstream.toXML(chart));
        Gson gson =new Gson();
      //  System.out.println(gson.toJson(chartM));
       // ChartFusionM chart=(ChartFusionM)gson.fromJson(json,ChartFusionM.class);
      // DataSourceFusionM chart2=(DataSourceFusionM)gson.fromJson(json,DataSourceFusionM.class);
        //System.out.println(chart.getDataset());
      //  System.out.println(gson.toJson(chart2.getChartFusionM()));

       // org.json.JsonReader rdr = org.json.Json.createReader()){

        JSONObject myObject = null;
        try {
            myObject = new JSONObject(json);
            JSONObject chartOBJ=myObject.getJSONObject("chart");
            String caption=chartOBJ.getString("caption");
            System.out.println("xxxxx->"+caption);
            chartOBJ.put("caption","new aoe");
            myObject.put("chart",chartOBJ);
            System.out.println(chartOBJ.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String subCaption="(ตุลาคม _เลือกตามเดือน_7 6_เลือกตามเดือน_)";
        subCaption= subCaption.replaceAll("_เลือกตามเดือน_","aoe");
        System.out.println("xxx"+subCaption);
        //chartOBJ.put("subCaption",subCaption);
        /*
            org.json.JSONArray  results = obj.getJSONArray("data");
            for (org.json.JsonObject result : results.getValuesAs(org.json.JsonObject.class)) {
                System.out.print(result.getJsonObject("from").getString("name"));
                System.out.print(": ");
                System.out.println(result.getString("message", ""));
                System.out.println("-----------");
            }
            */
    }

    @Test
    public void test1() {
        System.out.println("aoe 1");
        Assert.assertEquals("Hello World 2", "Hello World 2");

    }
    @Test
    public void test2() {
        System.out.println("aoe 2");
        Assert.assertEquals("Hello World 2", "Hello World 2");

    }
    /*
    public static void  main(String[] args){
        System.out.println("chatchai Debug");
    }
    */
}
