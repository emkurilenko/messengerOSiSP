package userAction;

import sample.User;

import java.util.ArrayList;
import java.util.LinkedList;

public class AllUsers {
    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User user){
        users.add(user);
    }

    public void deleteUser(User user){
        users.remove(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
