package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.model.UserList;

public class UserListCellController extends JFXListCell<UserList> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userListName;

    @FXML
    private AnchorPane rootViewPane;

    private DatabaseHandler databaseHandler;


    public static int userListId;

    @FXML
    public Button userListCellButton;

    @FXML
    private JFXButton userListDelete;

    TaskViewController taskViewController;

    private FXMLLoader fxmlLoader;

    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(UserList userList, boolean empty) {
        super.updateItem(userList, empty);

        if(empty || userList == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/userListCell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            userListName.setText(userList.getListName());
            setText(null);
            setGraphic(rootViewPane);

            rootViewPane.setOnMouseClicked(event -> {
                userListId = userList.getUserListId();

                TaskViewController taskViewController = new TaskViewController();
                taskViewController.setUserListId(userListId);
                TaskViewController.viewCurrentListNow = userList.getListName();

                DatabaseHandler databaseHandler = new DatabaseHandler();
                try {
                    ResultSet resultSet = databaseHandler.getTaskList(userListId);

                    if( !resultSet.isBeforeFirst() ){
                        rootViewPane.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/sample/view/emptyListMessage.fxml"));
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

                    }else{
                        rootViewPane.getScene().getWindow().hide();
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

                        taskViewController = loader.getController();
                        taskViewController.taskItem = FXCollections.observableArrayList();

                        while(resultSet.next()){
                            Task task = new Task();

                            task.setTaskName(resultSet.getString("taskname"));
                            task.setTaskDescription(resultSet.getString("taskdescription"));
                            task.setDateBefore(resultSet.getDate("datebefore"));
                            task.setDateCreated(resultSet.getTimestamp("datecreated"));
                            task.setTaskId(resultSet.getInt("taskid"));
                            task.setTaskPriority(resultSet.getString("priority"));
                            task.setTimeBofre(resultSet.getString("timebefore"));

                            taskViewController.taskItem.add(task);
                        }

                        taskViewController.taskItemView.setItems(taskViewController.taskItem);
                        taskViewController.taskItemView.setCellFactory(taskListCellController -> new TaskListCellController());

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            userListDelete.setOnAction(event -> {
                databaseHandler = new DatabaseHandler();

                try {
                    databaseHandler.removeUserList(userList.getUserListId());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                userListDelete.getScene().getWindow().hide();

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
        }
    }
    public void setUserId(int userId){
        this.userListId = userListId;
    }
    public int getUserID(){
        return userListId;
    }
}

