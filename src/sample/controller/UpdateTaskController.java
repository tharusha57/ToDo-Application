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
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

public class UpdateTaskController {

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
    private ImageView taskBackButton;

    private DatabaseHandler databaseHandler;


    @FXML
    void initialize() {

        taskSaveButton.setOnAction(event -> {
            TaskListCellController taskListCellController = new TaskListCellController();
            databaseHandler = new DatabaseHandler();

            String priority;

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
            task.setTaskName(addTaskName.getText().trim());
            task.setTaskId(TaskListCellController.taskId);
            task.setTaskDescription(addTaskDescription.getText().trim());
            task.setDateCreated(timestamp);
            task.setDateBefore(dateNew);
            task.setPriority(priority);
            task.setTimeBofre(timeNew);

            try {
                databaseHandler.updateTask(task);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            taskSaveButton.getScene().getWindow().hide();

        });

        taskBackButton.setOnMouseClicked(event -> {
            taskBackButton.getScene().getWindow().hide();

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
