package sample;

import java.io.InputStream;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String picture;

    public User(int id, String login, String password, String name, String surname, String picture) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.picture = picture;
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String name, String surname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public User(String login, String password, String name, String surname, String picture) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.picture = picture;
    }



    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
