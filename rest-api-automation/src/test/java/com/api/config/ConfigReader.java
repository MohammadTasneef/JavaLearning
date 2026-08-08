package com.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads settings from config.properties.
 *
 * An environment variable of the same name always wins over the file, so the API
 * token can stay out of the repository. base.uri becomes BASE_URI,
 * jira.api.token becomes JIRA_API_TOKEN, and so on.
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
		String fromEnvironment = System.getenv(asEnvironmentKey(key));
		if (fromEnvironment != null && !fromEnvironment.isBlank()) {
			return fromEnvironment.trim();
		}
		return PROPERTIES.getProperty(key, "").trim();
	}

	private static String asEnvironmentKey(String key) {
		return key.toUpperCase().replace('.', '_');
	}
}
