module currency.converter.project_interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens currency.converter.project_interface to javafx.fxml;
    exports currency.converter.project_interface;
}