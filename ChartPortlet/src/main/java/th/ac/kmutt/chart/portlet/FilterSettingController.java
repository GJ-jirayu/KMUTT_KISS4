package th.ac.kmutt.chart.portlet;

/**
 * Created by imake on 07/09/2015.
 */

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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.form.FilterForm;
import th.ac.kmutt.chart.model.ChartInstanceM;
import th.ac.kmutt.chart.model.CommentM;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.*;
import java.util.List;

@Controller("filterSettingController")
@RequestMapping("EDIT")
@SessionAttributes({"filterForm"})
public class FilterSettingController {

    private static final Logger logger = Logger
            .getLogger(FilterSettingController.class);
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
    public String showSettingFilter(PortletRequest request,PortletResponse response, Model model) {
        logger.info("into Edit Filter");
        FilterM filterM=new FilterM();
        filterM.setType("global");
        List<FilterM> filterList= chartService.listFilter(filterM);
        model.addAttribute("filterList",filterList);
        FilterForm  filterForm = null;
        if (!model.containsAttribute("filterForm")) {
            filterForm = new FilterForm();
            model.addAttribute("filterForm",
                    filterForm);
        } else {
            filterForm = (FilterForm) model.asMap().get("filterForm");
        }
        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        // logger.info("themeDisplay->"+themeDisplay);

        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
        logger.info("getPpidd ==>" + themeDisplay.getPpid());
        logger.info("getId ==>" + themeDisplay.getPortletDisplay().getId());
        logger.info("getRootPortletId ==>" + themeDisplay.getPortletDisplay().getRootPortletId());
        logger.info("instanceId instanceId==>"+instanceId);
        logger.info("getNamespace ==>" + response.getNamespace());
        FilterInstanceM filterInstanceM=new FilterInstanceM();
        filterInstanceM.setInstanceId(instanceId);

       List<FilterInstanceM> filterInstanceList= chartService.listFilterInstance(filterInstanceM);
        String[] ids=null;
        if(filterInstanceList!=null){
            ids=new String[filterInstanceList.size()];
            int index=0;
            for (FilterInstanceM obj:filterInstanceList){
                ids[index++]=obj.getFilterId()+"";
                logger.info(" ["+index+"]->"+obj.getFilterId());
            }
        }
        filterForm.setIds(ids);
        filterForm.setInstanceId(instanceId);
        model.addAttribute("filterForm", filterForm);
        return "filter/editFilter";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("filterForm") FilterForm filterForm,
                             BindingResult result, Model model) {
        /*PortletPreferences prefs = request.getPreferences();
        String chartType = prefs.getValue("chartType", "");*/
        //logger.info("into do submit instance =>"+chartSettingForm.getChartInstance()+" ,new json "+chartSettingForm.getJsonStr());
        boolean isSave=false;
        FilterInstanceM filterInstanceM= new FilterInstanceM();//chartService.findFilterInstanceById(filterForm.getInstanceId());

            filterInstanceM.setInstanceId(filterForm.getInstanceId());

        String[] ids=filterForm.getIds();
        logger.info("ids=>"+ids);
        if(ids!=null){
            for (int i=0;i<ids.length;i++){
                logger.info("xxx -> "+ids[i]);
            }
        }

        /*
        String jsonStr=chartSettingForm.getAdvProp()+","+chartSettingForm.getDataAdhoc();
        chartInstanceM.setChartJson(jsonStr);
        chartInstanceM.setChartType(chartSettingForm.getChartType());
        chartInstanceM.setChartId(1);
        chartInstanceM.setServiceId(3);
        chartInstanceM.setAdvProp(chartSettingForm.getAdvProp());
        chartInstanceM.setChartHeight(chartSettingForm.getChartHeight());
        chartInstanceM.setDataSourceType(chartSettingForm.getDataSourceType());
        if(chartSettingForm.getComment()!=null){
            CommentM commentM=new CommentM();
            commentM.setInstanceId(chartInstanceM.getInstanceId());
            commentM.setComment(chartSettingForm.getComment());
            chartInstanceM.setComment(commentM);
        }
        logger.info("chartSettingForm.getChartHeight()-->"+chartSettingForm.getChartHeight());
        logger.info("chartSettingForm.getInstanceId()-->"+chartInstanceM.getInstanceId());
        chartInstanceM.setDataAdhoc(chartSettingForm.getDataAdhoc());
        //chartInstanceM.setChartJson(chartSettingForm.getJsonStr());
        */
       // if(isSave)
         //   chartService.saveFilterInstance(filterInstanceM);
        // else
            filterInstanceM.setIds(ids);
            chartService.updateFilterInstance(filterInstanceM);

        try {
            response.setPortletMode(PortletMode.VIEW);
            response.setRenderParameter("action", "list");
        } catch (PortletModeException e) {
            e.printStackTrace();
        }
        // logger.info("chartType -->"+chartSettingForm.getChartType());
    }
}
