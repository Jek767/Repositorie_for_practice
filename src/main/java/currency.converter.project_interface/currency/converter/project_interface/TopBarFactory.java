package currency.converter.project_interface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.util.Locale;

public class TopBarFactory {
    private final LocalizationManager localizationManager;
    private final IThemeManager themeManager;
    private final Runnable onLanguageChange;
    private final Button themeButton;
    private final Button langButton;
    private final Label titleLabel;

    public TopBarFactory(LocalizationManager localizationManager, IThemeManager themeManager, Runnable onLanguageChange) {
        this.localizationManager = localizationManager;
        this.themeManager = themeManager;
        this.onLanguageChange = onLanguageChange;
        this.themeButton = createThemeButton();
        this.langButton = createLanguageButton();
        this.titleLabel = new Label("Converter Project");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    }

    public HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10, 0, 20, 0));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("glass-pane");
        HBox leftControls = new HBox(15);
        leftControls.setAlignment(Pos.CENTER_LEFT);
        leftControls.getChildren().addAll(themeButton, langButton);
        HBox.setHgrow(leftControls, Priority.ALWAYS);
        leftControls.setMaxWidth(Double.MAX_VALUE);
        leftControls.setAlignment(Pos.CENTER_LEFT);
        topBar.getChildren().addAll(leftControls, titleLabel);
        return topBar;
    }

    private Button createThemeButton() {
        Button btn = new Button(localizationManager.getBundle().getString("theme.button"));
        btn.getStyleClass().add("settings-button");
        btn.setFocusTraversable(false);
        ContextMenu menu = new ContextMenu();
        MenuItem lightItem = new MenuItem(localizationManager.getBundle().getString("theme.light"));
        MenuItem darkItem = new MenuItem(localizationManager.getBundle().getString("theme.dark"));
        lightItem.setOnAction(e -> {
            themeManager.setDark(false);
            menu.hide();
        });
        darkItem.setOnAction(e -> {
            themeManager.setDark(true);
            menu.hide();
        });
        menu.getItems().addAll(lightItem, darkItem);
        btn.setOnAction(e -> menu.show(btn, Side.BOTTOM, 0, 0));
        return btn;
    }

    private Button createLanguageButton() {
        Button btn = new Button(localizationManager.getBundle().getString("language.button"));
        btn.getStyleClass().add("settings-button");
        btn.setFocusTraversable(false);
        ContextMenu menu = new ContextMenu();
        MenuItem ruItem = new MenuItem(localizationManager.getBundle().getString("lang.ru"));
        MenuItem enItem = new MenuItem(localizationManager.getBundle().getString("lang.en"));
        ruItem.setOnAction(e -> {
            localizationManager.setLocale(new Locale("ru"));
            onLanguageChange.run();
            menu.hide();
        });
        enItem.setOnAction(e -> {
            localizationManager.setLocale(new Locale("en"));
            onLanguageChange.run();
            menu.hide();
        });
        menu.getItems().addAll(ruItem, enItem);
        btn.setOnAction(e -> menu.show(btn, Side.BOTTOM, 0, 0));
        return btn;
    }

    public Button getThemeButton() { return themeButton; }
    public Button getLangButton() { return langButton; }
    public Label getTitleLabel() { return titleLabel; }
}
