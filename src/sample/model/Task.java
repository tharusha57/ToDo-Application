package sample.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Task {
    private int taskId;
    private int userListId;
    private String taskName;
    private String taskDescription;
    private String taskPriority;
    private Date dateBefore;
    private String timeBofre;

    private Timestamp dateCreated;

    public Task(int taskId, int userListId, String taskName, String taskDescription, String taskPriority, Date dateBefore, String timeBofre, Timestamp dateCreated, String priority) {
        this.taskId = taskId;
        this.userListId = userListId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.dateBefore = dateBefore;
        this.timeBofre = timeBofre;
        this.dateCreated = dateCreated;
        this.priority = priority;
    }

    public String getTimeBofre() {
        return timeBofre;
    }

    public void setTimeBofre(String timeBofre) {
        this.timeBofre = timeBofre;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Task() {
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    private String priority;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserListId() {
        return userListId;
    }

    public void setUserListId(int userid) {
        this.userListId = userid;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }


    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
