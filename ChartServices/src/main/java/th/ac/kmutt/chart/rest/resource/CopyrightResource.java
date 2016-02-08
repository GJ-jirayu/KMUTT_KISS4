package th.ac.kmutt.chart.rest.resource;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import th.ac.kmutt.chart.constant.ServiceConstant;
import th.ac.kmutt.chart.domain.CopyrightServiceEntity;
import th.ac.kmutt.chart.model.ChartM;
import th.ac.kmutt.chart.model.CopyrightServiceM;
import th.ac.kmutt.chart.service.ChartService;
import th.ac.kmutt.chart.xstream.common.ImakeResultMessage;
import th.ac.kmutt.chart.xstream.common.Paging;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imake on 20/10/2015.
 */
public class CopyrightResource  extends BaseResource {
    //private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
    @Autowired
    @Qualifier("chartServiceJpaImpl")
    private ChartService chartService;

    @Autowired
    private com.thoughtworks.xstream.XStream xstream;
    @Autowired
    private com.thoughtworks.xstream.XStream jsonXstream;
    public CopyrightResource() {
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

    @Override
    protected Representation post(Representation entity, Variant variant)
            throws ResourceException {
        // TODO Auto-generated method stub
        logger.debug("into Post ConsultantReportResource 2");
        InputStream in = null;
        try {
            in = entity.getStream();
            xstream.processAnnotations(th.ac.kmutt.chart.model.CopyrightServiceM.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
            th.ac.kmutt.chart.model.CopyrightServiceM xsource = new th.ac.kmutt.chart.model.CopyrightServiceM();
            Object xtarget = xstream.fromXML(in);
            if (xtarget != null) {
                xsource = (th.ac.kmutt.chart.model.CopyrightServiceM) xtarget;
                if (xsource != null) {
                    CopyrightServiceEntity domain = new CopyrightServiceEntity();
                    BeanUtils.copyProperties(xsource, domain);
                    if (xsource.getServiceName() != null
                            && xsource.getServiceName().length() != 0) {
                        String serviceName = xsource.getServiceName();

                        Paging page = xsource.getPaging();
                        @SuppressWarnings("rawtypes")
                        java.util.ArrayList<CopyrightServiceEntity> domains = (java.util.ArrayList<CopyrightServiceEntity>)
                                chartService.listCopyrightServiceEntity(xsource);
                        List<th.ac.kmutt.chart.model.CopyrightServiceM> models = new ArrayList<th.ac.kmutt.chart.model.CopyrightServiceM>();
                        models = getCopyrightServiceModels(domains);
                        ImakeResultMessage imakeMessage = new ImakeResultMessage();
                        imakeMessage.setResultListObj(models);
                        return getRepresentation(entity, imakeMessage, xstream);
                        /*
                        JsonRepresentation representation_aoe = null;


                        jsonXstream
                                .processAnnotations(th.ac.kmutt.chart.model.CopyrightServiceM.class);// or
                        jsonXstream.autodetectAnnotations(true);
                        jsonXstream.setMode(XStream.NO_REFERENCES);
                        // jsonXstream.addImplicitCollection(models.getClass(), "TitleM");;
                        Gson gson = new Gson();
                        String jsonStr= gson.toJson(models);
                        System.out.println("json->" + jsonStr);
                        String json = jsonXstream.toXML(models);
                        System.out.println("json->"+json);

                        representation_aoe = new JsonRepresentation(jsonStr);

                        return representation_aoe;
                        */
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

    private List<th.ac.kmutt.chart.model.CopyrightServiceM> getCopyrightServiceModels(
            java.util.ArrayList<CopyrightServiceEntity> domains) {
        List<th.ac.kmutt.chart.model.CopyrightServiceM> models = new ArrayList<th.ac.kmutt.chart.model.CopyrightServiceM>(
                domains.size());
        for (CopyrightServiceEntity domain : domains) {
            th.ac.kmutt.chart.model.CopyrightServiceM model = new th.ac.kmutt.chart.model.CopyrightServiceM();
            BeanUtils.copyProperties(domain, model);
            model.setPaging(null);
            models.add(model);
        }
        return models;
    }
    @Override
    protected Representation get(Variant variant) throws ResourceException {
        // TODO Auto-generated method stub
        CopyrightServiceM copyrightServiceM=new CopyrightServiceM();
        //CopyrightServiceM copyrightServiceM=new CopyrightServiceM();
        copyrightServiceM.setType(3);
        copyrightServiceM.setYear(2558);
        copyrightServiceM.setMonth(1);
        copyrightServiceM.setMonthDesc("Jan");
        copyrightServiceM.setValue(1000);
        chartService.saveCopyrightServiceEntity(copyrightServiceM);

        return null;
    }
}
