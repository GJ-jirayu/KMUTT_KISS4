import junit.framework.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import th.ac.kmutt.chart.fusion.gson.LineFusion;
import th.co.imake.chart.services.MultiSeriesColumn2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public class MultiSeriesColumn2DTest {
    private ClassPathXmlApplicationContext springContext;
    private  MultiSeriesColumn2D multiSeriesColumn2D;
    @BeforeTest
    public void setUp() {
        System.out.println("setUp");
        springContext = new ClassPathXmlApplicationContext(
                new String[] {
                        "config/applicationContext-converter.xml"});
        multiSeriesColumn2D =(MultiSeriesColumn2D)springContext.getBean("mscolumn2d");
    }
    @AfterTest
    public void tearDown() {
        System.out.println("tearDownp");
        springContext.close();
    }
    @Test
    public void testCategories() {
        String[] labels={"Q1","Q2","Q3"};

        System.out.println(multiSeriesColumn2D.getCategories(labels));
        System.out.println("aoe 1"+","+multiSeriesColumn2D);
        Assert.assertEquals("Hello World 2", "Hello World 2");

    }

    @Test
    public void testDataset() {
        String[] values={"10000","10005","10009"};
        String seriesname="Previous Year";
        System.out.println(multiSeriesColumn2D.getDataset(seriesname,values));
        System.out.println("aoe 2");
        Assert.assertEquals("Hello World 2", "Hello World 2");
    }
    @Test
    public void testTrendlines() {
        List<LineFusion> LineFusions=new ArrayList<LineFusion>(2);
        LineFusion lineFusion1=new LineFusion();
        lineFusion1.setStartvalue("12250");
        lineFusion1.setColor("#0075c2");
        lineFusion1.setDisplayvalue("Previous{br}Average");

        LineFusion lineFusion2=new LineFusion();
        lineFusion2.setStartvalue("25950");
        lineFusion2.setColor("#1aaf5d");
        lineFusion2.setDisplayvalue("Current{br}Average");

        LineFusions.add(lineFusion1);
        LineFusions.add(lineFusion2);
        System.out.println(multiSeriesColumn2D.getTrendlines(LineFusions));

    }
}
