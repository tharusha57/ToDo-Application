package sample.model;

public class UserList {
    private int userId;
    private String listName;
    private int userListId;

    public int getUserListId() {
        return userListId;
    }

    public void setUserListId(int userListId) {
        this.userListId = userListId;
    }

    public void userList(){

    }
    public UserList( int userId, String listName) {
        this.userId = userId;
        this.listName = listName;
    }

    public UserList( int userId, String listName,int userListId) {
        this.userId = userId;
        this.listName = listName;
        this.userListId = userListId;
    }
    public UserList( String listName) {
        this.listName = listName;
    }

    public UserList() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
