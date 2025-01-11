package software.ulpgc.moneycalculator.swing;

import software.ulpgc.moneycalculator.*;
import software.ulpgc.moneycalculator.fixerws.FixerCurrencyLoader;
import software.ulpgc.moneycalculator.mocks.MockExchangeRateLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingMain extends JFrame {
    private final Map<String, Command> commands = new HashMap<>();
    private MoneyDisplay moneyDisplay;
    private MoneyDialog moneyDialog;
    private CurrencyDialog currencyDialog;

    public SwingMain(List<Currency> currencies) {
        this.setTitle("Money Calculator");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        initializeComponents(currencies);
    }

    private void initializeComponents(List<Currency> currencies) {
        this.moneyDialog = new SwingMoneyDialog().define(currencies);
        this.currencyDialog = new SwingCurrencyDialog().define(currencies);
        this.moneyDisplay = new SwingMoneyDisplay();

        this.add((Component) moneyDialog);
        this.add((Component) currencyDialog);
        this.add((Component) moneyDisplay);
        this.add(createToolbar());

        Command command = new ExchangeMoneyCommand(
                moneyDialog, currencyDialog, new MockExchangeRateLoader(), moneyDisplay);
        addCommand("exchange money", command);
    }

    private Component createToolbar() {
        JButton button = new JButton("Calculate");
        button.addActionListener(e -> commands.get("exchange money").execute());
        return button;
    }

    private void addCommand(String name, Command command) {
        commands.put(name, command);
    }
}
