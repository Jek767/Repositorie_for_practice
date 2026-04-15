package currency.converter.project_interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ResourceBundle;

public class CurrencyService implements ICurrencyService, LocalizationManager.LocaleChangeListener {
    private final LocalizationManager localizationManager;
    private ObservableList<Currency> currencies;

    public CurrencyService(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
        localizationManager.addListener(this);
        rebuildList(localizationManager.getBundle());
    }

    private void rebuildList(ResourceBundle bundle) {
        currencies = FXCollections.observableArrayList(
                new Currency(1, "AUD", bundle.getString("currency.AUD")),
                new Currency(2, "AZN", bundle.getString("currency.AZN")),
                new Currency(3, "RUB", bundle.getString("currency.RUB")),
                new Currency(4, "DZD", bundle.getString("currency.DZD")),
                new Currency(5, "GBP", bundle.getString("currency.GBP")),
                new Currency(6, "AMD", bundle.getString("currency.AMD")),
                new Currency(7, "BHD", bundle.getString("currency.BHD")),
                new Currency(8, "BYN", bundle.getString("currency.BYN")),
                new Currency(9, "BOB", bundle.getString("currency.BOB")),
                new Currency(10, "BRL", bundle.getString("currency.BRL")),
                new Currency(11, "HUF", bundle.getString("currency.HUF")),
                new Currency(12, "VND", bundle.getString("currency.VND")),
                new Currency(13, "HKD", bundle.getString("currency.HKD")),
                new Currency(14, "GEL", bundle.getString("currency.GEL")),
                new Currency(15, "DKK", bundle.getString("currency.DKK")),
                new Currency(16, "AED", bundle.getString("currency.AED")),
                new Currency(17, "USD", bundle.getString("currency.USD")),
                new Currency(18, "EUR", bundle.getString("currency.EUR")),
                new Currency(19, "EGP", bundle.getString("currency.EGP")),
                new Currency(20, "INR", bundle.getString("currency.INR")),
                new Currency(21, "IDR", bundle.getString("currency.IDR")),
                new Currency(22, "IRR", bundle.getString("currency.IRR")),
                new Currency(23, "KZT", bundle.getString("currency.KZT")),
                new Currency(24, "CAD", bundle.getString("currency.CAD")),
                new Currency(25, "QAR", bundle.getString("currency.QAR")),
                new Currency(26, "KGS", bundle.getString("currency.KGS")),
                new Currency(27, "CNY", bundle.getString("currency.CNY")),
                new Currency(28, "CUP", bundle.getString("currency.CUP")),
                new Currency(29, "MDL", bundle.getString("currency.MDL")),
                new Currency(30, "MNT", bundle.getString("currency.MNT")),
                new Currency(31, "NGN", bundle.getString("currency.NGN")),
                new Currency(32, "NZD", bundle.getString("currency.NZD")),
                new Currency(33, "NOK", bundle.getString("currency.NOK")),
                new Currency(34, "OMR", bundle.getString("currency.OMR")),
                new Currency(35, "PLN", bundle.getString("currency.PLN")),
                new Currency(36, "SAR", bundle.getString("currency.SAR")),
                new Currency(37, "RON", bundle.getString("currency.RON")),
                new Currency(38, "SGD", bundle.getString("currency.SGD")),
                new Currency(39, "TJS", bundle.getString("currency.TJS")),
                new Currency(40, "THB", bundle.getString("currency.THB")),
                new Currency(41, "BDT", bundle.getString("currency.BDT")),
                new Currency(42, "TRY", bundle.getString("currency.TRY")),
                new Currency(43, "TMT", bundle.getString("currency.TMT")),
                new Currency(44, "UZS", bundle.getString("currency.UZS")),
                new Currency(45, "UAH", bundle.getString("currency.UAH")),
                new Currency(46, "CZK", bundle.getString("currency.CZK")),
                new Currency(47, "SEK", bundle.getString("currency.SEK")),
                new Currency(48, "CHF", bundle.getString("currency.CHF")),
                new Currency(49, "ETB", bundle.getString("currency.ETB")),
                new Currency(50, "RSD", bundle.getString("currency.RSD")),
                new Currency(51, "ZAR", bundle.getString("currency.ZAR")),
                new Currency(52, "KRW", bundle.getString("currency.KRW")),
                new Currency(53, "JPY", bundle.getString("currency.JPY")),
                new Currency(54, "MMK", bundle.getString("currency.MMK"))
        );
    }

    @Override
    public ObservableList<Currency> getCurrencies() {
        return currencies;
    }

    @Override
    public Currency findByCode(String code) {
        return currencies.stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void onLocaleChanged(ResourceBundle newBundle) {
        rebuildList(newBundle);
    }
}
