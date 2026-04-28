package currency.converter.project_interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizationManager {
    private Locale currentLocale;
    private ResourceBundle bundle;
    private final List<LocaleChangeListener> listeners = new ArrayList<>();

    public LocalizationManager(Locale initialLocale) {
        this.currentLocale = initialLocale;
        reloadBundle();
    }

    private void reloadBundle() {
        try {
            bundle = ResourceBundle.getBundle("messages", currentLocale);
        } catch (MissingResourceException e) {
            bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setLocale(Locale locale) {
        if (locale.equals(currentLocale)) return;
        this.currentLocale = locale;
        reloadBundle();
        notifyListeners();
    }

    private void notifyListeners() {
        for (LocaleChangeListener l : listeners) {
            l.onLocaleChanged(bundle);
        }
    }

    public void addListener(LocaleChangeListener listener) {
        listeners.add(listener);
    }

    public interface LocaleChangeListener {
        void onLocaleChanged(ResourceBundle newBundle);
    }
}
