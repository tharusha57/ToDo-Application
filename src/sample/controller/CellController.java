package sample.controller;

import com.jfoenix.controls.JFXButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

public class CellController extends JFXListCell<User> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label cellLabel;

    @FXML
    private JFXButton cellButton;

    @FXML
    private ImageView image;

    @FXML
    private JFXButton cellDeleteButton;

    private DatabaseHandler databaseHandler;

    private FXMLLoader fxmlLoader;

    public int userId;

    public static int userListId;

    public static int getUserListId() {
        return userListId;
    }

    public static void setUserListId(int userListId) {
        CellController.userListId = userListId;
    }

    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(User user, boolean empty) {

        super.updateItem(user, empty);

        if(empty || user == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            cellLabel.setText(user.getUsername());

            setText(null);
            setGraphic(rootPane);

            cellButton.setOnAction(event -> {

                TaskViewController taskViewController = new TaskViewController();
                taskViewController.setUserId(user.getUserId());

                TaskViewController.viewCurrentUserName = user.getUsername();


                cellButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/taskView.fxml"));
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

            cellDeleteButton.setOnAction(event -> {
                getListView().getItems().remove(getItem());

                databaseHandler = new DatabaseHandler();

                try {
                    databaseHandler.removeUser(user.getUserId());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

        }
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserID(){
        return userId;
    }
}