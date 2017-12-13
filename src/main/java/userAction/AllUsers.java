package userAction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sample.Const;
import sample.CookiesWork;
import sample.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AllUsers {
    ArrayList<User> usersList = new ArrayList<>();

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public void addUser(User user){
        usersList.add(user);
    }

    public void deleteUser(User user){
        usersList.remove(user);
    }

    public void checkInServer(){
        GetUserInformation getUserInformation = new GetUserInformation();
        HttpURLConnection connection = null;
        URL url;
        int code;
        try {
            url = new URL(Const.URL + "?operation=search");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String json = in.readLine();
            in.close();
            connection.disconnect();
            usersList = new Gson().fromJson(json, new TypeToken<ArrayList<User>>(){}.getType());
        }
        catch (Exception e){
        }
        finally {
            if(connection!=null)
                connection.disconnect();
        }

        for (User us:
             usersList) {
            getUserInformation.getPicture(us,false);
        }
    }
}
