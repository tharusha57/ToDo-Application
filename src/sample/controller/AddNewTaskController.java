package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

public class AddNewTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField addTaskName;

    @FXML
    private JFXTextField addTaskDescription;

    @FXML
    private JFXCheckBox highPick;

    @FXML
    private JFXCheckBox midPick;

    @FXML
    private JFXCheckBox lowPick;

    @FXML
    private JFXButton taskSaveButton;

    @FXML
    private JFXDatePicker taskDateBefore;

    @FXML
    private JFXTimePicker taskTimeBefore;

    @FXML
    private Label addTaskWarning;

    @FXML
    private ImageView taskBackButton;

    private DatabaseHandler databaseHandler;

    int tempId = getUserListId();

    private int userListId;

    public int getUserListId() {
        return userListId;
    }

    public void setUserListId(int userListId) {
        this.userListId = userListId;
    }

    @FXML
    void initialize() {

        taskSaveButton.setOnAction(event -> {
            databaseHandler = new DatabaseHandler();

            String priority  = "";

            if(highPick.isSelected()){
                lowPick.unCheckedColorProperty();
                midPick.unCheckedColorProperty();
                priority = "High";
            }else if(midPick.isSelected()){
                lowPick.unCheckedColorProperty();
                highPick.unCheckedColorProperty();
                priority = "Mid";
            }else{
                priority = "Low";
            }

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            Task task = new Task();

            Date dateNew = java.sql.Date.valueOf(taskDateBefore.getValue());
            String timeNew = taskTimeBefore.getValue().toString();
            task.setUserListId(UserListCellController.userListId);
            task.setTaskName(addTaskName.getText().trim());
            task.setTaskDescription(addTaskDescription.getText().trim());
            task.setDateCreated(timestamp);
            task.setDateBefore(dateNew);
            task.setPriority(priority);
            task.setTimeBofre(timeNew);

            if(taskDateBefore.getValue() == null ||taskTimeBefore.getValue() == null || addTaskName.getText() == null || addTaskName.getText() == null ||
                    addTaskDescription.getText() == null ||  priority == null){
                addTaskWarning.setVisible(true);
            }else{

                try {
                    databaseHandler.createNewTask(task);
                } catch (SQLException e) {
                    showWarningMessage();
                } catch (ClassNotFoundException e) {
                    showWarningMessage();
                }

                taskSaveButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/taskView.fxml"));
                try {
                    loader.load();
                } catch (IOException d) {
                    d.printStackTrace();
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

        taskBackButton.setOnMouseClicked(event -> {
            taskBackButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/taskView.fxml"));
            try {
                loader.load();
            } catch (IOException d) {
                d.printStackTrace();
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
    public void showWarningMessage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/selectListWarning.fxml"));
        try {
            loader.load();
        } catch (IOException d) {
            d.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/images/logo-icon-transparent.png"));
        stage.setTitle("To Do");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
