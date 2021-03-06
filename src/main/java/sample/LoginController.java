package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController{
    private HttpURLConnection connection = null;
    private Scene scene;

    @FXML
    private JFXTextField loginUser;
    @FXML private JFXPasswordField passLogin;
    @FXML private JFXButton btnLogin;
    @FXML private JFXButton btnRegister;



    @FXML
    void bntClickLogin(MouseEvent event) {
        if(!Const.checkConnection()){
            Const.showErrorDialog("Error connection!", "No connection to the server.");
            return;
        }

        String login = loginUser.getText();
        String password = passLogin.getText();
        System.out.println(login);
        System.out.println(password);

        if(login.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Введите данные! ");
            alert.showAndWait();
            return;
        }

       /* if(!CheckInput.checkForLogin(login) || !CheckInput.checkPassword(password)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Ошибка ввода!");
            alert.setContentText("Логин: Символов>2. Только буквы и цифры");
            alert.showAndWait();
            return;
        }*/

        try {
            String url = Const.URL + "?operation=login&login=" + URLEncoder.encode(login,"UTF-8") + "&password=" + URLEncoder.encode(password,"UTF-8");
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDefaultUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                List<String> cookies = connection.getHeaderFields().get(CookiesWork.COOKIES_HEADER);
                String[] vals = cookies.get(0).split("=");
                CookiesWork.cookie = vals[1];
                connection.disconnect();

                (((Node)event.getSource()).getScene()).getWindow().hide();
                FXMLLoader fmxlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/chatScene.fxml"));
                Parent window = (Pane) fmxlLoader.load();
                Scene scene = new Scene(window);
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
                stage.setTitle("MessengerPSU");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                //Смена Активити
            }else if(connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND){
                Const.showErrorDialog(null,"User not found/password field.");
                return;
            }
            System.out.println(connection.getResponseCode());
            System.out.println(CookiesWork.cookie);
        }catch (Throwable cause){
            cause.getStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

    @FXML
    void btnClickRegister(MouseEvent event) throws IOException {
        if(!Const.checkConnection()){
            Const.showErrorDialog("Error connection!", "No connection to the server.");
            return;
        }
       (((Node)event.getSource()).getScene()).getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("views/registerScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
       // Main.primaryStage.setScene(new Scene(parent));
       // Main.primaryStage.show();
    }





}
