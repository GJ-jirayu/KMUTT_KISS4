import junit.framework.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import th.ac.kmutt.chart.fusion.gson.Dial;
import th.ac.kmutt.chart.fusion.gson.GroupsFusion;
import th.ac.kmutt.chart.fusion.gson.Item;
import th.co.imake.chart.services.RealTimeAngular;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imake on 03/02/2016.
 */
public class RealTimeAngularTest {
    private ClassPathXmlApplicationContext springContext;
    private RealTimeAngular realTimeAngular;
    @BeforeTest
    public void setUp() {
        System.out.println("setUp");
        springContext = new ClassPathXmlApplicationContext(
                new String[] {
                        "config/applicationContext-converter.xml"});
        realTimeAngular =(RealTimeAngular)springContext.getBean("angulargauge");
    }
    @AfterTest
    public void tearDown() {
        System.out.println("tearDownp");
        springContext.close();
    }
    @Test
    public void testDials() {
        List<Dial> dials=new ArrayList<Dial>(2);
        Dial dial1=new Dial();
        dial1.setValue("65");
        dial1.setBgcolor("000000");
        dial1.setBordercolor("#FFFFFF");
        dial1.setBorderalpha("100");

        //Dial dial2=new Dial();

        dials.add(dial1);
        System.out.println(realTimeAngular.getDials(dials));
    }
    @Test
    public void testAnnotations() {

        List<GroupsFusion> groupsFusions=new ArrayList<GroupsFusion>(2);
        GroupsFusion groupsFusion1=new GroupsFusion();
        groupsFusion1.setLabel_x("160");
        groupsFusion1.setLabel_y("160");

        List<Item> items=new ArrayList<Item>(2);
        Item item1=new Item();
        item1.setType("circle");
        item1.setRadius("130");
        Item item2 =new Item();
        item2.setType("circle");
        item2.setFillcolor("#ffffff,#000000");

        items.add(item1);
        items.add(item2);
        groupsFusion1.setItems(items);

        GroupsFusion groupsFusion2=new GroupsFusion();
        groupsFusion2.setScaletext("0");
        groupsFusion2.setScaletext("1");

        groupsFusions.add(groupsFusion1);
        groupsFusions.add(groupsFusion2);
        System.out.println(realTimeAngular.getAnnotations(groupsFusions));

    }

}
