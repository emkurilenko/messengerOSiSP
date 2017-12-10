package userAction;

import com.google.gson.Gson;
import javafx.embed.swing.SwingFXUtils;
import org.apache.commons.io.IOUtils;
import sample.*;

import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetProfileInfo {
    private BufferedImage buffi;

    public GetProfileInfo() {
        getImage();
    }

    public User getProfile(String login){
        User user = null;
        HttpURLConnection connection = null;
        URL url;
        try {
            String str = Const.URL + "?operation=profile&type=info&login=" + login;
            url = new URL(str);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            int code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String inputLine = in.readLine();
            user = new Gson().fromJson(inputLine, User.class);
            in.close();
            connection.disconnect();
        }
        catch (Exception e){
        }
        finally {
            if(connection!=null)
                connection.disconnect();
        }
        try {
            String str = Const.URL + "?operation=profile&type=image&login=" + login;
            System.out.println(login);
            url = new URL(str);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            int codeImage = connection.getResponseCode();
            if(HttpURLConnection.HTTP_OK == codeImage) {
                InputStream is = connection.getInputStream();
                BufferedImage bi = ImageIO.read(is);
                if(bi == null){
                    System.out.println("Comon");
                    user.setPicture(buffi);
                    is.close();
                    return user;
                }
                user.setPicture(Compression.compress(bi,0.5f));
                is.close();
                connection.disconnect();
            }else {
                user.setPicture(buffi);
            }
        }
        catch (Exception e){
        }
        finally {
            if(connection!=null)
                connection.disconnect();
        }
        return user;
    }

    private  void getImage(){
        try {
            String nameFile = getClass().getClassLoader().getResource("img/default.png").toString();
            InputStream is = new FileInputStream(nameFile.substring(6, nameFile.length()));
            buffi = ImageIO.read(is);
            is.close();
        }catch (Exception e){
            System.out.println("File not found");
        }
    }
}
