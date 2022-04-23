package sample.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class EmptyListWarningController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton emptyListOkButton;

    @FXML
    void initialize() {
        emptyListOkButton.setOnAction(event -> {
            emptyListOkButton.getScene().getWindow().hide();
        });
    }
}
