package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;

    }

    public static void main(String[] args) throws Exception{
        final Logger logger = Logger.getGlobal();
        ItemParser itemParser = new ItemParser();
        String output = (new Main()).readRawDataToString();
        System.out.println(output);
        int errorCount;
        String str = null;
        int itemCount = 0;
        Map<Double,Integer> priceMap = null;
        ArrayList<String> seperatedString = itemParser.parseRawDataIntoStringArray(output);
        ArrayList<Item> itemArrayList = itemParser.toGetArrayListOfItem(seperatedString);
        Map<String,ArrayList<Double>> map = itemParser.toGetEachItemCount(itemArrayList);
        for (Map.Entry<String,ArrayList<Double>> entry:map.entrySet()) {
            String key = entry.getKey().substring(0,1).toUpperCase()+entry.getKey().substring(1);
            ArrayList<Double> value = entry.getValue();
            priceMap = itemParser.toCountPrice(value);
            itemCount = value.size();
            System.out.println("Name : "+key+"        seen "+itemCount);
            for (Map.Entry<Double,Integer> entry1: priceMap.entrySet()) {
                Double priceKey = entry1.getKey();
                Integer priceCount = entry1.getValue();
                System.out.println("Price : "+priceKey+"        seen "+priceCount);
            }
        }
        System.out.println(itemParser.getCount());
        // TODO: parse the data in output into items, and display to console.
    }
}
