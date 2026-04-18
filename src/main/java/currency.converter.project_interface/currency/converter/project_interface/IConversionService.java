package currency.converter.project_interface;

import java.util.ResourceBundle;

public interface IConversionService {
    ConversionService.ConversionResult convert(double amount, Currency from, Currency to, ResourceBundle bundle);
}
