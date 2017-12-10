package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

class CellRenderer implements Callback<ListView<User>,ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>(){

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    try {
                        HBox hBox = new HBox();

                    Text name = new Text(user.getName()+" "+user.getSurname());



                    ImageView pictureImageView = new ImageView();
                    pictureImageView.setFitHeight(35);
                    pictureImageView.setFitHeight(35);
                    pictureImageView.setSmooth(true);
                    pictureImageView.setPreserveRatio(true);
                    Image image=null;
                    image = SwingFXUtils.toFXImage(user.getPicture(), null);

                    pictureImageView.setImage(image);

                   hBox.getChildren().addAll(pictureImageView, name);
                    //hBox.getChildren().addAll(name);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return cell;
    }
}