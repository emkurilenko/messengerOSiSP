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

public class CellRenderDialog implements Callback<ListView<Dialog>, ListCell<Dialog>> {
    @Override
    public ListCell<Dialog> call(ListView<Dialog> p) {

        ListCell<Dialog> cell = new ListCell<Dialog>() {

            @Override
            protected void updateItem(Dialog dialog, boolean bln) {
                super.updateItem(dialog, bln);
                setGraphic(null);
                setText(null);
                if (dialog != null) {
                    try {
                        HBox vBox = new HBox();

                        Text name = new Text(dialog.getSecond());
                        ImageView imageView = new ImageView();
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        imageView.setSmooth(true);
                        imageView.setPreserveRatio(true);
                        Image image = SwingFXUtils.toFXImage(dialog.getPicture(), null);
                        imageView.setImage(image);
                        vBox.getChildren().addAll(imageView, name);
                        vBox.setAlignment(Pos.CENTER_LEFT);
                        setGraphic(vBox);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return cell;
    }
}
