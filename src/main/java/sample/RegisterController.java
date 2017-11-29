package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
    private File fileImage;
    private boolean check;
    private String login;
    private String password;
    private String name;
    private String secondName;

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

    @FXML
    public void initialize() {
       /* btnRegister.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Превышение допустимого значения");
        idLogin.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>40){
                alert.showAndWait();

            }
        });
        idPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if(idPass.getText().length()>5){
                alert.showAndWait();
                idPass.clear();
            }
        });
        idName.textProperty().addListener((observable, oldValue, newValue)->{
            if(newValue.length()>40){
                alert.showAndWait();
            }
        });
        idSecondName.textProperty().addListener((observable, oldValue, newValue)->{
            if(newValue.length()>40){
                alert.showAndWait();
            }
        });*/
    }

    @FXML
    void btnRegister(MouseEvent event) throws Exception {
        name = idName.getText();
        secondName = idSecondName.getText();
        login = idLogin.getText();
        password = idPass.getText();

        if(name.isEmpty() || secondName.isEmpty() || login.isEmpty() || password.isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setHeaderText(null);
            alert1.setContentText("Одно из полей - пустое.");
            alert1.showAndWait();
            return;
        }

        name = deleteSpace(name);
        secondName = deleteSpace(secondName);
        login = deleteSpace(login);
        password = deleteSpace(password);
        name = firstUpperCase(name);
        secondName = firstUpperCase(secondName);

        if (check &&(!checkForInputName(name) || !checkForInputName(secondName))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Ошибка ввода.");
            alert.showAndWait();
            return;
        }

            if (fileImage != null) {
                try {
                    byte[] imageInByte;
                    BufferedImage original = ImageIO.read(fileImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(original, "jpg", baos);
                    baos.flush();
                    imageInByte = baos.toByteArray();
                    baos.close();

                    String url = URL + "register";
                    System.out.println(url + " 1");
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDefaultUseCaches(false);
                    connection.setConnectTimeout(250);
                    connection.setReadTimeout(250);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(imageInByte.length));
                    connection.connect();


                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.write(imageInByte);
                    wr.flush();
                    wr.close();



                    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line = in.readLine();

                        if (line != null) {
                            System.out.println(line);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Проблема с загрузкой файла!");
                            alert.setHeaderText("Ошибка!");
                            alert.showAndWait();
                        }
                        in.close();
                    } else {
                        System.out.println("fail " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Ошибка " + connection.getResponseCode());
                        alert.setHeaderText("Ошибка!");
                        alert.showAndWait();
                    }
                } catch (Throwable cause) {
                    cause.getStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

            try {
                String url = URL + "register?login=" +login + "&password=" + password +
                        "&name=" + name + "&surname=" + secondName;
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setDefaultUseCaches(false);
                connection.setConnectTimeout(250);
                connection.setReadTimeout(250);

                connection.connect();

                System.out.println(url);
                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = in.readLine();
                    if(line.equals("Exist")){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Данный логин уже занят! Попробуйте другой");
                        alert.showAndWait();
                        return;
                    }
                    if (line != null) {
                        System.out.println(line);
                        System.out.println("123");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Проблема с передачей данных");
                    alert.setHeaderText("Ошибка!");
                    alert.showAndWait();
                }
                    in.close();
                } else {
                    System.out.println("fail " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Ошибка:" + connection.getResponseCode());
                    alert.setHeaderText("Ошибка!");
                    alert.showAndWait();
                }

            } catch (Throwable cause) {
                cause.getStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            loginScene(event, false);
    }


    @FXML
    void btnChoice(MouseEvent event) {
        FileChooser fc =new FileChooser();
        fc.setTitle("Choice picture");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fc.getExtensionFilters().addAll(extFilterJPG);
        fileImage = fc.showOpenDialog(null);
        if(fileImage!=null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(fileImage);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                img.setImage(image);
            } catch (IOException ex) {
                //ex.printStackTrace();
                fileImage = null;
            }
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

    private boolean checkSpace(String str){
        if(str.contains(" ")){
            return false;
        }else
            return true;
    }

    private boolean checkForInputName(String name){
        if(name == null){
            return false;
        }
        if(name.length()>40)
            return false;
        for(int i = 0; i < name.length();i++){
            Character ch = name.charAt(i);
            if(!Character.isLetter(ch))
                return false;
        }
        return checkSpace(name);
    }

    private String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private String deleteSpace(String str){
        return str.replaceAll(" ","");
    }

}
