
package th.ac.kmutt.chart.rest.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import th.ac.kmutt.chart.dwh.domain.DimField;
import th.ac.kmutt.chart.dwh.domain.InboundOutboundStudent;
import th.ac.kmutt.chart.model.FilterInBoundOutBoundServiceM;
import th.ac.kmutt.chart.model.InBoundOutBoundServiceM;
import th.ac.kmutt.chart.service.ChartService;
import th.ac.kmutt.chart.xstream.common.ImakeResultMessage;
import th.ac.kmutt.chart.xstream.common.Paging;

/**
 * Created by imake on 20/10/2015.
 */
public class FilterInBoundOutBoundResource  extends BaseResource {
   // private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
    @Autowired
    @Qualifier("chartServiceJpaImpl")
    private ChartService chartService;

    @Autowired
    private com.thoughtworks.xstream.XStream xstream;
    @Autowired
    private com.thoughtworks.xstream.XStream jsonXstream;
    public FilterInBoundOutBoundResource() {
        super();
        logger.debug("into constructor TitleResource");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doInit() throws ResourceException {
        // TODO Auto-generated method stub
        super.doInit();
        logger.debug("into doInit");
    }

    @SuppressWarnings("unchecked")
	@Override
    protected Representation post(Representation entity, Variant variant)
            throws ResourceException {
        // TODO Auto-generated method stub
        logger.debug("into Post ConsultantReportResource 2");
        InputStream in = null;
        try {
            in = entity.getStream();
            xstream.processAnnotations(FilterInBoundOutBoundServiceM.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
            FilterInBoundOutBoundServiceM xsource = new FilterInBoundOutBoundServiceM();
            Object xtarget = xstream.fromXML(in);
            if (xtarget != null) { 
                xsource = (FilterInBoundOutBoundServiceM) xtarget;
                if (xsource != null) {
                	DimField domain = new DimField();
                    BeanUtils.copyProperties(xsource, domain);
                    if (xsource.getServiceName() != null && xsource.getServiceName().length() != 0) {
                        String serviceName = xsource.getServiceName();
                        logger.info("\n\n"+serviceName+"\n\n");
                        
                        if (serviceName.equals("InternationalCompareAllStudent")) {                        	
                            Paging page = xsource.getPaging();
                            ArrayList<DimField> domains = (ArrayList<DimField>)
                                    chartService.InternationalCompareAllStudent(xsource);
                            List<DimField> models = new ArrayList<DimField>(domains.size());
                            models = getFilterInboundOutboundStudentModels(domains);
                            ImakeResultMessage imakeMessage = new ImakeResultMessage();
                            imakeMessage.setResultListObj(models);
                            return getRepresentation(entity, imakeMessage, xstream);                      	                     	
                        }
                        else if(serviceName.equals("EmpInternationalCompareAllEmp")) {                        	
                            Paging page = xsource.getPaging();
                            ArrayList<DimField> domains = (ArrayList<DimField>)
                                    chartService.EmpInternationalCompareAllEmp(xsource);
                            List<DimField> models = new ArrayList<DimField>(domains.size());
                            models = getFilterModels(domains);
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

    private List<DimField> getFilterModels(ArrayList<DimField> domains) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<FilterInBoundOutBoundServiceM> getFilterInBoundOutBoundServiceModels(
            ArrayList<DimField> domains) {
        List<FilterInBoundOutBoundServiceM> models = new ArrayList<FilterInBoundOutBoundServiceM>(
                domains.size());
        for (DimField domain : domains) {
        	FilterInBoundOutBoundServiceM model = new FilterInBoundOutBoundServiceM();
            BeanUtils.copyProperties(domain, model);

            model.setFilterCode;(domain.getFieldCode());
            model.setPaging(null);
            models.add(model);
            
        }
        return models;
    }
    
    @Override
    protected Representation get(Variant variant) throws ResourceException {
       
        return null;
    }
}
