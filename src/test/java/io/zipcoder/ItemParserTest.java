package io.zipcoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemParserTest {

    private String rawSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawSingleItemIrregularSeperatorSample = "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";

    private String rawBrokenSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                                      +"naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                                      +"NAMe:;price:1.23;type:Food;expiration:2/25/2016##" +
            "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##"+"naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##";
    private String toCheckPriceArray = "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##"+"naMe:CoOkieS;price:2.30;type:Food*expiration:1/25/2016##";
    private ItemParser itemParser;

    @Before
    public void setUp(){
        itemParser = new ItemParser();
    }

    @Test
    public void parseRawDataIntoStringArrayTest(){
        Integer expectedArraySize = 3;
        ArrayList<String> items = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        Integer actualArraySize = items.size();
        assertEquals(expectedArraySize, actualArraySize);
    }

    @Test
    public void parseStringIntoItemTest() throws ItemParseException{
        Item expected = new Item("milk", 3.23, "food","1/25/2016");
        Item actual = itemParser.parseStringIntoItem(rawSingleItem);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = ItemParseException.class)
    public void parseBrokenStringIntoItemTest() throws ItemParseException{
        itemParser.parseStringIntoItem(rawBrokenSingleItem);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTest(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItem).size();
        assertEquals(expected, actual);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTestIrregular(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItemIrregularSeperatorSample).size();
        assertEquals(expected, actual);
    }@Test
    public void toGetArrayListOfItemTest(){
        ArrayList<String> seperatedString = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        Integer expected = 2;
        Integer actual = itemParser.toGetArrayListOfItem(seperatedString).size();
        assertEquals(expected,actual);
    }@Test
    public  void  toGetEachItemCountTest(){
        Integer expected = 3;
        ArrayList<Item> itemArrayList = itemParser.toGetArrayListOfItem
                (itemParser.parseRawDataIntoStringArray(rawMultipleItems));
        Map<String,ArrayList<Double>> map = itemParser.toGetEachItemCount(itemArrayList);
        Integer actual = map.size();
        assertEquals(expected,actual);
    }
    @Test
    public  void getCountTest(){
        ArrayList<Item> itemArrayList = itemParser.toGetArrayListOfItem
                (itemParser.parseRawDataIntoStringArray(rawMultipleItems));
        int expected = 1;
        assertEquals(expected,itemParser.getCount());
    }
    @Test
    public void toCountPriceTest(){
        ArrayList<Double> doubles = new ArrayList<>();
        ArrayList<Item> itemArrayList = itemParser.toGetArrayListOfItem
                (itemParser.parseRawDataIntoStringArray(toCheckPriceArray));
        Map<String,ArrayList<Double>> map = itemParser.toGetEachItemCount(itemArrayList);
        doubles = map.get("cookies");

        System.out.println(itemParser.toCountPrice(doubles));

    }

}
