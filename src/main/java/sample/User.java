package sample;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    //private byte[] picture;
    private BufferedImage picture;

    public User() {
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

    public User(String login, String name, String surname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        //this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public void setPicture(BufferedImage picture) {
        this.picture = picture;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
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