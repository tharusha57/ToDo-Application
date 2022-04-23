package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;


public class UserCreateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField createUsername;

    @FXML
    private JFXDatePicker createBirthDate;

    @FXML
    private Label warningLabel;

    @FXML
    private JFXButton createUserButton;

    @FXML
    private ImageView createUserBackButton;

    @FXML
    void initialize() {

        createUserButton.setOnAction(event -> {
            try {
                createUser();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        createUserBackButton.setOnMouseClicked(event -> {
            createUserBackButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        });

    }
    public void createUser() throws SQLException, ClassNotFoundException {
        if(createUsername.getText().equals("") || createBirthDate.getValue().equals("")){
            warningLabel.setVisible(true);
        }else{
            String firstName = createUsername.getText().trim();
            LocalDate birthDate = createBirthDate.getValue();

            Date date = Date.valueOf(birthDate);

            User user = new User(firstName,date);

            DatabaseHandler databaseHandler = new DatabaseHandler();

            databaseHandler.createUser(user);

            createUserButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

        }
    }
}