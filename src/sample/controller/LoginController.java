package sample.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton newuserButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXListView<User> listView;

    private ObservableList<User> users;

    private DatabaseHandler databaseHandler;

    private int userId;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getNumberOfUsers();
        users = FXCollections.observableArrayList();

        while(resultSet.next()){
            User user = new User(resultSet.getString("username"));
            userId = resultSet.getInt("idusers");

            user.setUserId(resultSet.getInt("idusers"));

            users.add(user);
        }

        listView.setItems(users);
        listView.setCellFactory(CellController -> new CellController());

        newuserButton.setOnAction(event -> {
            newuserButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/userCreate.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/images/logo-icon-transparent.png"));
            stage.setTitle("To Do");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        });

    }
}


