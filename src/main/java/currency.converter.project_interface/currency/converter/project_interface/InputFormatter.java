package currency.converter.project_interface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class InputFormatter {

    private static final DecimalFormat TWO_DECIMALS = new DecimalFormat("#0.##",
            new DecimalFormatSymbols(Locale.US)); // формат с точкой в качестве разделителя

    public static String normalizeNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        // 1. Замена запятой на точку
        String normalized = input.trim().replace(',', '.');
        // 2. Удаление пробелов (если были)
        normalized = normalized.replaceAll("\\s", "");
        // 3. Проверка на число
        try {
            double value = Double.parseDouble(normalized);
            // 4. Форматирование с двумя знаками после запятой (без лишних нулей)
            String formatted = TWO_DECIMALS.format(value);
            return formatted;
        } catch (NumberFormatException e) {
            return null; // не число
        }
    }

    /* Парсит строку в Double после нормализации.
     Возвращает Double или null при ошибке */
    public static Double parseAmount(String input) {
        String normalized = normalizeNumber(input);
        if (normalized == null) {
            return null;
        }
        try {
            return Double.parseDouble(normalized);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /* Форматирует число с двумя знаками после запятой.
     Возвращает строку вида "123.45" */
    public static String formatAmount(double amount) {
        return TWO_DECIMALS.format(amount);
    }
}
