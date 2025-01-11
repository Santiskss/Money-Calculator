package software.ulpgc.moneycalculator.fixerws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.ExchangeRate;
import software.ulpgc.moneycalculator.ExchangeRateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

public class FixerExchangeRateLoader implements ExchangeRateLoader {
    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return toExchangeRate(loadJson(from, to), from, to);
        } catch (IOException e) {
            throw new RuntimeException("Error loading exchange rate", e);
        }
    }

    private ExchangeRate toExchangeRate(String json, Currency from, Currency to) {
        JsonObject rates = new Gson().fromJson(json, JsonObject.class).get("rates").getAsJsonObject();
        double fromRate = rates.get(from.code()).getAsDouble();
        double toRate = rates.get(to.code()).getAsDouble();
        double rate = toRate / fromRate;
        return new ExchangeRate(from, to, LocalDate.now(), rate);
    }

    private String loadJson(Currency from, Currency to) throws IOException {
        URL url = new URL("http://data.fixer.io/api/latest?access_key=" + FixerAPI.key + "&symbols=" + from.code() + "," + to.code());
        try (InputStream is = url.openStream()) {
            return new String(is.readAllBytes());
        }
    }
}
