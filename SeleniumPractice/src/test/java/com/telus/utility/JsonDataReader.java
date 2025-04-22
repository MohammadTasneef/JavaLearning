package com.telus.utility;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonDataReader {
    private JSONObject jsonData;
    
    public JsonDataReader() {
        try {
            // Read JSON file
            String filepath = System.getProperty("user.dir") + "\\JsonData\\testdata.json";
            FileReader file = new FileReader(filepath);
            JSONParser parser = new JSONParser();
            this.jsonData = (JSONObject) parser.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Object[][] getTestData() {
        try {
            JSONArray jsonArray = (JSONArray) this.jsonData.get("Data");
            Object[][] data = new Object[jsonArray.size()][1];
            
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject singleData = (JSONObject) jsonArray.get(i);
                data[i][0] = singleData;
            }
            
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0]; // Return empty array in case of error
        }
    }
}