package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStageObj;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStageObj = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/loginScene.fxml"));
        primaryStage.setTitle("MessengerPSU");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
        Scene mainScene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void stop(){


        //File file = new File("src/main/resources/cache/");
       // deleteDirectory(file);

    }

    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else dir.delete();
    }
}
