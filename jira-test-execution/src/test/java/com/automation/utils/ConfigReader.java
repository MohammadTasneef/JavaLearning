package com.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads settings from config.properties.
 *
 * An environment variable of the same name wins over the file, so passwords and the
 * service account path never have to be committed. jira.password becomes JIRA_PASSWORD,
 * spreadsheet.id becomes SPREADSHEET_ID, and so on.
 */
public class ConfigReader {

	private static final Properties PROPERTIES = new Properties();

	static {
		try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (in == null) {
				throw new IllegalStateException("config.properties was not found on the classpath");
			}
			PROPERTIES.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("Could not read config.properties", e);
		}
	}

	private ConfigReader() {
	}

	public static String get(String key) {
		String fromEnvironment = System.getenv(key.toUpperCase().replace('.', '_'));
		if (fromEnvironment != null && !fromEnvironment.isBlank()) {
			return fromEnvironment.trim();
		}
		return PROPERTIES.getProperty(key, "").trim();
	}

	public static boolean isBlank(String key) {
		return get(key).isEmpty();
	}
}
