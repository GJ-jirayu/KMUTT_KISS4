package th.ac.kmutt.chart.service.impl;

import org.springframework.stereotype.Service;
import th.ac.kmutt.chart.constant.ServiceConstant;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;
import th.ac.kmutt.chart.xstream.common.ImakeResultMessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Service("chartServiceWSImpl")
public class ChartServiceWSImpl extends PostCommon implements ChartService {

    @Override
    public List listService(ServiceM param) {
        param.setServiceName(ServiceConstant.SERVICE_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "service", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    private static SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    public List listChart() {
        return null;
    }

    @Override
    public List listCopyrightService(CopyrightServiceM param) {
        param.setServiceName(ServiceConstant.COPYRIGHT_SERVICE_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "copyright", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    @Override
    public List listJournalPapersService(JournalPapersServiceM param) {
        param.setServiceName(ServiceConstant.JOURNAL_SERVICE_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "journalPapers", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    @Override
    public List listFundingResourceService(FundingResourceServiceM param) {
        param.setServiceName(ServiceConstant.FUNDING_SERVICE_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "funding", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    @Override
    public Integer saveChart(ChartM model){
        model.setServiceName(ServiceConstant.CHART_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chart", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }
    @Override
    public Integer updateChart(ChartM model){
        model.setServiceName(ServiceConstant.CHART_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chart", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteChart(ChartM model){
        // TODO Auto-generated method stub
        model.setServiceName(ServiceConstant.CHART_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chart", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ChartM findChartById(Integer chartId) {
        // TODO Auto-generated method stub
        ChartM model = new ChartM();
        model.setChartId(chartId);
        model.setServiceName(ServiceConstant.CHART_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chart", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ChartM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }
    @Override
    public List listChart(ChartM param) {
        param.setServiceName(ServiceConstant.CHART_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "chart", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    // chart feature
    @Override
    public Integer saveChartFeature(ChartFeatureM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeature",true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateChartFeature(ChartFeatureM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteChartFeature(ChartFeatureM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ChartFeatureM findChartFeatureById(Integer chartId) {
        ChartFeatureM model = new ChartFeatureM();
        model.setChartId(chartId);
        model.setServiceName(ServiceConstant.CHART_FEATURE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ChartFeatureM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Chart Feature Instance
    @Override
    public Integer saveChartFeatureInstance(ChartFeatureInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_INSTANCE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeatureinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateChartFeatureInstance(ChartFeatureInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_INSTANCE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeatureinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteChartFeatureInstance(ChartFeatureInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_INSTANCE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeatureinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ChartFeatureInstanceM findChartFeatureInstanceById(String instanceId) {
        ChartFeatureInstanceM model = new ChartFeatureInstanceM();
        model.setInstanceId(instanceId);
        model.setServiceName(ServiceConstant.CHART_FEATURE_INSTANCE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeatureinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ChartFeatureInstanceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Chart Feature Mapping
    @Override
    public Integer saveChartFeatureMapping(ChartFeatureMappingM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_MAPPING_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeaturemapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateChartFeatureMapping(ChartFeatureMappingM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_MAPPING_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeaturemapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteChartFeatureMapping(ChartFeatureMappingM model) {
        model.setServiceName(ServiceConstant.CHART_FEATURE_MAPPING_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeaturemapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFeatureMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ChartFeatureMappingM findChartFeatureMappingById(Integer featureId, Integer chartId) {
        ChartFeatureMappingM model = new ChartFeatureMappingM();
        model.setChartId(chartId);
        model.setFeatureId(featureId);
        model.setServiceName(ServiceConstant.CHART_FEATURE_MAPPING_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfeaturemapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ChartFeatureMappingM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Chart Filter Instance
    @Override
    public Integer saveChartFilterInstance(ChartFilterInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_FILTER_INSTANCE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfilterinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFilterInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateChartFilterInstance(ChartFilterInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_FILTER_INSTANCE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfilterinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFilterInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteChartFilterInstance(ChartFilterInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_FILTER_INSTANCE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfilterinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartFilterInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ChartFilterInstanceM findChartFilterInstanceById(String instanceId) {
        ChartFilterInstanceM model = new ChartFilterInstanceM();
        model.setInstanceId(instanceId);
        model.setServiceName(ServiceConstant.CHART_FILTER_INSTANCE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartfilterinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ChartFilterInstanceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }
    @Override
    public List listChartFilterInstance(ChartFilterInstanceM param) {
        param.setServiceName(ServiceConstant.CHART_FILTER_INSTANCE_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "chartfilterinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    // Chart Instance
    @Override
    public Integer saveChartInstance(ChartInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_INSTANCE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateChartInstance(ChartInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_INSTANCE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteChartInstance(ChartInstanceM model) {
        model.setServiceName(ServiceConstant.CHART_INSTANCE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ChartInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ChartInstanceM findChartInstanceById(String instanceId) {
        ChartInstanceM model = new ChartInstanceM();
        model.setInstanceId(instanceId);
        model.setServiceName(ServiceConstant.CHART_INSTANCE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "chartinstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ChartInstanceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Comment
    @Override
    public Integer saveComment(CommentM model) {
        model.setServiceName(ServiceConstant.COMMENT_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "comment", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((CommentM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateComment(CommentM model) {
        model.setServiceName(ServiceConstant.COMMENT_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "comment", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((CommentM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteComment(CommentM model) {
        model.setServiceName(ServiceConstant.COMMENT_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "comment", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((CommentM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public CommentM findCommentById(String instanceId) {
        CommentM model = new CommentM();
        model.setInstanceId(instanceId);
        model.setServiceName(ServiceConstant.COMMENT_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "comment", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (CommentM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Copyright Service
    @Override
    public Integer saveCopyrightService(CopyrightServiceM model) {
        model.setServiceName(ServiceConstant.COPYRIGHT_SERVICE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "copyright", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((CopyrightServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateCopyrightService(CopyrightServiceM model) {
        model.setServiceName(ServiceConstant.COPYRIGHT_SERVICE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "copyright", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((CopyrightServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteCopyrightService(CopyrightServiceM model) {
        model.setServiceName(ServiceConstant.COPYRIGHT_SERVICE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "copyright", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((CopyrightServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public CopyrightServiceM findCopyrightServiceById(Integer type, Integer year, Integer month) {
        CopyrightServiceM model = new CopyrightServiceM();
        model.setType(type);
        model.setYear(year);
        model.setMonth(month);
        model.setServiceName(ServiceConstant.COPYRIGHT_SERVICE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "copyright", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (CopyrightServiceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Feature
    @Override
    public Integer saveFeature(FeatureM model) {
        model.setServiceName(ServiceConstant.FEATURE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "feature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FeatureM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateFeature(FeatureM model) {
        model.setServiceName(ServiceConstant.FEATURE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "feature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FeatureM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteFeature(FeatureM model) {
        model.setServiceName(ServiceConstant.FEATURE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "feature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FeatureM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public FeatureM findFeatureById(Integer featureId) {
        FeatureM model = new FeatureM();
        model.setFeatureId(featureId);
        model.setServiceName(ServiceConstant.FEATURE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "feature", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (FeatureM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Filter
    @Override
    public Integer saveFilter(FilterM model) {
        model.setServiceName(ServiceConstant.FILTER_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filter", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FilterM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateFilter(FilterM model) {
        model.setServiceName(ServiceConstant.FILTER_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filter", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FilterM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteFilter(FilterM model) {
        model.setServiceName(ServiceConstant.FILTER_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filter", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FilterM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public FilterM findFilterById(Integer filterId) {
        FilterM model = new FilterM();
        model.setFilterId(filterId);
        model.setServiceName(ServiceConstant.FILTER_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filter", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (FilterM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    @Override
    public List listFilter(FilterM param) {
        param.setServiceName(ServiceConstant.FILTER_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "filter", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    // Filter Instance
    @Override
    public Integer saveFilterInstance(FilterInstanceM model) {
        model.setServiceName(ServiceConstant.FILTER_INSTANCE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filterInstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FilterInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateFilterInstance(FilterInstanceM model) {
        model.setServiceName(ServiceConstant.FILTER_INSTANCE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filterInstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FilterInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteFilterInstance(FilterInstanceM model) {
        model.setServiceName(ServiceConstant.FILTER_INSTANCE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filterInstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FilterInstanceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public FilterInstanceM findFilterInstanceById(String instanceId) {
        FilterInstanceM model = new FilterInstanceM();
        model.setInstanceId(instanceId);
        model.setServiceName(ServiceConstant.FILTER_INSTANCE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "filterInstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (FilterInstanceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    @Override
    public List listFilterInstance(FilterInstanceM param) {
        param.setServiceName(ServiceConstant.FILTER_INSTANCE_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "filterInstance", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

    // Funding
    @Override
    public Integer saveFundingResourceService(FundingResourceServiceM model) {
        model.setServiceName(ServiceConstant.FUNDING_SERVICE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "funding", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FundingResourceServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateFundingResourceService(FundingResourceServiceM model) {
        model.setServiceName(ServiceConstant.FUNDING_SERVICE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "funding", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FundingResourceServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteFundingResourceService(FundingResourceServiceM model) {
        model.setServiceName(ServiceConstant.FUNDING_SERVICE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "funding", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((FundingResourceServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public FundingResourceServiceM findFundingResourceServiceById(Integer type, Integer year) {
        FundingResourceServiceM model = new FundingResourceServiceM();
        model.setType(type);
        model.setYear(year);
        model.setServiceName(ServiceConstant.FUNDING_SERVICE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "funding", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (FundingResourceServiceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Journal
    @Override
    public Integer saveJournalPapersService(JournalPapersServiceM model) {
        model.setServiceName(ServiceConstant.JOURNAL_SERVICE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "journalPapers", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((JournalPapersServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateJournalPapersService(JournalPapersServiceM model) {
        model.setServiceName(ServiceConstant.JOURNAL_SERVICE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "journalPapers", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((JournalPapersServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteJournalPapersService(JournalPapersServiceM model) {
        model.setServiceName(ServiceConstant.JOURNAL_SERVICE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "journalPapers", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((JournalPapersServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public JournalPapersServiceM findJournalPapersServiceById(Integer type, Integer year) {
        JournalPapersServiceM model = new JournalPapersServiceM();
        model.setType(type);
        model.setYear(year);
        model.setServiceName(ServiceConstant.JOURNAL_SERVICE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "journalPapers", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (JournalPapersServiceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Service Chart Mapping
    @Override
    public Integer saveServiceChartMapping(ServiceChartMappingM model) {
        model.setServiceName(ServiceConstant.SERVICE_CHART_MAPPING_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicechartmapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceChartMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateServiceChartMapping(ServiceChartMappingM model) {
        model.setServiceName(ServiceConstant.SERVICE_CHART_MAPPING_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicechartmapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceChartMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteServiceChartMapping(ServiceChartMappingM model) {
        model.setServiceName(ServiceConstant.SERVICE_CHART_MAPPING_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicechartmapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceChartMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ServiceChartMappingM findServiceChartMappingById(Integer serviceId, Integer chartId) {
        ServiceChartMappingM model = new ServiceChartMappingM();
        model.setChartId(chartId);
        model.setServiceName(ServiceConstant.SERVICE_CHART_MAPPING_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicechartmapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ServiceChartMappingM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Service
    @Override
    public Integer saveService(ServiceM model) {
        model.setServiceName(ServiceConstant.SERVICE_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "service", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateService(ServiceM model) {
        model.setServiceName(ServiceConstant.SERVICE_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "service", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteService(ServiceM model) {
        model.setServiceName(ServiceConstant.SERVICE_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "service", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ServiceM findServiceById(Integer serviceId) {
        ServiceM model = new ServiceM();
        model.setServiceId(serviceId);
        model.setServiceName(ServiceConstant.SERVICE_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "service", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ServiceM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    // Service Filter Mapping
    @Override
    public Integer saveServiceFilterMapping(ServiceFilterMappingM model) {
        model.setServiceName(ServiceConstant.SERVICE_FILTER_MAPPING_SAVE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicefiltermapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceFilterMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer updateServiceFilterMapping(ServiceFilterMappingM model) {
        model.setServiceName(ServiceConstant.SERVICE_FILTER_MAPPING_UPDATE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicefiltermapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceFilterMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public Integer deleteServiceFilterMapping(ServiceFilterMappingM model) {
        model.setServiceName(ServiceConstant.SERVICE_FILTER_MAPPING_DELETE);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicefiltermapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return ((ServiceFilterMappingM) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
        else
            return null;
    }

    @Override
    public ServiceFilterMappingM findServiceFilterMappingById(Integer serviceId, Integer filterId) {
        ServiceFilterMappingM model = new ServiceFilterMappingM();
        model.setServiceId(serviceId);
        model.setFilterId(filterId);
        model.setServiceName(ServiceConstant.SERVICE_FILTER_MAPPING_FIND_BY_ID);
        ImakeResultMessage imakeMessage = postMessage(model, model.getClass().getName(), "servicefiltermapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return (ServiceFilterMappingM) imakeMessage.getResultListObj().get(0);
        else
            return null;
    }

    @Override
    public List listServiceFilterMapping(ServiceFilterMappingM param) {
        param.setServiceName(ServiceConstant.SERVICE_FILTER_MAPPING_SEARCH);
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "servicefiltermapping", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
    }

	@Override
	public List listInboundOutbound(InBoundOutBoundServiceM param) {
		param.setServiceName("N");
        ImakeResultMessage imakeMessage = postMessage(param, param.getClass().getName(), "aew", true);
        if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
            return  imakeMessage.getResultListObj();
        else
            return null;
	}
    
    
}
