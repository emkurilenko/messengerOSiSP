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

public class GetDialog {

    private ArrayList<Dialog> dialogs;
    public GetDialog() {
        dialogs = new ArrayList<>();
        getDialog();
    }





    private void getDialog(){
        HttpURLConnection connection = null;
        URL url;
        try {
            url = new URL(Const.URL + "?operation=dialogs");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            int code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String json = in.readLine();
            in.close();
            connection.disconnect();
            dialogs = new Gson().fromJson(json, new TypeToken<ArrayList<Dialog>>(){}.getType());
        }
        catch (Exception e){
        }
        finally {
            if(connection!=null)
                connection.disconnect();
        }

        for (Dialog dialog: dialogs) {
            try {
                String str = Const.URL + "?operation=profile&type=image&login=" + dialog.getSecond();
                url = new URL(str);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Cookie", CookiesWork.cookie);
                int code = connection.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedImage bi = ImageIO.read(is);
                    if (bi == null) {
                        String nameFile = getClass().getClassLoader().getResource("images/default.png").toString();
                        InputStream ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
                        dialog.setPicture(Compression.compress(ImageIO.read(ins), 0.2f));
                        ins.close();
                        is.close();
                        continue;
                    }
                    dialog.setPicture(Compression.compress(bi, 0.2f));
                    is.close();
                } else {
                    String nameFile = getClass().getClassLoader().getResource("img/default.png").toString();
                    InputStream ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
                    dialog.setPicture(Compression.compress(ImageIO.read(ins), 0.2f));
                    ins.close();
                }
            } catch (Exception e) {
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        }
    }

    public ArrayList<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(ArrayList<Dialog> dialogs) {
        this.dialogs = dialogs;
    }
}
