package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sample.model.Task;
import sample.model.UserList;

public class TaskViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView viewMyDay;

    @FXML
    private ImageView viewUserLists;

    @FXML
    private ImageView viewImportant;

    @FXML
    private ImageView viewFavourites;

    @FXML
    private AnchorPane importantPane;

    @FXML
    private AnchorPane mydayPane;

    @FXML
    private Label viewTaskdate;

    @FXML
    public JFXListView<Task> taskItemView;

    public ObservableList<Task> taskItem;
    public ObservableList<Task> refreshedTaskItem;

    @FXML
    private JFXListView<UserList> taskListView;

    private ObservableList<UserList> userViewList;

    @FXML
    private ImageView viewAddTask;

    @FXML
    public ImageView viewAddList;

    @FXML
    private Label viewUserName;

    public static String viewCurrentUserName;

    @FXML
    private Label viewCurrentList;

    @FXML
    private ImageView viewGoBackButton;

    public static String viewCurrentListNow;

    private DatabaseHandler databaseHandler;

    public static int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static int userListId;

    public int getUserListId() {
        return userListId;
    }

    public void setUserListId(int userListId) {
        this.userListId = userListId;
    }


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        viewTaskdate.setText(localDate.toString());
        viewCurrentList.setText(viewCurrentListNow);
        viewUserName.setText(viewCurrentUserName);

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getListItems(getUserId());
        userViewList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            UserList userList = new UserList(resultSet.getString("listname"));
            userList.setUserId(resultSet.getInt("idusers"));
            userList.setUserListId(resultSet.getInt("userlistid"));

            userViewList.add(userList);
        }

        taskListView.setItems(userViewList);
        taskListView.setCellFactory(UserListCellController -> new UserListCellController());

        viewGoBackButton.setOnMouseClicked(event -> {
            viewGoBackButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
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

        viewAddTask.setOnMouseClicked(event -> {

            if (userViewList.isEmpty()) {
                viewAddTask.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/emptyListWarning.fxml"));
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

            } else {
                viewAddTask.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/addNewTask.fxml"));
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
            }

        });

        viewAddList.setOnMouseClicked(event -> {

            AddNewListController addNewListController = new AddNewListController();
            addNewListController.setUserId(getUserId());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/addNewList.fxml"));
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
            stage.showAndWait();

            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        importantPane.setOnMouseClicked(event -> {
            databaseHandler = new DatabaseHandler();
            try {
                ResultSet highResultSet = databaseHandler.getHighTask(getUserListId());

                refreshedTaskItem = FXCollections.observableArrayList();

                while (highResultSet.next()) {
                    Task task = new Task();

                    task.setTaskName(highResultSet.getString("taskname"));
                    task.setTaskDescription(highResultSet.getString("taskdescription"));
                    task.setDateBefore(highResultSet.getDate("datebefore"));
                    task.setDateCreated(highResultSet.getTimestamp("datecreated"));
                    task.setTaskId(highResultSet.getInt("taskid"));
                    task.setTaskPriority(highResultSet.getString("priority"));
                    task.setTimeBofre(highResultSet.getString("timebefore"));

                    refreshedTaskItem.add(task);
                }

                taskItemView.setItems(refreshedTaskItem);
                taskItemView.setCellFactory(taskListCellController -> new TaskListCellController());


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

    }
}