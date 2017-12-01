package sample;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ChatSceneController implements Initializable{
    private HttpURLConnection connection = null;
    @FXML private javafx.scene.image.ImageView idImage;
    @FXML private Text idLoginText;
    @FXML private Text idNameSurnameText;
    private ArrayList<User> userlist;
    @FXML private JFXListView userList;

    @FXML void btnSngOut(MouseEvent event) {
        Platform.runLater(()->{
            (((Node)event.getSource()).getScene()).getWindow().hide();
            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
        });
    }

    private void setUserList(){
        Platform.runLater(() -> {
            ObservableList<User> users = FXCollections.observableList(userlist);
            userList.setItems(users);
        }
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        userlist = new ArrayList<>();
        userlist.add(new User("ior","1234","asd","lasd"));

        String url = Const.URL + "?operation=profile";

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDefaultUseCaches(false);
            connection.setRequestProperty("Content-type","application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            System.out.println(CookiesWork.cookie);
            connection.connect();
            System.out.println("connect Scene");
            int code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = in.readLine();
            in.close();
            System.out.println(line);
            User user = new Gson().fromJson(line,User.class);

            setPersonInformation(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPersonInformation(User user){
        try {
            idNameSurnameText.setText(user.getName() + " "+user.getSurname());
            idLoginText.setText(user.getLogin());
            InputStream inputStream = Const.convertStringToIS(user.getPicture());
            System.out.println(inputStream);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            idImage.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
