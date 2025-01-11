package software.ulpgc.moneycalculator;

import software.ulpgc.moneycalculator.fixerws.FixerCurrencyLoader;
import software.ulpgc.moneycalculator.swing.SwingMain;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Currency> currencies = new FixerCurrencyLoader().load();
        SwingMain swingMain = new SwingMain(currencies);
        swingMain.setVisible(true);
    }
}
