package th.ac.kmutt.chart.portlet;


import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import th.ac.kmutt.chart.form.FilterForm;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.model.FilterValueM;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.print.attribute.HashAttributeSet;
import javax.xml.namespace.QName;
import java.util.*;

/*import com.opencsv.CSVReader;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
*/
//import org.apache.log4j.Logger;
/*import th.ac.kmutt.research.constant.ServiceConstant;
import th.ac.kmutt.research.form.FilterForm;
import th.ac.kmutt.research.mapper.CustomObjectMapper;
import th.ac.kmutt.research.model.ResearchGroupM;
import th.ac.kmutt.research.service.ResearchService;
import th.ac.kmutt.research.utils.ImakeUtils;
import th.ac.kmutt.research.xstream.common.ImakeResultMessage;
*/


@Controller("filterController")
@RequestMapping("VIEW")
@SessionAttributes({"filterForm"})
public class FilterController {

    private static final Logger logger = Logger
            .getLogger(FilterController.class);

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


    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        //String a[] = new String[]{"ntcfaq.nfaqName"};
        final String[] ALLOWED_FIELDS = {"researchGroupM.researchGroupId", "researchGroupM.createdBy", "researchGroupM.createdDate",
                "researchGroupM.groupCode", "researchGroupM.permissions", "researchGroupM.updatedBy",
                "researchGroupM.updatedDate", "researchGroupM.groupTh", "researchGroupM.groupEng", "mode",
                "command", "keySearch", "pageNo", "paging.pageSize", "ids", "tab", "filter","instanceId","filterGlobals","aoe_global"};

        binder.setAllowedFields(ALLOWED_FIELDS);
    }

    @RequestMapping
    // default (action=list)
    public String showFilter(PortletRequest request, Model model) {
        FilterForm filterForm = null;
        if (!model.containsAttribute("filterForm")) {
            filterForm = new FilterForm();
            model.addAttribute("filterForm",
                    filterForm);
        } else {
            filterForm = (FilterForm) model.asMap().get("filterForm");
        }
        String keySearch = filterForm.getKeySearch();
    ThemeDisplay themeDisplay = (ThemeDisplay) request
            .getAttribute(WebKeys.THEME_DISPLAY);
    String instanceId=themeDisplay.getPortletDisplay().getInstanceId();

        FilterInstanceM filterInstanceM=new FilterInstanceM();
        filterInstanceM.setInstanceId(instanceId);

       List<FilterInstanceM> filterList= chartService.listFilterInstance(filterInstanceM);
        if(filterList!=null && filterList.size()>0){
            for (int i=0;i<filterList.size();i++){
                FilterM filterM=filterList.get(i).getFilterM();
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
                        filterValueM.setKeyMapping(MONTH_FILTER_KEY[j]);
                        filterValueM.setValueMapping(MONTH_FILTER_VALUE[j]);
                        filterValues.add(filterValueM);
                    }
                }
                filterM.setFilterValues(filterValues);
            }
        }
        model.addAttribute("filterList",filterList);
        logger.info("into list Filter");
        return "filter/showFilter";
    }

    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(SessionStatus status,javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("filterForm") FilterForm filterForm,
                             BindingResult result, Model model) {

        //response.setRenderParameter("nfaqSiteId",faqform.getNfaqSiteId());
       String command = "list";
        //}
        //String[] filterGolbals=filterForm.getFilterGlobals();
        String[] aoe_global=request.getParameterValues("aoe_global");
        /*
        logger.info("into doSubmit filterGolbals=>"+filterGolbals);
        if(filterGolbals!=null && filterGolbals.length>0){
            for (int i=0;i<filterGolbals.length;i++)
                logger.info(" filterGolbals["+i+"]"+filterGolbals[i]);
        }
    */
        logger.info("into doSubmit aoe_global=>"+aoe_global);
        Map filterMap=new HashMap();
        if(aoe_global!=null && aoe_global.length>0){
            for (int i=0;i<aoe_global.length;i++) {
                logger.info(" aoe_global[" + i + "]" + aoe_global[i]);
                filterMap.put(aoe_global[i],aoe_global[i]);
            }
        }
        String mode=filterForm.getMode();
        logger.info("into doSubmit mode=>"+mode);
       // logger.info("into doSubmitn "+filterForm.getCommand());
        QName qname = new QName("http://liferay.com/events","empinfo","x");
        //QName qname = new QName("http://liferay.com/events","empinfo");
        th.ac.kmutt.chart.fusion.model.FilterFusionM filterFusionM=new th.ac.kmutt.chart.fusion.model.FilterFusionM();
        filterFusionM.setFilters(aoe_global);
        response.setEvent(qname, filterFusionM);
       // status.setComplete();
        model.addAttribute("filterMap",filterMap);
        response.setRenderParameter("action", command);
    }

}
