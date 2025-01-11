package software.ulpgc.moneycalculator.mocks;

import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.ExchangeRate;
import software.ulpgc.moneycalculator.ExchangeRateLoader;
import software.ulpgc.moneycalculator.fixerws.FixerExchangeRateLoader;

import java.time.LocalDate;

public class MockExchangeRateLoader implements ExchangeRateLoader {

    private final FixerExchangeRateLoader fixerExchangeRateLoader = new FixerExchangeRateLoader();

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        double rate = fixerExchangeRateLoader.load(from, to).rate();
        return new ExchangeRate(from, to, LocalDate.now(), rate);
    }
}
