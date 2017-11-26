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
        ArrayList<String> seperatedString = itemParser.parseRawDataIntoStringArray(output);
        ArrayList<Item> itemArrayList = itemParser.toGetArrayListOfItem(seperatedString);
        Map<String,ArrayList<Double>> map = itemParser.toGetEachItemCount(itemArrayList);
        itemParser.finalOutput(map);

        // TODO: parse the data in output into items, and display to console.
    }
}
