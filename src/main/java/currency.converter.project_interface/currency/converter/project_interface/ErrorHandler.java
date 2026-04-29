package currency.converter.project_interface;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;

public class ErrorHandler {
    private final TextArea errorArea;
    private TextField amountField;
    private ResourceBundle bundle;
    private final Button clearBtn;

    public ErrorHandler(VBox errorContainer, TextField amountField, ResourceBundle bundle) {
        this.amountField = amountField;
        this.bundle = bundle;
        errorArea = new TextArea();
        errorArea.setEditable(false);
        errorArea.setWrapText(true);
        errorArea.setPrefHeight(150);
        errorArea.getStyleClass().add("error-text-area");
        clearBtn = createClearButton();
        errorContainer.getChildren().clear();
        errorContainer.getChildren().addAll(errorArea, clearBtn);
    }

    private Button createClearButton() {
        Button btn = new Button(bundle.getString("clear.button"));
        btn.setOnAction(e -> {
            errorArea.clear();
            if (amountField != null) amountField.clear();
        });
        return btn;
    }

    public void showError(String message) {
        errorArea.appendText(message + "\n");
    }

    public void clearErrors() {
        errorArea.clear();
    }

    public void updateBundle(ResourceBundle newBundle) {
        this.bundle = newBundle;
        clearBtn.setText(newBundle.getString("clear.button"));
    }

    public void setAmountField(TextField amountField) {
        this.amountField = amountField;
    }
}
