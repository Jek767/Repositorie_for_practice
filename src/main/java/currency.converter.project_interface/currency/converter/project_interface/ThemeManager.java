package currency.converter.project_interface;

import javafx.scene.layout.BorderPane;

public class ThemeManager implements IThemeManager {
    private final BorderPane root;
    private boolean dark = false;

    public ThemeManager(BorderPane root) {
        this.root = root;
        root.getStyleClass().add("root-light");
    }

    @Override
    public void setDark(boolean dark) {
        if (this.dark == dark) return;
        this.dark = dark;
        root.getStyleClass().removeAll("root-light", "root-dark");
        root.getStyleClass().add(dark ? "root-dark" : "root-light");
    }

    @Override
    public boolean isDark() {
        return dark;
    }

    @Override
    public void toggle() {
        setDark(!dark);
    }
}
