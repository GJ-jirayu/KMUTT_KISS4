package th.co.imake.chart.services.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import th.ac.kmutt.chart.fusion.gson.*;
import th.co.imake.chart.services.MultiSeriesColumn2D;
import th.co.imake.chart.services.RealTimeAngular;

import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
@Service("angulargauge")
public class RealTimeAngularImpl implements RealTimeAngular {
    @Override
    public String getDials(List<Dial> dials) {
        Gson gson =new Gson();
        DialsFusion dialsFusion =new DialsFusion();
        dialsFusion.setDial(dials);

        return gson.toJson(dialsFusion);
    }

    @Override
    public String getAnnotations(List<GroupsFusion> groupsFusions) {
        Gson gson =new Gson();
        AnnotationsFusion annotationsFusion =new AnnotationsFusion();
        annotationsFusion.setGroups(groupsFusions);

        return gson.toJson(annotationsFusion);
    }
}
