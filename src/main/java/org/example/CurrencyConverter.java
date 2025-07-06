package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class CurrencyConverter {

    private static String API_KEY = System.getenv("EXCHANGE_API_KEY");
    private static final String BASE_URL;

    static {
        Properties properties = new Properties();
        try (InputStream input = CurrencyConverter.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources folder");
            }
            properties.load(input);
            API_KEY = properties.getProperty("api.key");

            if (API_KEY == null || API_KEY.isEmpty()) {
                throw new RuntimeException("API key is missing in config.properties");
            }

            BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
        } catch (Exception e) {
            throw new RuntimeException("Failed to load API key from config.properties", e);
        }
    }

    public static double convertCurrency(String currencyFrom, String currencyTo, double amount) throws IOException {
        String urlStr = BASE_URL + currencyFrom;

        URL url = new URL(urlStr);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonObject json = JsonParser.parseReader(new InputStreamReader(request.getInputStream())).getAsJsonObject();

        if (!json.get("result").getAsString().equals("success")) {
            throw new RuntimeException("API call failed: " + json);
        }

        JsonObject rates = json.getAsJsonObject("conversion_rates");
        double rate = rates.get(currencyTo).getAsDouble();

        return amount * rate;

    }

}
