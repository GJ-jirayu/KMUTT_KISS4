package th.ac.kmutt.chart.fusion.gson;

import java.io.Serializable;

/**
 * Created by imake on 03/02/2016.
 */
public class LineFusion implements Serializable {
    private String startvalue;
    private String color;
    private String displayvalue;
    private String valueOnRight;
    private String thickness;
    private String showBelow;
    private String tooltext;

    public String getStartvalue() {
        return startvalue;
    }

    public void setStartvalue(String startvalue) {
        this.startvalue = startvalue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDisplayvalue() {
        return displayvalue;
    }

    public void setDisplayvalue(String displayvalue) {
        this.displayvalue = displayvalue;
    }

    public String getValueOnRight() {
        return valueOnRight;
    }

    public void setValueOnRight(String valueOnRight) {
        this.valueOnRight = valueOnRight;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getShowBelow() {
        return showBelow;
    }

    public void setShowBelow(String showBelow) {
        this.showBelow = showBelow;
    }

    public String getTooltext() {
        return tooltext;
    }

    public void setTooltext(String tooltext) {
        this.tooltext = tooltext;
    }
}
