package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class RegisterController {
    private HttpURLConnection connection = null;
    final private String URL = "http://localhost:8080/mess/";
    File fileImage;

    @FXML
    private JFXTextField idLogin;

    @FXML
    private JFXPasswordField idPass;

    @FXML
    private JFXTextField idName;

    @FXML
    private JFXTextField idSecondName;

    @FXML
    private JFXButton btnImageChoise;

    @FXML
    private ImageView img;

    @FXML
    private JFXButton btnRegister;

    @FXML
    void btnCancel(MouseEvent event) throws IOException {
        loginScene(event,true);
    }
/*?login=" + idLogin.getText().toString() + "&password="+idPass.getText().toString()
                    +"&name="+idName.getText().toString();*/

    @FXML
    void btnRegister(MouseEvent event) throws Exception {
        try {
            byte[] imageInByte;
            BufferedImage original = ImageIO.read(fileImage);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(original,"jpg",baos);
            baos.flush();
            imageInByte =baos.toByteArray();
            baos.close();
            /*InputStream inImage = new ByteArrayInputStream(baos.toByteArray());

            BufferedImage bImageFromConvert = ImageIO.read(inImage);

            ImageIO.write(bImageFromConvert,"jpg",new File("D:\\1.jpg"));*/

            String url = URL +"register";

            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDefaultUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty( "charset", "utf-8");
            connection.setRequestProperty( "Content-Length", Integer.toString(imageInByte.length ));
            connection.connect();


            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(imageInByte);
            wr.flush();
            wr.close();


            System.out.println(url);

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
            if (connection != null) {
                connection.disconnect();
            }
        }


        //loginScene(event,false);
    }


    @FXML
    void btnChoice(MouseEvent event) {
        FileChooser fc =new FileChooser();
        fc.setTitle("Choice picture");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fc.getExtensionFilters().addAll(extFilterJPG);
        fileImage = fc.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(fileImage);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            img.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loginScene(MouseEvent event, boolean cancel) throws IOException {
      //  (((Node)event.getSource()).getScene()).getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginScene.fxml"));
        Parent parent = loader.load();
        //Parent parent = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
        Main.primaryStage.setScene(new Scene(parent));
        LoginController loginController = loader.getController();
        loginController.setLoginUser(idLogin.getText());
        loginController.setPassLogin(idPass.getText());
        Main.primaryStage.show();
        /*Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);

        stage.show();*/
    }
}
