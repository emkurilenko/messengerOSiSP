package userAction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sample.Compression;
import sample.Const;
import sample.CookiesWork;
import sample.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class GetAllUsers {
    public void getAllUsers(AllUsers allUsers){
        ArrayList<User> users = null;
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(Const.URL + "?operation=search");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            int code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String json = in.readLine();
            in.close();
            connection.disconnect();
            users = new Gson().fromJson(json, new TypeToken<ArrayList<User>>(){}.getType());
        }
        catch (Exception e){
        }
        finally {
            if(connection!=null)
                connection.disconnect();
        }
        for (User user:
             users) {
            if(user.getLogin()==CookiesWork.cookie){
                users.remove(user);
                continue;
            }
            try {
                String str = Const.URL + "?operation=profile&type=image&login=" + user.getLogin();
                url = new URL(str);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Cookie", CookiesWork.cookie);
                int codeImage = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == codeImage) {
                    InputStream is = connection.getInputStream();
                    BufferedImage bi = ImageIO.read(is);
                    if (bi == null) {
                        String nameFile = getClass().getClassLoader().getResource("images/default.png").toString();
                        InputStream ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
                        user.setPicture(Compression.compress(ImageIO.read(ins),0.2f));
                        ins.close();
                        is.close();
                        continue;
                    }
                    user.setPicture(Compression.compress(bi, 0.2f));
                    is.close();
                    connection.disconnect();
                } else {
                    String nameFile = getClass().getClassLoader().getResource("img/default.png").toString();
                    InputStream ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
                    user.setPicture(Compression.compress(ImageIO.read(ins),0.2f));
                    ins.close();
                }
            } catch (Exception e) {
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        }

        allUsers.setUsers(users);
        //users.clear();
        //users = null;
    }
}
