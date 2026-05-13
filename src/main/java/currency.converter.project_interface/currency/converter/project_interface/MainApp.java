package currency.converter.project_interface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application implements LocalizationManager.LocaleChangeListener {
    private LocalizationManager localizationManager;
    private ThemeManager themeManager;
    private CurrencyService currencyService;
    private ConversionService conversionService;
    private ErrorHandler errorHandler;
    private MainContentFactory mainContentFactory;
    private TopBarFactory topBarFactory;
    // Ссылки на элементы, обновляемые при смене языка
    private Button themeButton, langButton;
    private Label titleLabel;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        // Инициализация сервисов
        localizationManager = new LocalizationManager(new Locale("en"));
        currencyService = new CurrencyService(localizationManager);
        localizationManager.addListener(this);

        root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setFocusTraversable(true);
        themeManager = new ThemeManager(root);
        conversionService = new ConversionService();
        // Левая панель ошибок
        VBox leftBox = new VBox(10);
        leftBox.setPrefWidth(250);
        leftBox.getStyleClass().add("glass-pane");
        leftBox.setPadding(new Insets(15));
        errorHandler = new ErrorHandler(leftBox, null, localizationManager.getBundle());
        // Фабрика центральной части
        mainContentFactory = new MainContentFactory(localizationManager, currencyService, conversionService, errorHandler);
        // Передаём поле ввода в errorHandler
        errorHandler.setAmountField(mainContentFactory.getAmountField());
        // Фабрика верхней панели
        topBarFactory = new TopBarFactory(localizationManager, themeManager, this::onLanguageChanged);
        HBox topBar = topBarFactory.createTopBar();
        // Сохраняем ссылки на кнопки и заголовок
        themeButton = topBarFactory.getThemeButton();
        langButton = topBarFactory.getLangButton();
        titleLabel = topBarFactory.getTitleLabel();
        root.setTop(topBar);
        root.setCenter(createMainLayout(leftBox, mainContentFactory.createCenterBox()));
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        // Сброс фокуса
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node target = (Node) event.getTarget();
            boolean isControl = false;
            Node node = target;
            while (node != null) {
                if (node instanceof Control) {
                    isControl = true;
                    break;
                }
                node = node.getParent();
            }
            if (!isControl) root.requestFocus();
        });
        primaryStage.setTitle("Converter Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createMainLayout(VBox leftBox, VBox centerBox) {
        HBox mainBox = new HBox(20);
        mainBox.setPadding(new Insets(10, 0, 0, 0));
        HBox.setHgrow(centerBox, Priority.ALWAYS);
        mainBox.getChildren().addAll(leftBox, centerBox);
        return mainBox;
    }

    private void onLanguageChanged() {
        // Колбэк из TopBarFactory, всё обновится через onLocaleChanged
    }

    @Override
    public void onLocaleChanged(ResourceBundle newBundle) {
        // Обновляем верхнюю панель
        themeButton.setText(newBundle.getString("theme.button"));
        langButton.setText(newBundle.getString("language.button"));
        titleLabel.setText("Bakhlany Convertor"); // статично
        // Обновляем центральную часть
        mainContentFactory.updateTexts();
        mainContentFactory.updateCurrencyList();
        // Обновляем кнопку очистки
        errorHandler.updateBundle(newBundle);
        errorHandler.clearErrors();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
