package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import sample.database.DatabaseHandler;
import sample.model.UserList;

public class AddNewListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField listName;

    private DatabaseHandler databaseHandler;

    @FXML
    private JFXButton listCreateButton;

    public static int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    void initialize() {
        listCreateButton.setOnAction(event -> {
         UserList userList = new UserList(getUserId() , listName.getText().trim());

            databaseHandler = new DatabaseHandler();
            try {
                databaseHandler.createUserList(userList);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            listCreateButton.getScene().getWindow().hide();
        });

    }
}