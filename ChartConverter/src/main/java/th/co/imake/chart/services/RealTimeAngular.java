package th.co.imake.chart.services;

import th.ac.kmutt.chart.fusion.gson.Dial;
import th.ac.kmutt.chart.fusion.gson.GroupsFusion;

import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public interface RealTimeAngular {
    public String getDials(List<Dial> dials);
    public String getAnnotations(List<GroupsFusion> groupsFusions);
}
