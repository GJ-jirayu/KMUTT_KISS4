package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by imake on 03/02/2016.
 */
public class Item   implements Serializable {

    private String type;
    @SerializedName("x")
    private String label_x;
    @SerializedName("y")
    private String label_y;
    private String radius;
    private String showborder;
    private String bordercolor;
    private String fillasgradient;
    private String fillcolor;
    private String fillalpha;
    private String fillratio;
    private String label;
    private String fontcolor;
    private String fontsize;
    private String bold;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel_x() {
        return label_x;
    }

    public void setLabel_x(String label_x) {
        this.label_x = label_x;
    }

    public String getLabel_y() {
        return label_y;
    }

    public void setLabel_y(String label_y) {
        this.label_y = label_y;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getShowborder() {
        return showborder;
    }

    public void setShowborder(String showborder) {
        this.showborder = showborder;
    }

    public String getBordercolor() {
        return bordercolor;
    }

    public void setBordercolor(String bordercolor) {
        this.bordercolor = bordercolor;
    }

    public String getFillasgradient() {
        return fillasgradient;
    }

    public void setFillasgradient(String fillasgradient) {
        this.fillasgradient = fillasgradient;
    }

    public String getFillcolor() {
        return fillcolor;
    }

    public void setFillcolor(String fillcolor) {
        this.fillcolor = fillcolor;
    }

    public String getFillalpha() {
        return fillalpha;
    }

    public void setFillalpha(String fillalpha) {
        this.fillalpha = fillalpha;
    }

    public String getFillratio() {
        return fillratio;
    }

    public void setFillratio(String fillratio) {
        this.fillratio = fillratio;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFontcolor() {
        return fontcolor;
    }

    public void setFontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
    }

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public String getBold() {
        return bold;
    }

    public void setBold(String bold) {
        this.bold = bold;
    }
}
