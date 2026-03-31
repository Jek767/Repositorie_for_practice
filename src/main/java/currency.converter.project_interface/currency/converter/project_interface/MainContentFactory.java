package currency.converter.project_interface;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;

public class MainContentFactory {
    private final LocalizationManager localizationManager;
    private final ICurrencyService currencyService;
    private final IConversionService conversionService;
    private final ErrorHandler errorHandler;
    private final TextField amountField;
    private final TextField resultField;
    private final ComboBox<Currency> comboFrom;
    private final ComboBox<Currency> comboTo;
    private final Button convertBtn;
    private final Label amountLabel, fromLabel, resultLabel, toLabel;

    public MainContentFactory(LocalizationManager localizationManager,
                              ICurrencyService currencyService,
                              IConversionService conversionService,
                              ErrorHandler errorHandler) {
        this.localizationManager = localizationManager;
        this.currencyService = currencyService;
        this.conversionService = conversionService;
        this.errorHandler = errorHandler;
        // Создание элементов с начальным списком валют
        amountField = new TextField();
        resultField = new TextField();
        comboFrom = new ComboBox<>(currencyService.getCurrencies());
        comboTo = new ComboBox<>(currencyService.getCurrencies());
        convertBtn = new Button();
        amountLabel = new Label();
        fromLabel = new Label();
        resultLabel = new Label();
        toLabel = new Label();
        // Начальная настройка
        setupFields();
        updateTexts();
    }

    private void setupFields() {
        amountField.setPromptText(localizationManager.getBundle().getString("sum.label"));
        amountField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String text = amountField.getText();
                String formatted = InputFormatter.normalizeNumber(text);
                if (formatted != null) amountField.setText(formatted);
            }
        });
        resultField.setEditable(false);
        resultField.setPromptText(localizationManager.getBundle().getString("result.label"));
        comboFrom.getSelectionModel().selectFirst();
        comboTo.getSelectionModel().select(1);
        convertBtn.setPrefWidth(200);
        convertBtn.setOnAction(e -> performConversion());
    }

    private void performConversion() {
        errorHandler.clearErrors();
        Double amount = InputFormatter.parseAmount(amountField.getText());
        if (amount == null) {
            errorHandler.showError(localizationManager.getBundle().getString("error.invalid.number"));
            return;
        }
        if (amount <= 0) {
            errorHandler.showError(localizationManager.getBundle().getString("error.positive.amount"));
            return;
        }
        Currency from = comboFrom.getValue();
        Currency to = comboTo.getValue();
        ConversionService.ConversionResult result = conversionService.convert(
                amount, from, to, localizationManager.getBundle());
        if (result.isSuccess()) {
            resultField.setText(InputFormatter.formatAmount(result.getValue()));
        } else {
            errorHandler.showError(result.getErrorMessage());
            resultField.clear();
        }
    }

    public void updateTexts() {
        ResourceBundle bundle = localizationManager.getBundle();
        amountLabel.setText(bundle.getString("amount.label"));
        fromLabel.setText(bundle.getString("from.label"));
        resultLabel.setText(bundle.getString("result.label"));
        toLabel.setText(bundle.getString("to.label"));
        convertBtn.setText(bundle.getString("convert.button"));
        amountField.setPromptText(bundle.getString("sum.label"));
        resultField.setPromptText(bundle.getString("result.label"));
    }

    //Обновляет список валют в ComboBox при смене языка и восстанавливает выбор.
    public void updateCurrencyList() {
        ObservableList<Currency> newCurrencies = currencyService.getCurrencies();
        // Сохраняем коды выбранных валют
        String selectedFromCode = comboFrom.getValue() != null ? comboFrom.getValue().getCode() : null;
        String selectedToCode = comboTo.getValue() != null ? comboTo.getValue().getCode() : null;
        comboFrom.setItems(newCurrencies);
        comboTo.setItems(newCurrencies);
        // Восстанавливаем выбор по коду
        if (selectedFromCode != null) {
            Currency newFrom = currencyService.findByCode(selectedFromCode);
            if (newFrom != null) {
                comboFrom.setValue(newFrom);
            } else {
                comboFrom.getSelectionModel().selectFirst();
            }
        } else {
            comboFrom.getSelectionModel().selectFirst();
        }

        if (selectedToCode != null) {
            Currency newTo = currencyService.findByCode(selectedToCode);
            if (newTo != null) {
                comboTo.setValue(newTo);
            } else {
                comboTo.getSelectionModel().select(1);
            }
        } else {
            comboTo.getSelectionModel().select(1);
        }
    }

    public VBox createCenterBox() {
        VBox centerBox = new VBox(15);
        centerBox.setPrefWidth(400);
        centerBox.getStyleClass().add("glass-pane");
        centerBox.setPadding(new Insets(15));
        centerBox.getChildren().addAll(
                amountLabel, amountField,
                fromLabel, comboFrom,
                resultLabel, resultField,
                toLabel, comboTo,
                convertBtn
        );
        return centerBox;
    }

    // Геттеры
    public TextField getAmountField() { return amountField; }
    public TextField getResultField() { return resultField; }
    public ComboBox<Currency> getComboFrom() { return comboFrom; }
    public ComboBox<Currency> getComboTo() { return comboTo; }
}
