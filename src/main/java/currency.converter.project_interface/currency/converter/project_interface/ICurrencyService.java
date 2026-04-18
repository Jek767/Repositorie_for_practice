package currency.converter.project_interface;

import javafx.collections.ObservableList;

public interface ICurrencyService {
    ObservableList<Currency> getCurrencies();
    Currency findByCode(String code);
}
