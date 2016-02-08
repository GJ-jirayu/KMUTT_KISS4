package th.ac.kmutt.chart.rest.resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import th.ac.kmutt.chart.constant.ServiceConstant;
import th.ac.kmutt.chart.domain.*;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;
import th.ac.kmutt.chart.xstream.common.ImakeResultMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imake on 20/10/2015.
 */
public class FilterInstanceResource  extends BaseResource {
//    private static final Logger logger = Logger.getLogger("MISSConsultLog");

    @Autowired
    @Qualifier("chartServiceJpaImpl")
    private ChartService chartService;

    @Autowired
    private com.thoughtworks.xstream.XStream xstream;
    @Autowired
    private com.thoughtworks.xstream.XStream jsonXstream;

    public FilterInstanceResource() {
        super();
        logger.debug("into constructor FilterInstanceEntity");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doInit() throws ResourceException {
        // TODO Auto-generated method stub
        super.doInit();
        logger.debug("into doInit");
    }

    @Override
    protected Representation post(Representation entity, Variant variant)
            throws ResourceException {
        // TODO Auto-generated method stub
        logger.debug("into Post ConsultantReportResource 2");
        InputStream in = null;
        try {
            in = entity.getStream();
            xstream.processAnnotations(FilterInstanceM.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
            FilterInstanceM xsource = new FilterInstanceM();
            Object xtarget = xstream.fromXML(in);
            if (xtarget != null) {
                xsource = (FilterInstanceM) xtarget;
                if (xsource != null) {
                    FilterInstanceEntity domain = new FilterInstanceEntity();
                    BeanUtils.copyProperties(xsource, domain);

                    FilterInstanceEntityPK pk = new FilterInstanceEntityPK();
                    if (xsource.getFilterId() != null)
                        pk.setFilterId(xsource.getFilterId());
                    if (xsource.getInstanceId() != null)
                        pk.setInstanceId(xsource.getInstanceId());
                    domain.setId(pk);


                    if (xsource.getServiceName() != null
                            && xsource.getServiceName().length() != 0) {
                        String serviceName = xsource.getServiceName();
                        if (serviceName.equals(ServiceConstant.FILTER_INSTANCE_FIND_BY_ID)) {
                            domain = chartService.findFilterInstanceEntityById(xsource.getInstanceId());
                            ImakeResultMessage imakeMessage = new ImakeResultMessage();
                            if (domain != null) {
                                List<FilterInstanceM> models = new ArrayList<FilterInstanceM>(1);

                                java.util.ArrayList<FilterInstanceEntity> domains =new ArrayList<FilterInstanceEntity>(1);
                                domains.add(domain);
                                // get Model List
                                models=getFilterInstanceModels(domains);

                                imakeMessage.setResultListObj(models);
                            }
                            return getRepresentation(entity, imakeMessage, xstream);
                        } else if (serviceName.equals(ServiceConstant.FILTER_INSTANCE_SAVE)) {
                            //java.sql.Timestamp now = new java.sql.Timestamp(new Date().getTime());
                            // domain.setCreatedDate(now);
                            //domain.setUpdatedDate(now);
                            int updateRecord = chartService.saveFilterInstanceEntity(domain);
                            return returnUpdateRecord(entity, xsource, updateRecord);
                        } else if (serviceName.equals(ServiceConstant.FILTER_INSTANCE_UPDATE)) {
                            //java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
                            //domain.setUpdatedDate(updatedDate);
                            int updateRecord=0;
                            // int updateRecord = chartService.updateChartFilterInstanceEntity(domain);
                            logger.info("deleteFilterInstanceEntity->"+domain.getId().getInstanceId());
                            chartService.deleteFilterInstanceEntity(domain);
                            String[] ids=xsource.getIds();
                            for (int i=0;i<ids.length;i++){
                                FilterInstanceEntity domain_inner = new FilterInstanceEntity();
                                BeanUtils.copyProperties(xsource, domain_inner);

                                FilterInstanceEntityPK pk_inner = new FilterInstanceEntityPK();
                                logger.info("xsource.getInstanceId->"+xsource.getInstanceId());
                                if (xsource.getInstanceId() != null)
                                    pk_inner.setInstanceId(xsource.getInstanceId());
                                //if (xsource.getFilterId() != null)
                                pk_inner.setFilterId(Integer.valueOf(ids[i]));
                                domain_inner.setId(pk_inner);
                                updateRecord = chartService.saveFilterInstanceEntity(domain_inner);
                            }
                            return returnUpdateRecord(entity, xsource, updateRecord);

                        } else if (serviceName.equals(ServiceConstant.FILTER_INSTANCE_ITEMS_DELETE)) {

                        } else if (serviceName.equals(ServiceConstant.FILTER_INSTANCE_DELETE)) {
                            int updateRecord = 0;
                            try {
                                chartService.deleteFilterInstanceEntity(domain);
                            } catch (Exception e) {
                                Throwable t = e.getCause();

                                while ((t != null) && !(t instanceof ConstraintViolationException)) {
                                    t = t.getCause();
                                }

                                if (t instanceof ConstraintViolationException) {
                                    updateRecord = -9;
                                }
                            }
                            return returnUpdateRecord(entity, xsource, updateRecord);
                        } else if (serviceName.equals(ServiceConstant.FILTER_INSTANCE_SEARCH)) {

                             @SuppressWarnings("rawtypes")
                             java.util.ArrayList<FilterInstanceEntity> domains = (java.util.ArrayList<FilterInstanceEntity>)
                                     chartService.listFilterInstanceEntity(xsource);
                            List<FilterInstanceM> models = new ArrayList<FilterInstanceM>(domains.size());
                            models = getFilterInstanceModels(domains);
                            ImakeResultMessage imakeMessage = new ImakeResultMessage();
                            imakeMessage.setResultListObj(models);
                            return getRepresentation(entity, imakeMessage, xstream);

                        }

                    } else {
                    }
                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            logger.debug(" into Finally Call");
            try {
                if (in != null)
                    in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private List<FilterInstanceM> getFilterInstanceModels(
            java.util.ArrayList<FilterInstanceEntity> domains) {
        List<FilterInstanceM> models = new ArrayList<FilterInstanceM>(
                domains.size());
        for (FilterInstanceEntity domain : domains) {
            FilterInstanceM model = new FilterInstanceM();
            BeanUtils.copyProperties(domain, model);
            FilterInstanceEntityPK pk =domain.getId();
            if(pk!=null){
                model.setFilterId(pk.getFilterId());
                model.setInstanceId(pk.getInstanceId());
            }
            // set FilterByFilterM
            if (domain.getFilterByFilterId() != null) {
                FilterM filterM= new FilterM();
                BeanUtils.copyProperties(domain.getFilterByFilterId(), filterM);
                filterM.setPaging(null);
                model.setFilterM(filterM);
            }
            model.setPaging(null);
            models.add(model);;
        }
        return models;
    }

    private Representation returnUpdateRecord(Representation entity, FilterInstanceM model, int updateRecord) {
        ImakeResultMessage imakeMessage = new ImakeResultMessage();
        List<FilterInstanceM> xsources = new ArrayList<FilterInstanceM>(1);
        model.setUpdateRecord(updateRecord);
        xsources.add(model);
        imakeMessage.setResultListObj(xsources);
        return getRepresentation(entity, imakeMessage, xstream);
    }

    @Override
    protected Representation get(Variant variant) throws ResourceException {
        // TODO Auto-generated method stub
        return null;
    }

}
