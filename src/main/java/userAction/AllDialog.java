package userAction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sample.Compression;
import sample.Const;
import sample.CookiesWork;
import sample.Dialog;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AllDialog {
    private ArrayList<Dialog> dialogs = new ArrayList<>();

    public AllDialog() {
        getImage();
    }

    private BufferedImage buffi;

    public ArrayList<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(ArrayList<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public void addDialog(Dialog dialog){
        dialogs.add(dialog);
    }

    public void getDialog(){
        HttpURLConnection connection = null;
        URL url;
        int code;
        try {
            url = new URL(Const.URL + "?operation=dialogs");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String json = in.readLine();
            in.close();
            connection.disconnect();
            dialogs = new Gson().fromJson(json, new TypeToken<ArrayList<sample.Dialog>>(){}.getType());
        } catch (Exception e) {
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        for (Dialog dlg:
             dialogs) {
            getPicture(dlg);
        }
        return;
    }

    public void getPicture(Dialog dialog){
        URL url;
        HttpURLConnection connection = null;
        int code;
        int codeImage;
        try {
            String str = Const.URL + "?operation=profile&type=image&login=" + dialog.getSecond();
            url = new URL(str);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            codeImage = connection.getResponseCode();
            if (codeImage == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedImage bi = ImageIO.read(is);
                if (bi == null) {
                    dialog.setPicture(buffi);
                    is.close();
                    connection.disconnect();
                    return;
                } else {
                    dialog.setPicture(Const.createResizedCopy(Compression.compress(bi, 0.5f),
                            40, 40, true));
                }
            } else {
                dialog.setPicture(buffi);
            }
            connection.disconnect();
        } catch (Exception e) {
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
    public void getImage() {
        try {
            String nameFile = getClass().getClassLoader().getResource("images/default.png").toString();
            InputStream is = new FileInputStream(nameFile.substring(6, nameFile.length()));
            buffi = ImageIO.read(is);
            is.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
