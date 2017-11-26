package io.zipcoder;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemParser {
    public int count;
    public int getCount(){
        return count;
    }
    private static final Logger logger = Logger.getGlobal();

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) {

        try {
            //System.out.println(rawItem);
            String[] strings = rawItem.split(",");
            Item item = new Item(strings[0],Double.parseDouble(strings[1]),strings[2],strings[3]);
            return item;
        }catch (ArrayIndexOutOfBoundsException e){
            String msg="Not a valid Item";
            logger.log(Level.SEVERE,msg);
            count++;
            return null;
        }
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^|@|!|%|*]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    public ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }
    public ArrayList<Item> toGetArrayListOfItem(ArrayList<String> seperatedString){
        ItemParser itemParser = new ItemParser();
        ArrayList<ArrayList<String>> commaSeparatedList = new ArrayList<>();
        StringBuilder itemStr = new StringBuilder();
        ArrayList<Item> itemArrayList = new ArrayList<>();
        for (String str:seperatedString) {
            commaSeparatedList.add(itemParser.findKeyValuePairsInRawItemData(str));
        }
        for (int i=0;i<commaSeparatedList.size();i++){
            try {
                for(int j=0;j<commaSeparatedList.get(i).size();j++){
                    String[] strings = commaSeparatedList.get(i).get(j).split(":");
                    itemStr.append(strings[1]).append(",");
                }
            }catch (ArrayIndexOutOfBoundsException e){
                // String message = "not a valid string";
                //logger.log(Level.SEVERE,message);
                count++;
            }


            if(itemParser.parseStringIntoItem(itemStr.toString()) != null){
                itemArrayList.add(itemParser.parseStringIntoItem(itemStr.toString()));
            }
            itemStr.delete(0,itemStr.length());
        }
        //System.out.println(itemArrayList.size() +" "+itemParser.count);
        return itemArrayList;
    }
    public Map<String,ArrayList<Double>> toGetEachItemCount(ArrayList<Item> itemArrayList){
        int count = 0;
        ArrayList<Double> arrayListNoDuplicates = new ArrayList<>();
        Map<String,ArrayList<Double>> arrayListMap = new HashMap<>();
        ArrayList<Double> temp = null;
        ArrayList<Double> temp1 = null;
        for(int i=0 ;i<itemArrayList.size();i++){
            if(arrayListMap.isEmpty()) {
                //System.out.println("Price:"+itemArrayList.get(i).getPrice());
                arrayListNoDuplicates.add(itemArrayList.get(i).getPrice());
                arrayListMap.put(itemArrayList.get(i).getName(), arrayListNoDuplicates);
                //arrayListNoDuplicates.clear();
            }
            else {
                if(arrayListMap.containsKey(itemArrayList.get(i).getName())){
                    temp1 = new ArrayList<Double>();
                    temp1.add(itemArrayList.get(i).getPrice());
                    temp1.addAll(arrayListMap.get(itemArrayList.get(i).getName()));
                    arrayListMap.remove(itemArrayList.get(i).getName());
                    arrayListMap.put(itemArrayList.get(i).getName(),temp1);
                }
                else{

                    //arrayListNoDuplicates.clear();
                    //System.out.println("Price:"+itemArrayList.get(i).getPrice());
                    temp = new ArrayList<Double>();
                    temp.add(itemArrayList.get(i).getPrice());
                    arrayListMap.put(itemArrayList.get(i).getName(),temp);
                }
            }
        }
        return arrayListMap;
    }
    public Map<Double,Integer> toCountPrice(ArrayList<Double> priceArrayList){
        Map<Double,Integer> map = new HashMap<>();
        Integer count = 0;
        for(int i=0;i<priceArrayList.size();i++){
            if(map.isEmpty()){
                map.put(priceArrayList.get(i),1);

            }
            else {
                if(map.containsKey(priceArrayList.get(i))){
                    int temp = map.get(priceArrayList.get(i));
                    map.remove(priceArrayList.get(i));
                    map.put(priceArrayList.get(i),temp+1);
                }
                else {
                    map.put(priceArrayList.get(i),1);
                }
            }
        }
        return map;
    }
    public void finalOutput(Map<String,ArrayList<Double>> map){
        Map<Double,Integer> priceMap = null;
        String specifiers = "%-10s %-9s %-2s %-2d %-2s%n";
        int itemCount = 0;
        for (Map.Entry<String,ArrayList<Double>> entry:map.entrySet()) {
            String key = entry.getKey().substring(0,1).toUpperCase()+entry.getKey().substring(1);
            ArrayList<Double> value = entry.getValue();
            priceMap = toCountPrice(value);
            itemCount = value.size();
            //System.out.println("Name : "+key+"        seen "+itemCount);

            System.out.format(specifiers, "Name  : ", key,"seen:",itemCount,"times");
            System.out.println("===================================");

            for (Map.Entry<Double,Integer> entry1: priceMap.entrySet()) {
                Double priceKey = entry1.getKey();
                Integer priceCount = entry1.getValue();
                System.out.println("Price :    "+priceKey+"      seen: "+priceCount);
                System.out.println("-----------------------------------");
            }
        }
        System.out.println("Errors seen :"+getCount()+"  times");
    }

}
