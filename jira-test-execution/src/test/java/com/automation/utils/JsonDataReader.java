package com.automation.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reads the release cycle rows out of JsonData/testdata.json. Login details are not in
 * there any more, they come from the environment through ConfigReader.
 */
public class JsonDataReader {

	private static final String DATA_FILE = "JsonData/testdata.json";

	private JsonDataReader() {
	}

	public static List<Map<String, String>> readReleaseCycles() {
		File file = new File(System.getProperty("user.dir"), DATA_FILE);
		try {
			Map<String, List<Map<String, String>>> parsed = new ObjectMapper().readValue(file,
					new TypeReference<Map<String, List<Map<String, String>>>>() {
					});
			List<Map<String, String>> rows = parsed.get("Data");
			if (rows == null || rows.isEmpty()) {
				throw new IllegalStateException("No \"Data\" rows found in " + file);
			}
			return rows;
		} catch (IOException e) {
			throw new IllegalStateException("Could not read " + file, e);
		}
	}
}
