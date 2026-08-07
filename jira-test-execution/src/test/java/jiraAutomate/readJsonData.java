package jiraAutomate;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class readJsonData {

	public static void main(String[] args) throws IOException, ParseException {
	
		String filepath = System.getProperty("user.dir")+ ".\\JsonData\\testdata.json" ;
		FileReader file = new FileReader(filepath);
		JSONParser parser = new JSONParser();
		JSONObject obj =  (JSONObject) parser.parse(file);
		JSONArray jsonArray =(JSONArray) obj.get("Data");
		//JSONObject object = (JSONObject) jsonArray.get(0);
		//JSONArray jsonArraydata = (JSONArray) object.get(object);
		JSONObject singleData = (JSONObject) jsonArray.get(0);
		System.out.println(singleData.get("USERNAME"));
		System.out.println(singleData.get("PASSWORD"));
		System.out.println(singleData.get("RELEASE_CYCLE_NAME"));
		System.out.println(singleData.get("RELEASE_CYCLE_YEAR"));
		System.out.println(singleData.get("RELEASE_CYCLE_DATE"));
	}

}
