package sample.database;

import sample.model.Task;
import sample.model.User;
import sample.model.UserList;

import java.sql.*;

public class DatabaseHandler extends Config{

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String connectedString = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectedString,dbUser,dbPass);

        return dbConnection;
    }

    public void createUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_NAME + ","+ Const.USER_BIRTHDATE+")"+ "VALUES(?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setDate(2, user.getBirthdate());

        preparedStatement.executeUpdate();

    }
    /*public ResultSet getUser(){
        String query = "SELECT * FROM " + Const.USER_TABLE +  "WHERE " + Const.USER_NAME
    }*/
    public ResultSet getNumberOfUsers() throws SQLException, ClassNotFoundException {
        String query = " SELECT * FROM " + Const.USER_TABLE;

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;

    }
    public ResultSet getListItems(int userId) throws SQLException, ClassNotFoundException {
        String query = " SELECT * FROM " + Const.LIST_TABLE + " WHERE " + Const.USER_ID + "=" + userId;

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;

    }
//    public void createUser(User user) throws SQLException, ClassNotFoundException {
//        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_NAME + ","+ Const.USER_BIRTHDATE+")"+ "VALUES(?,?)";
//
//        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
//
//        preparedStatement.setString(1, user.getUsername());
//        preparedStatement.setDate(2, user.getBirthdate());
//
//        preparedStatement.executeUpdate();
//
//    }
    public void createUserList(UserList userList) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO " + Const.LIST_TABLE  + "(" + Const.USER_ID+ "," + Const.USER_LIST_NAME + ")VALUES(?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        System.out.println(userList.getUserId());

        preparedStatement.setInt(1, userList.getUserId());
        preparedStatement.setString(2, userList.getListName());

        preparedStatement.executeUpdate();
    }
    public ResultSet getTaskList(int listId) throws SQLException, ClassNotFoundException {
        String query = " SELECT * FROM " + Const.TASK_TABLE + " WHERE " + Const.USER_LIST_ID + "=" + listId;

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }
    public void createNewTask(Task task) throws SQLException, ClassNotFoundException {
        String query = " INSERT INTO " + Const.TASK_TABLE + "(" + Const.USER_LIST_ID + "," + Const.TASK_NAME + "," + Const.TASK_DESCRIPTION +
                "," + Const.TASK_DATECREATED + "," + Const.TASK_DATEBEFORE + "," + Const.TASK_PRIORITY + "," + Const.TASK_TIMEBEFORE + ")" + "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        preparedStatement.setInt(1,task.getUserListId());
        preparedStatement.setString(2,task.getTaskName());
        preparedStatement.setString(3,task.getTaskDescription());
        preparedStatement.setTimestamp(4,task.getDateCreated());
        preparedStatement.setDate(5,task.getDateBefore());
        preparedStatement.setString(6,task.getPriority());
        preparedStatement.setString(7,task.getTimeBofre());

        preparedStatement.executeUpdate();
    }

    public ResultSet getHighTask(int listId) throws SQLException, ClassNotFoundException {
        String query = " SELECT * FROM " + Const.TASK_TABLE + " WHERE " + Const.USER_LIST_ID + "=" + listId + " AND " + Const.TASK_PRIORITY +
                "=" + "'High'";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    public void removeTask(int taskId) throws SQLException, ClassNotFoundException {
        String query = " DELETE FROM " + Const.TASK_TABLE + " WHERE " + Const.TASK_ID+ "=" + taskId;

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public void removeUser(int userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = getListItems(userId);

        while(resultSet.next()){
            String query3 = " DELETE FROM " + Const.TASK_TABLE + " WHERE " + Const.USER_LIST_ID + "=" + resultSet.getInt("userlistid");
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query3);

            preparedStatement.executeUpdate();
            //preparedStatement.close();
        }

        String query2 = " DELETE FROM " + Const.LIST_TABLE + " WHERE " + Const.USER_ID + "=" + userId;
        String query = " DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID+ "=" + userId;

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query2);
        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(query);

        preparedStatement.executeUpdate();
        preparedStatement.close();

        preparedStatement1.executeUpdate();
        preparedStatement1.close();
    }

    public void updateTask(Task task) throws SQLException, ClassNotFoundException {
        String query = " UPDATE " + Const.TASK_TABLE + " SET "  + Const.TASK_NAME + "= ?," + Const.TASK_DESCRIPTION + "= ?," + Const.TASK_DATECREATED
                +"= ?, " + Const.TASK_DATEBEFORE + "= ?, " + Const.TASK_PRIORITY + "= ?, " + Const.TASK_TIMEBEFORE + "= ? "+" WHERE " + Const.TASK_ID + "= " + task.getTaskId();

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        preparedStatement.setString(1,task.getTaskName());
        preparedStatement.setString(2,task.getTaskDescription());
        preparedStatement.setTimestamp(3,task.getDateCreated());
        preparedStatement.setDate(4,task.getDateBefore());
        preparedStatement.setString(5,task.getPriority());
        preparedStatement.setString(6,task.getTimeBofre());

        preparedStatement.executeUpdate();

    }
    public void removeUserList(int userListID) throws SQLException, ClassNotFoundException {

        String query = " DELETE FROM " + Const.LIST_TABLE + " WHERE " + Const.USER_LIST_ID + "=" + userListID;

        ResultSet resultSet = getTaskList(userListID);

        while(resultSet.next()){
            String query2 = " DELETE FROM " + Const.TASK_TABLE + " WHERE " + Const.USER_LIST_ID + "=" + userListID;

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query2);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

        preparedStatement.executeUpdate();
        preparedStatement.close();

//        ResultSet resultSet = getListItems(userListID);
//
//        while(resultSet.next()){
//            String query3 = " DELETE FROM " + Const.TASK_TABLE + " WHERE " + Const.USER_LIST_ID + "=" + resultSet.getInt("userlistid");
//            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query3);
//
//            preparedStatement.executeUpdate();
//            //preparedStatement.close();
//        }
//
//        String query2 = " DELETE FROM " + Const.LIST_TABLE + " WHERE " + Const.USER_ID + "=" + userListID;
//        String query = " DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID+ "=" + userListID;
//
//        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query2);
//        PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(query);
//
//        preparedStatement.executeUpdate();
//        preparedStatement.close();
//
//        preparedStatement1.executeUpdate();
//        preparedStatement1.close();
    }




}
