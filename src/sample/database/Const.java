package sample.database;

import java.time.LocalDate;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String TASK_TABLE = "tasklist";
    public static final String LIST_TABLE = "userlist";

    // User table columns
    public static final String USER_ID = "idusers";
    public static final String USER_NAME = "username";
    public static final String USER_BIRTHDATE = "birthdate";

    // User List Columns
    public static final String USER_LIST_ID = "userlistid";
    public static final String USER_LIST_NAME = "listname";

    // Task Table Columns
    public static final String TASK_ID = "taskid";
    public static final String TASK_NAME = "taskname";
    public static final String TASK_DESCRIPTION = "taskdescription";
    public static final String TASK_DATECREATED = "datecreated";
    public static final String TASK_DATEBEFORE = "datebefore";
    public static final String TASK_PRIORITY = "priority";
    public static final String TASK_TIMEBEFORE = "timebefore";

}
