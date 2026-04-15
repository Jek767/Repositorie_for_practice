package currency.converter.project_interface;

import java.util.ResourceBundle;

public class ConversionService implements IConversionService {

    @Override
    public ConversionResult convert(double amount, Currency from, Currency to, ResourceBundle bundle) {
        if (from == null || to == null) {
            return ConversionResult.error(bundle.getString("error.select.currencies"));
        }
        if (from.equals(to)) {
            return ConversionResult.error(bundle.getString("error.same.currency"));
        }
        String request = InputFormatter.formatAmount(amount) + "===" + from.getCode() + "===" + to.getCode() + "===" + bundle.getLocale().getLanguage();
        String response = CppBridge.sendRequest(request, bundle);
        try {
            double result = Double.parseDouble(response);
            return ConversionResult.success(result);
        } catch (NumberFormatException e) {
            return ConversionResult.error(response);
        }
    }

    public static class ConversionResult {
        private final boolean success;
        private final double value;
        private final String errorMessage;

        private ConversionResult(boolean success, double value, String errorMessage) {
            this.success = success;
            this.value = value;
            this.errorMessage = errorMessage;
        }

        public static ConversionResult success(double value) {
            return new ConversionResult(true, value, null);
        }

        public static ConversionResult error(String message) {
            return new ConversionResult(false, 0, message);
        }

        public boolean isSuccess() { return success; }
        public double getValue() { return value; }
        public String getErrorMessage() { return errorMessage; }
    }
}
