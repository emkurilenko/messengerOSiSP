package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import userAction.*;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML private ImageView imageUser;
    @FXML private Text textLogin;
    @FXML private Text textName;
    @FXML private JFXButton btnDialog;
    @FXML private TextField textFieldSearch;
    @FXML private JFXButton btnSearch;
    @FXML private ListView<?> listUser;
    @FXML private JFXButton btnContacts;
    private AllUsers allUsers;

    @FXML
    void btnClickContacts(MouseEvent event) {
        Platform.runLater(()->{
            ObservableList<User> usersList = FXCollections.observableList(allUsers.getUsers());
            listUser.setItems(usersList);
            listUser.setCellFactory(new CellRenderer());
        });
    }

    private void getInformation(GetProfileInfo getProfileInfo){
            User user = getProfileInfo.getProfile(CookiesWork.cookie);
            Image image = SwingFXUtils.toFXImage(user.getPicture(), null);
            imageUser.setImage(image);
            textLogin.setText(user.getLogin());
            textName.setText(user.getName()+" "+user.getSurname());
    }

    @FXML
    void clickBtnDialog(MouseEvent event) {
        Platform.runLater(()->{
            GetDialog getDialog = new GetDialog();
            ObservableList<Dialog> dialogs = FXCollections.observableList(getDialog.getDialogs());
            dialogs.setItem()
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GetProfileInfo getProfileInfo = new GetProfileInfo();
        getInformation(getProfileInfo);
        allUsers = new AllUsers();
        GetAllUsers getAllUsers = new GetAllUsers();
        getAllUsers.getAllUsers(allUsers);
        System.out.println(allUsers.getUsers().size());
        System.out.println("Hello woeld");
    }



    /*@FXML
    private ImageView idImageLogin;

    @FXML
    private Text idLogin;

    @FXML
    private Text idNameSurname;

    @FXML
    private JFXTextField searchUser;

    @FXML
    private ListView<User> listUser;

    @FXML
    private VBox actionCompanion;

    @FXML
    private HBox infCompanion;

    @FXML
    private ImageView imgCompanion;

    @FXML
    private Text infLoginCompanion;

    @FXML
    private Text infNameCompanion;

    @FXML
    void btnSignOut(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserInfo();
 setListUser();
    }

    public void setListUser(){
        Platform.runLater(()->{
            GetProfileInfo getProfileInfo = new GetProfileInfo();
            ArrayList<User> users = new ArrayList<>();
            ArrayList<User> bufUsers = null;
                        HttpURLConnection connection = null;
            URL url;
            try {
                url = new URL(Const.URL + "?operation=search");
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Cookie", CookiesWork.cookie);
                int code = connection.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), "windows-1251"));
                String json = in.readLine();
                in.close();
                connection.disconnect();
                bufUsers = new Gson().fromJson(json, new TypeToken<ArrayList<User>>(){}.getType());
            }
            catch (Exception e){
            }
            finally {
                if(connection!=null)
                    connection.disconnect();
            }


           for (User us:
                 bufUsers) {
               try {
                System.out.println(us);
               String nameFile = getClass().getClassLoader().getResource("img/default.png").toString();
               InputStream ins = null;
               ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
               BufferedImage buf = ImageIO.read(ins);
                us.setPicture(buf);
               } catch (Exception e) {
                   e.printStackTrace();
               }
               *//*try {
                   String str = Const.URL + "?operation=profile&type=image&login=" + us.getLogin();
                   url = new URL(str);
                   connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setRequestProperty("Cookie", CookiesWork.cookie);
                   int codeImage = connection.getResponseCode();
                   if (HttpURLConnection.HTTP_OK == codeImage) {
                       InputStream is = connection.getInputStream();
                       BufferedImage bi = ImageIO.read(is);
                       if (bi == null) {
                           String nameFile = getClass().getClassLoader().getResource("img/default.png").toString();
                           InputStream ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
                           us.setPicture(ImageIO.read(ins));
                           ins.close();
                           is.close();
                           continue;
                       }
                       us.setPicture(Compression.compress(bi,0.2f));
                       is.close();
                       connection.disconnect();
                   } else {
                       String nameFile = getClass().getClassLoader().getResource("img/default.png").toString();
                       InputStream ins = new FileInputStream(nameFile.substring(6, nameFile.length()));
                       us.setPicture(ImageIO.read(ins));
                       ins.close();
                   }
               }catch (Exception e){
               }
                finally {
                   if(connection!=null)
                       connection.disconnect();
               }
              //  users.add(getProfileInfo.getProfile(us.getLogin()));
            }*//*
           }
            ObservableList<User> usersList = FXCollections.observableList(bufUsers);
            listUser.setItems(usersList);
            listUser.setCellFactory(new CellRenderer());
           // System.out.println(usersList.size());
        });
    }

    public void setUserInfo(){
        GetProfileInfo getProfileInfo = new GetProfileInfo();
        User user = getProfileInfo.getProfile(CookiesWork.cookie);
        Image image = SwingFXUtils.toFXImage(user.getPicture(), null);
        idImageLogin.setImage(image);
        idLogin.setText(user.getLogin());
        idNameSurname.setText(user.getName()+" " + user.getSurname());
    }*/
}
