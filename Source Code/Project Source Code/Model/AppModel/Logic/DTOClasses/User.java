package Model.AppModel.Logic.DTOClasses;

import java.util.List;

public class User {
    private int userID;
    private String username;
    private String password;
    private String name;
    private Icon icon;

    private List<Playlist> playlistsList;

    public User(String username) {
        this.username = username;
    }

    public User(int userID, String username, String password, String name, Icon icon) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.icon = icon;
    }

    public User(String username, String password, String name, Icon icon) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.icon = icon;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }



    public List<Playlist> getPlaylistsList() {
        return playlistsList;
    }

    public void setPlaylistsList(List<Playlist> playlistsList) {
        this.playlistsList = playlistsList;
    }


}
