package th.ac.kmutt.chart.fusion.gson;

import java.io.Serializable;

/**
 * Created by imake on 03/02/2016.
 */
public class Dial implements Serializable {
    private String value;
    private String bgcolor;
    private String bordercolor;
    private String borderalpha;
    private String basewidth;
    private String topwidth;
    private String borderthickness;
    private String valuey;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBordercolor() {
        return bordercolor;
    }

    public void setBordercolor(String bordercolor) {
        this.bordercolor = bordercolor;
    }

    public String getBorderalpha() {
        return borderalpha;
    }

    public void setBorderalpha(String borderalpha) {
        this.borderalpha = borderalpha;
    }

    public String getBasewidth() {
        return basewidth;
    }

    public void setBasewidth(String basewidth) {
        this.basewidth = basewidth;
    }

    public String getTopwidth() {
        return topwidth;
    }

    public void setTopwidth(String topwidth) {
        this.topwidth = topwidth;
    }

    public String getBorderthickness() {
        return borderthickness;
    }

    public void setBorderthickness(String borderthickness) {
        this.borderthickness = borderthickness;
    }

    public String getValuey() {
        return valuey;
    }

    public void setValuey(String valuey) {
        this.valuey = valuey;
    }
}
