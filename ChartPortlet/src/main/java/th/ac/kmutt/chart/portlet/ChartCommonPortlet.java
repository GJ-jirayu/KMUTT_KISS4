package th.ac.kmutt.chart.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.fusion.model.*;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import th.co.imake.chart.services.MultiSeriesColumn2D;

/**
 * Created by imake on 07/09/2015.
 */

@Controller("chartCommonController")
@RequestMapping("VIEW")
@SessionAttributes({"chartSettingForm"})
public class ChartCommonPortlet {

    /*private static final Logger logger = LogManager
            .getLogger(ChartCommonPortlet.class);
            */
    private static final Logger logger = Logger
            .getLogger(ChartCommonPortlet.class);
    //'แหล่งที่ได้รับการเผยแพร่'
    private static final String[] PUBLISH_RESOURCE_FILTER_KEY={"1","2","3","4"};
    private static final String[] PUBLISH_RESOURCE_FILTER_VALUE={"วารสารนานาชาติ","ประชุมนานาชาติ","วารสารในประเทศ","ประชุมระดับประเทศ"};

    //'แหล่งเงินทุน'
    private static final String[] FUNDING_RESOURCE_FILTER_KEY={"1","2","3"};
    private static final String[] FUNDING_RESOURCE_FILTER_VALUE={"เงินรายได้ มจธ.","รัฐ ว.1","แหล่งทุนภายนอก"};

    // 'เลือกตามปี'
    private static final String[] YEAR_FILTER_KEY={"2550","2551","2552","2553","2554","2555","2556","2557","2558"};
    private static final String[] YEAR_FILTER_VALUE={"2550","2551","2552","2553","2554","2555","2556","2557","2558"};
    // ''เลือกตามเดือน''
    private static final String[] MONTH_FILTER_KEY={"1","2","3","4","5","6","7","8","9","10","11","12"};
    private static final String[] MONTH_FILTER_VALUE={"ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."};

    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;

    @Autowired
    @Qualifier("mscolumn2d")
    private MultiSeriesColumn2D mscolumn2d;
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

        final String[] ALLOWED_FIELDS = {"researchGroupM.researchGroupId", "researchGroupM.createdBy", "researchGroupM.createdDate",
                "researchGroupM.groupCode", "researchGroupM.permissions", "researchGroupM.updatedBy",
                "researchGroupM.updatedDate", "researchGroupM.groupTh", "researchGroupM.groupEng", "mode", "command",
                "keySearch", "pageNo", "paging.pageSize", "ids", "tab", "filter","chartType","jsonStr",
                "chartInstance","chartJson","dataAdhoc","advProp","chartHeight","comment","dataSourceType","aoe_internal","filterRole"
        ,"linkTo","subFromFilter","dataSource","aoe_internal","chartTitle","chartSubTitle","showFilter","titleFromFilter"};

        binder.setAllowedFields(ALLOWED_FIELDS);
    }
    @RequestMapping
    // default (action=list)
    public String displayChart(PortletRequest request, Model model) {
        String[] labels={"Q1","Q2","Q3"};
        String categories=mscolumn2d.getCategories(labels);
        System.out.println(categories);
        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        String id= themeDisplay.getPortletDisplay().getId();
        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();

        logger.info("into list ChartCommonPortlet");
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(instanceId);
        String jsonStr="";
        String chartType="";
        String chartHeight="300";
        String comment="";
        String dataSourceType="2";
        String subFromFilter="0";
        String titleFromFilter="0";
        String showFilter="0";
        String linkTo="";
        String chartTitle="";
        String chartSubTitle="";
        Integer serviceId=null;

        if(chartInstanceM!=null){
            chartType=chartInstanceM.getChartType();
            jsonStr=chartInstanceM.getChartJson();
            chartHeight=chartInstanceM.getChartHeight();
            if(chartInstanceM.getComment()!=null && chartInstanceM.getComment().getComment()!=null)
                comment=chartInstanceM.getComment().getComment();
            dataSourceType=chartInstanceM.getDataSourceType();
            subFromFilter=chartInstanceM.getSubFromFilter();
            titleFromFilter=chartInstanceM.getTitleFromFilter();
            showFilter=chartInstanceM.getShowFilter();
            linkTo=chartInstanceM.getLinkTo();
            serviceId =chartInstanceM.getServiceId();
            chartTitle=chartInstanceM.getChartTitle();
            chartSubTitle=chartInstanceM.getChartSubTitle();
        }
        logger.info("serviceId="+serviceId);

        FilterFusionM filterFusionM = null;
        if (!model.containsAttribute("filterFusionM")) {

        } else {
            filterFusionM = (FilterFusionM) model.asMap().get("filterFusionM");
        }
        JSONObject myObject = null;
        JSONObject chartOBJ=null;
        if(jsonStr!=null && jsonStr.trim().length()>0){


        try {
            myObject = new JSONObject(jsonStr);
            chartOBJ =myObject.getJSONObject("chart");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        logger.info("filterFusionM-->"+filterFusionM);
        if(filterFusionM!=null && filterFusionM.getFilters()!=null){
            String[] filters=filterFusionM.getFilters();
            if(filters!=null){
                for(int i=0;i<filters.length;i++){
                    if(subFromFilter!=null && subFromFilter.equals("1")){
                        String[] filter_id_array=filters[i].split("_");
                        if(filter_id_array!=null && filter_id_array.length>=2){
                            if(!filter_id_array[0].equals("-1")) {
                                FilterM filterM = chartService.findFilterById(Integer.valueOf(filter_id_array[0]));
                                String replace_str = "_" + filterM.getFilterName().trim() + "_";
                                try {
                                    String subCaption = chartOBJ.getString("subCaption");
                                    subCaption = subCaption.replaceAll(replace_str, filter_id_array[2]);
                                    chartOBJ.put("subCaption", subCaption);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            try {
                                chartOBJ.put("subCaption", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(titleFromFilter!=null && titleFromFilter.equals("1")){
                        String[] filter_id_array=filters[i].split("_");
                        if(filter_id_array!=null && filter_id_array.length>=2){
                            if(!filter_id_array[0].equals("-1")) {
                                FilterM filterM = chartService.findFilterById(Integer.valueOf(filter_id_array[0]));
                                String replace_str = "_" + filterM.getFilterName().trim() + "_";
                                try {
                                    String caption = chartOBJ.getString("caption");
                                    caption = caption.replaceAll(replace_str, filter_id_array[2]);
                                    chartOBJ.put("caption", caption);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            //chartTitle=chartInstanceM.getChartTitle();
                            //chartSubTitle=chartInstanceM.getChartSubTitle();
                          //  if(chartTitle!=null && )
                            try {
                                chartOBJ.put("caption", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    logger.info("filter-->["+i+"]"+filters[i]);
                }
            }
        }else{
            if(subFromFilter!=null && subFromFilter.equals("1")){
                try {
                    //String caption = chartOBJ.getString("caption");
                    //caption = caption.replaceAll(replace_str, filter_id_array[2]);
                    chartOBJ.put("subCaption", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(titleFromFilter!=null && titleFromFilter.equals("1")){
                try {
                    //String caption = chartOBJ.getString("caption");
                    //caption = caption.replaceAll(replace_str, filter_id_array[2]);
                    chartOBJ.put("caption", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            myObject.put("chart",chartOBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logger.info("myObject----->"+myObject.toString());
        jsonStr=myObject.toString();

        }

        ChartSettingForm chartSettingForm = null;
        if (!model.containsAttribute("chartSettingForm")) {
            chartSettingForm = new ChartSettingForm();
            model.addAttribute("chartSettingForm",
                    chartSettingForm);
        } else {
            chartSettingForm = (ChartSettingForm) model.asMap().get("chartSettingForm");
        }

        if(dataSourceType.equals("1")){
           if(serviceId!=null){

               if(serviceId==3){
                   DataSourceFusionM advProp= new DataSourceFusionM();
                   StringBuffer jsonSB=new StringBuffer("");

                   jsonSB.append("\"categories\": [");
                   jsonSB.append("{\n" +
                           "            \"category\": [");

                   JournalPapersServiceM param=new JournalPapersServiceM();
                   param.setInstanceId(instanceId);
                   param.setFilterFusionM(filterFusionM);
                   List<JournalPapersServiceM> journalPapersServiceMList=
                           chartService.listJournalPapersService(param);
                   StringBuffer datasetSB_inner=new StringBuffer();
                   List<DatasetFusionM> dataset =new ArrayList<DatasetFusionM>();
                   Map yearMap =new HashMap();
                   int indexmap=0;
                   if (journalPapersServiceMList!= null && journalPapersServiceMList.size()>0){
                       for (int i=0;i<journalPapersServiceMList.size();i++){
                           JournalPapersServiceM journalPapersServiceM=journalPapersServiceMList.get(i);
                           if(!yearMap.containsKey(journalPapersServiceM.getYear()+"")){
                               if(indexmap!=0 ){//&& i!=(journalPapersServiceMList.size()-1)){
                                   jsonSB.append(",");
                               }
                               jsonSB.append("{\n" +
                                       "                    \"label\": \""+ journalPapersServiceM.getYear()+"\"\n" +
                                       "                }");
                               yearMap.put(journalPapersServiceM.getYear()+"",journalPapersServiceM.getYear()+"");
                               indexmap++;
                           }

                       }
                   }
                   jsonSB.append(" ]\n" +
                           "        }\n" +
                           "    ]");

                       int index_outer=0;

                       for (int j=0;j<PUBLISH_RESOURCE_FILTER_KEY.length;j++){
                           int index=0;
                           boolean haveData=false;
                           List<DataFusionM> data =new ArrayList<DataFusionM>();
                           for (int i=0;i<journalPapersServiceMList.size();i++){
                               JournalPapersServiceM journalPapersServiceM=journalPapersServiceMList.get(i);

                               if(PUBLISH_RESOURCE_FILTER_KEY[j].equals(""+journalPapersServiceM.getType())){
                                   DataFusionM d=new DataFusionM();
                                   d.setValue(journalPapersServiceM.getValue()+"");
                                   data.add(d);
                                   index++;
                               }
                           }
                           if(index!=0){
                               DatasetFusionM datasetFusionM=new DatasetFusionM();
                               datasetFusionM.setSeriesname(PUBLISH_RESOURCE_FILTER_VALUE[j]);
                               datasetFusionM.setData(data);
                               dataset.add(datasetFusionM);
                           }
                           if(index_outer!=0 ){

                           }
                       }

                   logger.debug("xxx dataset-->" + dataset.size());
                   logger.debug("jsonSB xxx ->" + jsonSB.toString());
                   StringBuffer jsonSB_dataSet=new StringBuffer("");
                   jsonSB_dataSet.append("\"dataset\": [");

                   StringBuffer aoe_sb=new StringBuffer();

                   if(dataset!=null && dataset.size()>0) {
                       for (int i = 0; i < dataset.size(); i++) {
                           DatasetFusionM datasetFusionM = dataset.get(i);
                           if (i != 0) {
                               aoe_sb.append(",");
                           }
                           aoe_sb.append("{\"seriesname\": \"" + datasetFusionM.getSeriesname() + "\",");
                           aoe_sb.append("\"data\": [");
                           List<DataFusionM> dataFusionlist = datasetFusionM.getData();

                           for (int j = 0; j < dataFusionlist.size(); j++) {
                               DataFusionM dataFusionM = dataFusionlist.get(j);
                               if (j != 0) {
                                   aoe_sb.append(",");
                               }
                               aoe_sb.append("{\n" +
                                       "                                   \"value\": \"" + dataFusionM.getValue() + "\"\n" +
                                       "                               }");
                           }
                           aoe_sb.append("]\n" +
                                   "                           }");

                       }
                   }
                   jsonSB_dataSet.append(aoe_sb.toString());
                   jsonSB_dataSet.append("]");
                   logger.debug("aoe_sbB xxx ->" + aoe_sb.toString());
                   logger.debug("final json ->{" + chartInstanceM.getAdvProp() + "," + jsonSB.toString() + "," + jsonSB_dataSet.toString() + "}");
                  // jsonStr="{"+chartInstanceM.getAdvProp()+","+jsonSB.toString()+","+jsonSB_dataSet.toString()+"}";
                   if(chartOBJ!=null)
                        jsonStr="{\"chart\": "+chartOBJ.toString()+","+jsonSB.toString()+","+jsonSB_dataSet.toString()+"}";
               }else if(serviceId==4){
                   CopyrightServiceM param=new CopyrightServiceM();
                   param.setInstanceId(instanceId);
                   param.setFilterFusionM(filterFusionM);
                  List<CopyrightServiceM> copyrightServiceMList =
                          chartService.listCopyrightService(param);
               }else if(serviceId==5){
                   FundingResourceServiceM param =new FundingResourceServiceM();
                   param.setInstanceId(instanceId);
                   param.setFilterFusionM(filterFusionM);
                   List<FundingResourceServiceM> fundingResourceServiceMList=
                           chartService.listFundingResourceService(param);
               }

               chartSettingForm.setDataSource(serviceId+"");
           }
        }

        chartSettingForm.setChartType(chartType);
        chartSettingForm.setJsonStr(jsonStr);
        chartSettingForm.setChartHeight(chartHeight);
        chartSettingForm.setComment(comment);
        chartSettingForm.setDataSourceType(dataSourceType);
        chartSettingForm.setSubFromFilter(subFromFilter);
        chartSettingForm.setTitleFromFilter(titleFromFilter);
        chartSettingForm.setShowFilter(showFilter);
        chartSettingForm.setLinkTo(linkTo);



        Map filterMap=new HashMap();
        List<ServiceFilterMappingM> serviceFilterMappingMList =null;
        if(dataSourceType.equals("1")){// webservice Type
            if(serviceId!=null){
                ServiceFilterMappingM param=new ServiceFilterMappingM();
                param.setServiceId(serviceId);
                serviceFilterMappingMList=chartService.listServiceFilterMapping(param);
                if(serviceFilterMappingMList!=null && serviceFilterMappingMList.size()>0){
                    for (int i=0;i<serviceFilterMappingMList.size();i++){
                        FilterM filterM=serviceFilterMappingMList.get(i).getFilterM();
                        Integer filterId=filterM.getFilterId();
                        List<FilterValueM> filterValues=null;
                        if(filterId.intValue()==1){//เลือกตามปี
                            filterValues=new ArrayList<FilterValueM>(YEAR_FILTER_KEY.length);
                            for (int j=0;j<YEAR_FILTER_KEY.length;j++){
                                FilterValueM filterValueM=new FilterValueM();
                                filterValueM.setKeyMapping(YEAR_FILTER_KEY[j]);
                                filterValueM.setValueMapping(YEAR_FILTER_VALUE[j]);
                                filterValues.add(filterValueM);
                            }

                        }else if(filterId.intValue()==2){//แหล่งที่ได้รับการเผยแพร่
                            filterValues=new ArrayList<FilterValueM>(PUBLISH_RESOURCE_FILTER_KEY.length);
                            for (int j=0;j<PUBLISH_RESOURCE_FILTER_KEY.length;j++){
                                FilterValueM filterValueM=new FilterValueM();
                                filterValueM.setKeyMapping(PUBLISH_RESOURCE_FILTER_KEY[j]);
                                filterValueM.setValueMapping(PUBLISH_RESOURCE_FILTER_VALUE[j]);
                                filterValues.add(filterValueM);
                            }
                        }
                        else if(filterId.intValue()==3){//แหล่งเงินทุน
                            filterValues=new ArrayList<FilterValueM>(FUNDING_RESOURCE_FILTER_KEY.length);
                            for (int j=0;j<FUNDING_RESOURCE_FILTER_KEY.length;j++){
                                FilterValueM filterValueM=new FilterValueM();
                                filterValueM.setKeyMapping(FUNDING_RESOURCE_FILTER_KEY[j]);
                                filterValueM.setValueMapping(FUNDING_RESOURCE_FILTER_VALUE[j]);
                                filterValues.add(filterValueM);
                            }
                        }
                        else if(filterId.intValue()==4){//เลือกตามเดือน
                            filterValues=new ArrayList<FilterValueM>(MONTH_FILTER_KEY.length);
                            for (int j=0;j<MONTH_FILTER_KEY.length;j++){
                                FilterValueM filterValueM=new FilterValueM();
                                filterValueM.setKeySearch(MONTH_FILTER_KEY[j]);
                                filterValueM.setValueMapping(MONTH_FILTER_VALUE[j]);
                                filterValues.add(filterValueM);
                            }
                        }
                        filterM.setFilterValues(filterValues);
                    }
                }


                ChartFilterInstanceM chartFilterInstanceM =new ChartFilterInstanceM();
                chartFilterInstanceM.setServiceId(serviceId);
                chartFilterInstanceM.setInstanceId(instanceId);
                List<ChartFilterInstanceM> chartFilterInstanceList= chartService.listChartFilterInstance(chartFilterInstanceM);
                if(chartFilterInstanceList!=null && chartFilterInstanceList.size()>0){
                    for (int i=0;i<chartFilterInstanceList.size();i++) {
                        String key=chartFilterInstanceList.get(i).getFilterM().getFilterId()+"_"+chartFilterInstanceList.get(i).getValue();
                        logger.info(" aoe_internal[" + i + "]" + key);

                        filterMap.put(key,key);
                    }
                }
            }
        }
        model.addAttribute("filterMap",filterMap);
        model.addAttribute("serviceFilterMappingMList",serviceFilterMappingMList);


        model.addAttribute("chartSettingForm", chartSettingForm);
        return "chart/p";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                         @ModelAttribute("chartSettingForm") ChartSettingForm chartSettingForm,
                         BindingResult result, Model model) {
        String[] aoe_internal=request.getParameterValues("aoe_internal");

        logger.debug("into doSubmit aoe_internal=>" + aoe_internal);

       // if(chartSettingForm.getDataSourceType().equals("1")) { // webservice
            ChartFilterInstanceM chartFilterInstanceM = new ChartFilterInstanceM();
            chartFilterInstanceM.setIds(aoe_internal);
            chartFilterInstanceM.setServiceId(Integer.valueOf(chartSettingForm.getDataSource()));
            chartFilterInstanceM.setInstanceId(chartSettingForm.getChartInstance());
            chartService.updateChartFilterInstance(chartFilterInstanceM);
        //}
        response.setRenderParameter("action", "list");
    }
    @EventMapping(value ="{http://liferay.com/events}empinfo")
    //@javax.portlet.ProcessEvent(qname = "{http://liferay.com}empinfo")
    public void receiveEvent(EventRequest request, EventResponse response, ModelMap map)
    {
        Event event = request.getEvent();
        FilterFusionM filterFusionM = (FilterFusionM)event.getValue();
        logger.info("into receiveEvent->"+filterFusionM);
        map.put("empinfo", filterFusionM);
        map.addAttribute("filterFusionM", filterFusionM);
        response.setRenderParameter("action", "list");
    }
}
