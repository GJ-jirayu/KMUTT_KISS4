package th.ac.kmutt.chart.portlet;

/**
 * Created by imake on 13/09/2015.
 */

import com.google.gson.Gson;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.fusion.model.ChartFusionM;
import th.ac.kmutt.chart.fusion.model.DataSourceFusionM;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("chartCommonSettingController")
@RequestMapping("EDIT")
@SessionAttributes({"chartSettingForm"})
public class ChartCommonSettingController {

    private static final Logger logger = Logger
            .getLogger(ChartCommonSettingController.class);

    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;
    // 'เลือกตามปี'
    private static final String[] YEAR_FILTER_KEY={"2550","2551","2552","2553","2554","2555","2556","2557","2558"};
    private static final String[] YEAR_FILTER_VALUE={"2550","2551","2552","2553","2554","2555","2556","2557","2558"};

    // ''เลือกตามเดือน''
    private static final String[] MONTH_FILTER_KEY={"1","2","3","4","5","6","7","8","9","10","11","12"};
    private static final String[] MONTH_FILTER_VALUE={"ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."};

    //'แหล่งที่ได้รับการเผยแพร่'
    private static final String[] PUBLISH_RESOURCE_FILTER_KEY={"1","2","3","4"};
    private static final String[] PUBLISH_RESOURCE_FILTER_VALUE={"วารสารนานาชาติ","ประชุมนานาชาติ","วารสารในประเทศ","ประชุมระดับประเทศ"};

    //'แหล่งเงินทุน'
    private static final String[] FUNDING_RESOURCE_FILTER_KEY={"1","2","3"};
    private static final String[] FUNDING_RESOURCE_FILTER_VALUE={"เงินรายได้ มจธ.","รัฐ ว.1","แหล่งทุนภายนอก"};
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        //String a[] = new String[]{"ntcfaq.nfaqName"};
        final String[] ALLOWED_FIELDS = {"researchGroupM.researchGroupId", "researchGroupM.createdBy", "researchGroupM.createdDate",
                "researchGroupM.groupCode", "researchGroupM.permissions", "researchGroupM.updatedBy",
                "researchGroupM.updatedDate", "researchGroupM.groupTh", "researchGroupM.groupEng", "mode", "command",
                "keySearch", "pageNo", "paging.pageSize", "ids", "tab", "chartType","jsonStr","chartInstance",
                "chartHeight","chartJson","dataAdhoc","advProp","comment","dataSourceType","aoe_internal","filterRole"
        ,"linkTo","subFromFilter","dataSource","aoe_internal","chartTitle","chartSubTitle","showFilter","titleFromFilter"};

        binder.setAllowedFields(ALLOWED_FIELDS);
    }

    @RequestMapping
    // default (action=list)
    public String showSetting(PortletRequest request, Model model) {
        ChartSettingForm chartSettingForm = null;
        if (!model.containsAttribute("chartSettingForm")) {
            chartSettingForm = new ChartSettingForm();
            model.addAttribute("chartSettingForm",
                    chartSettingForm);
        } else {
            chartSettingForm = (ChartSettingForm) model.asMap().get("chartSettingForm");
        }

        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
       // logger.info("themeDisplay->"+themeDisplay);
        String id= themeDisplay.getPortletDisplay().getId();

        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(instanceId);
        chartSettingForm.setChartInstance(instanceId);
        String json="";
        String dataSourceType="1";
        Integer serviceId=null;
        if(chartInstanceM!=null){
            json=chartInstanceM.getChartJson();
            dataSourceType=chartInstanceM.getDataSourceType();
            chartSettingForm.setAdvProp(chartInstanceM.getAdvProp());
            chartSettingForm.setDataAdhoc(chartInstanceM.getDataAdhoc());
            chartSettingForm.setChartType(chartInstanceM.getChartType());
            chartSettingForm.setJsonStr(json);
            chartSettingForm.setDataSourceType(dataSourceType);
            chartSettingForm.setLinkTo(chartInstanceM.getLinkTo());
            chartSettingForm.setFilterRole(chartInstanceM.getFilterRole());
            chartSettingForm.setSubFromFilter(chartInstanceM.getSubFromFilter());
            chartSettingForm.setTitleFromFilter(chartInstanceM.getTitleFromFilter());
            chartSettingForm.setDataSource(chartInstanceM.getServiceId()+"");
            chartSettingForm.setChartTitle(chartInstanceM.getChartTitle());
            chartSettingForm.setChartSubTitle(chartInstanceM.getChartSubTitle());
            chartSettingForm.setShowFilter(chartInstanceM.getShowFilter());
            serviceId=chartInstanceM.getServiceId();

        }

        ServiceM  serviceM=new ServiceM();
        serviceM.setType("chart");
        List<ServiceM> listServices=  chartService.listService(serviceM);
        model.addAttribute("serviceList",listServices);
        /*
        FilterM filterM=new FilterM();
        filterM.setType("global");
        List<FilterM> filterList= chartService.listFilter(filterM);
        */
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


        ChartM chartM=new ChartM();
        chartM.setActiveFlag("1");
        List<ChartM> chartList= chartService.listChart(chartM);
        model.addAttribute("chartList", chartList);
        //logger.info("listServices-->"+listServices.size());
        //logger.info("filterList-->"+filterList.size());
        //logger.info("serviceFilterMappingList-->"+serviceFilterMappingList.size());
        model.addAttribute("chartSettingForm", chartSettingForm);
        model.addAttribute("dataSourceJson",json);



        return "chart/settingChart";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("chartSettingForm") ChartSettingForm chartSettingForm,
                             BindingResult result, Model model) {
        //logger.info("into do submit instance =>"+chartSettingForm.getChartInstance()+" ,new json "+chartSettingForm.getJsonStr());
        boolean isSave=false;
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(chartSettingForm.getChartInstance());
        if(chartInstanceM==null) {
            chartInstanceM = new ChartInstanceM();
            chartInstanceM.setInstanceId(chartSettingForm.getChartInstance());
            isSave=true;
        }
        String jsonStr="";
       String dataJson="";

        //Gson gson =new Gson();
        //  System.out.println(gson.toJson(chartM));
        //DataSourceFusionM advProp=(DataSourceFusionM)gson.fromJson("{"+chartSettingForm.getAdvProp()+"}",DataSourceFusionM.class);
        JSONObject myObject = null;
        JSONObject chartOBJ=null;
        try {
            myObject = new JSONObject("{"+chartSettingForm.getAdvProp()+"}");
            chartOBJ =myObject.getJSONObject("chart");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(chartSettingForm.getChartTitle()!=null && chartSettingForm.getChartTitle().trim().length()>0){
            //advProp.getChartFusionM().setCaption(chartSettingForm.getChartTitle());
            try {
               // String caption=chartOBJ.getString("caption");
                chartOBJ.put("caption",chartSettingForm.getChartTitle());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(chartSettingForm.getChartSubTitle()!=null && chartSettingForm.getChartSubTitle().trim().length()>0){
            //advProp.getChartFusionM().setSubCaptionn(chartSettingForm.getChartSubTitle());
            try {
                chartOBJ.put("subCaption",chartSettingForm.getChartSubTitle());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            myObject.put("chart",chartOBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logger.info("myObject->"+myObject.toString());
        logger.info("mychart->"+chartOBJ.toString());
        String advProb_new="";
        chartInstanceM.setChartType(chartSettingForm.getChartType());
        if(chartSettingForm.getDataSourceType().equals("1")){ // webservice
            ServiceM serviceM=new ServiceM();
            serviceM.setServiceId(Integer.valueOf(chartSettingForm.getDataSource()));
            chartInstanceM.setServiceId(Integer.valueOf(chartSettingForm.getDataSource()));
            chartInstanceM.setService(serviceM);
            th.ac.kmutt.chart.model.CopyrightServiceM param
                    =new th.ac.kmutt.chart.model.CopyrightServiceM();
            dataJson="\"data\": []";

           // jsonStr="{\"chart\": "+gson.toJson(advProp.getChartFusionM())+","+dataJson+"}";
            //advProb_new=myObject.toString();
            //jsonStr=myObject.toString()+","+dataJson+"}";
        }else{
            //advProb_new="{"+chartSettingForm.getAdvProp()
            dataJson=chartSettingForm.getDataAdhoc();
            //jsonStr="{"+chartSettingForm.getAdvProp()+","+dataJson+"}";
        }
        jsonStr="{\"chart\": "+chartOBJ.toString()+","+dataJson+"}";
       // jsonStr="{\"chart\": "+gson.toJson(advProp.getChartFusionM())+","+dataJson+"}";
        chartInstanceM.setChartJson(jsonStr);
        //chartInstanceM.setChartId(1);
        //chartInstanceM.setServiceId(3);
        chartInstanceM.setAdvProp(chartSettingForm.getAdvProp());
        chartInstanceM.setChartHeight(chartSettingForm.getChartHeight());
        chartInstanceM.setDataSourceType(chartSettingForm.getDataSourceType());
        chartInstanceM.setLinkTo(chartSettingForm.getLinkTo());
        chartInstanceM.setFilterRole(chartSettingForm.getFilterRole());
        chartInstanceM.setSubFromFilter(chartSettingForm.getSubFromFilter());
        chartInstanceM.setTitleFromFilter(chartSettingForm.getTitleFromFilter());
        chartInstanceM.setShowFilter(chartSettingForm.getShowFilter());
        chartInstanceM.setChartTitle(chartSettingForm.getChartTitle());
        chartInstanceM.setChartSubTitle(chartSettingForm.getChartSubTitle());
        if(chartSettingForm.getComment()!=null){
            CommentM commentM=new CommentM();
            commentM.setInstanceId(chartInstanceM.getInstanceId());
            commentM.setComment(chartSettingForm.getComment());
            chartInstanceM.setComment(commentM);
        }
        logger.debug("chartSettingForm.getDataSourceType()-->" + chartSettingForm.getDataSourceType());
        logger.debug("chartSettingForm.getDataSource()-->" + chartSettingForm.getDataSource());
        logger.debug("chartSettingForm.getInstanceId()-->"+chartInstanceM.getInstanceId());
        chartInstanceM.setDataAdhoc(chartSettingForm.getDataAdhoc());
        if(isSave)
            chartService.saveChartInstance(chartInstanceM);
        else
            chartService.updateChartInstance(chartInstanceM);
        String[] aoe_internal=request.getParameterValues("aoe_internal");

        logger.debug("into doSubmit aoe_internal=>" + aoe_internal);

        if(chartSettingForm.getDataSourceType().equals("1")) { // webservice
            ChartFilterInstanceM chartFilterInstanceM = new ChartFilterInstanceM();
            chartFilterInstanceM.setIds(aoe_internal);
            chartFilterInstanceM.setServiceId(Integer.valueOf(chartSettingForm.getDataSource()));
            chartFilterInstanceM.setInstanceId(chartSettingForm.getChartInstance());
            chartService.updateChartFilterInstance(chartFilterInstanceM);
        }
        try {
            response.setPortletMode(PortletMode.VIEW);
            response.setRenderParameter("action", "list");
        } catch (PortletModeException e) {
            e.printStackTrace();
        }
    }
}
