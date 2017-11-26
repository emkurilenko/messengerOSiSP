package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.omg.CORBA.Request;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    private HttpURLConnection connection = null;
    final private String URL = "http://localhost:8080/messenger/";


    @FXML
    private JFXTextField loginUser;

    public void setLoginUser(String loginUser) {
        this.loginUser.setText(loginUser);
    }

    public void setPassLogin(String passLogin) {
        this.passLogin.setText(passLogin);
    }

    @FXML
    private JFXPasswordField passLogin;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnRegister;



    @FXML
    void bntClickLogin(MouseEvent event) {
        try {
            String url = URL + "login?name=" + loginUser.getText().toString() + "&password=" + passLogin.getText().toString();
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDefaultUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();
            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = in.readLine();

                System.out.println(line);
            }else{
                System.out.println("fail "+connection.getResponseCode() + ", " +connection.getResponseMessage());

            }

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
       // (((Node)event.getSource()).getScene()).getWindow().hide();
        /*Parent parent = FXMLLoader.load(getClass().getResource("/registerScene.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerScene.fxml"));
        Parent parent = loader.load();
        Main.primaryStage.setScene(new Scene(parent));
        Main.primaryStage.show();
    }

}
