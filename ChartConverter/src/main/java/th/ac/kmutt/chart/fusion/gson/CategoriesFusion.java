package th.ac.kmutt.chart.fusion.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imake on 21/10/2015.
 */
public class CategoriesFusion implements Serializable{
    @SerializedName("category")
    private List<Category> category;

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
        }
}
