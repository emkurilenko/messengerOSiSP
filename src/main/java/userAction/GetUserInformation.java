package userAction;

import com.google.gson.Gson;
import sample.Compression;
import sample.Const;
import sample.CookiesWork;
import sample.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUserInformation{

    private BufferedImage buffi;

    public GetUserInformation() {
        getImage();
    }

    public User getInformation(String login) {
        User user = null;
        URL url;
        HttpURLConnection connection = null;
        int code;
        int codeImage;
        try {
            String str = Const.URL + "?operation=profile&type=info&login=" + login;
            url = new URL(str);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            connection.setRequestProperty("Cache-Control", "no-cache");
            code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String inputLine = in.readLine();
            user = new Gson().fromJson(inputLine, User.class);
            in.close();
            connection.disconnect();

        } catch (Exception e) {
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return user;
    }

    public void getPicture(User user, boolean down) {
        URL url;
        HttpURLConnection connection = null;
        int code;
        int codeImage;
        try {
            String str = Const.URL + "?operation=profile&type=image&login=" + user.getLogin();
            url = new URL(str);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            codeImage = connection.getResponseCode();
            if (codeImage == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedImage bi = ImageIO.read(is);
                if (bi == null) {
                    user.setPicture(buffi);
                    is.close();
                    connection.disconnect();
                    return;
                } else {
                    if (user.getLogin().equals(CookiesWork.cookie) || down) {
                        // user.setPicture(Compression.compress(bi,0.9f));
                        user.setPicture(bi);
                        is.close();
                        connection.disconnect();
                        return;
                    }
                    user.setPicture(Const.createResizedCopy(Compression.compress(bi, 0.5f),
                            40, 40, true));
                }
            } else {
                user.setPicture(buffi);
            }
            connection.disconnect();
        } catch (Exception e) {
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    private void getImage() {
        try {
            String nameFile = getClass().getClassLoader().getResource("images/default.png").toString();
            InputStream is = new FileInputStream(nameFile.substring(6, nameFile.length()));
            buffi = ImageIO.read(is);
            is.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }


    public static BufferedImage getPicture(String login){
        URL url;
        HttpURLConnection connection = null;
        int code;
        int codeImage;
        BufferedImage bi = null;
        try {
            String str = Const.URL + "?operation=profile&type=image&login=" + login;
            url = new URL(str);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            codeImage = connection.getResponseCode();
            if (codeImage == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                bi = ImageIO.read(is);
                if(bi==null){
                    InputStream isa = new FileInputStream("src/main/resources/images/default.png");
                    System.out.println("file found");
                    bi = ImageIO.read(isa);
                }
            }else{

            }
            connection.disconnect();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        catch (Exception e) {
        }
        return bi;
    }

}
