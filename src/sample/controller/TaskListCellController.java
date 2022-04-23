package sample.controller;

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
import sample.model.Task;

public class TaskListCellController extends JFXListCell<Task> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label taskName;

    @FXML
    private Label taskDescription;

    @FXML
    private Label dateBefore;

    @FXML
    private AnchorPane taskListPane;

    private FXMLLoader fxmlLoader;

    @FXML
    private Label taskPriority;

    @FXML
    private Label dateAfter;

    @FXML
    private ImageView taskDeleteButton;

    @FXML
    private ImageView taskUpdateButton;

    @FXML
    private Label timeBefore;

    @FXML
    private ImageView taskLowIcon;

    @FXML
    private ImageView taskMidIcon;

    @FXML
    private ImageView taskHighIcon;

    private DatabaseHandler databaseHandler;

    public static int taskId;

    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if(empty || task == null){
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/taskListCell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            taskName.setText(task.getTaskName());
            taskDescription.setText(task.getTaskDescription());
            dateBefore.setText(task.getDateBefore().toString());
            dateAfter.setText(task.getDateCreated().toString());
            taskPriority.setText(task.getTaskPriority());
            timeBefore.setText(task.getTimeBofre());

            if(taskPriority.getText().equals("High")){
                taskHighIcon.setVisible(true);
            }else if(taskPriority.getText().equals("Mid")){
                taskMidIcon.setVisible(true);
            }else{
                taskLowIcon.setVisible(true);
            }


            setText(null);
            setGraphic(taskListPane);

            taskDeleteButton.setOnMouseClicked(event -> {
                getListView().getItems().remove(getItem());
                databaseHandler = new DatabaseHandler();

                try {
                    databaseHandler.removeTask(task.getTaskId());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            });

            taskUpdateButton.setOnMouseClicked(event -> {

                taskId = task.getTaskId();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/updateTask.fxml"));
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
}
