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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class RegisterController {

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
    void btnRegister(MouseEvent event) throws IOException {
        loginScene(event,false);
    }


    @FXML
    void btnChoice(MouseEvent event) {
        FileChooser fc =new FileChooser();
        fc.setTitle("Choice picture");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fc.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
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
