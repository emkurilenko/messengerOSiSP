package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import userAction.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private ImageView imageUser;

    @FXML
    private Text textLogin;
    @FXML
    private Text textName;
    @FXML
    private Button btnDialog;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnContacts;
    @FXML
    private ImageView imgSobes;
    @FXML
    private Text textLoginSobes;
    @FXML
    private VBox vBoxCenter;
    @FXML
    private ListView<User> firstListViewUser;
    @FXML
    private ListView<Dialog> secondListViewDialog;
    @FXML
    private ListView<Message> chatPane;
    @FXML
    private TextArea messageBox;
    @FXML
    private JFXButton buttonSend;

    private AllUsers allUsers;

    private CellRenderUser renderUser;
    private String request;
    private ArrayList<Message> bufMessages;

    private ObservableList<User> usersList;
    private ObservableList<Dialog> dialogsList;
    private ObservableList<Message> messagesList;
    private String secondUserLogin;

    @FXML
    void sendButtonAction(ActionEvent event) {

    }

    @FXML
    void sendMethod(KeyEvent event) {

    }

    @FXML
    void btnSendMessage(MouseEvent event) {
        String message = messageBox.getText();
        if (!message.isEmpty()) {
            sendMessage(message, secondUserLogin);
            messageBox.clear();
        }
    }

    @FXML
    void clickBtnDialog(MouseEvent event) {
        Platform.runLater(() -> {
            firstListViewUser.setVisible(false);
            secondListViewDialog.setVisible(true);

            getAllDialog();
        });
    }


    @FXML
    void btnClickContacts(MouseEvent event) {
        Platform.runLater(() -> {
            firstListViewUser.setVisible(true);
            secondListViewDialog.setVisible(false);
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAllDialog();
        GetUserInformation getUserInformation = new GetUserInformation();
        User user = getUserInformation.getInformation(CookiesWork.cookie);
        getUserInformation.getPicture(user, true);
        textLogin.setText(user.getLogin());
        textName.setText(user.getName() + " " + user.getSurname());
        Image image = SwingFXUtils.toFXImage(user.getPicture(), null);
        imageUser.setImage(image);


        vBoxCenter.setVisible(false);
        getAllUser();

    }

    @FXML
    void firstListViewUserListener(MouseEvent event) {
        vBoxCenter.setVisible(true);
        secondUserLogin = firstListViewUser.getSelectionModel().getSelectedItem().getLogin();
        System.out.println("click: " + firstListViewUser.getSelectionModel().getSelectedItem());

        setInformationSobes(secondUserLogin);

        getMessage(secondUserLogin);
        messagesList = FXCollections.observableList(bufMessages);
        chatPane.setItems(messagesList);

    }

    @FXML
    void secondListViewDialogListener(MouseEvent event) {
        vBoxCenter.setVisible(true);
        secondUserLogin = secondListViewDialog.getSelectionModel().getSelectedItem().getSecond();
        System.out.println("click: " + secondListViewDialog.getSelectionModel().getSelectedItem());

        setInformationSobes(secondUserLogin);

        getMessage(secondUserLogin);
        System.out.println(secondUserLogin);
        messagesList = FXCollections.observableList(bufMessages);
        chatPane.setItems(messagesList);
    }

    private void getAllUser() {
        Thread th = new Thread(() -> {
            allUsers = new AllUsers();
            allUsers.checkInServer();

            usersList = FXCollections.observableList(allUsers.getUsersList());
            firstListViewUser.setItems(usersList);
            firstListViewUser.setCellFactory(new CellRenderUser());
        });
        th.start();
    }

    private void getAllDialog() {
        Thread th = new Thread(() -> {
            AllDialog allDialog = new AllDialog();
            allDialog.getDialog();

            dialogsList = FXCollections.observableList(allDialog.getDialogs());
            secondListViewDialog.setItems(dialogsList);
            secondListViewDialog.setCellFactory(new CellRenderDialog());
        });
        th.start();
    }

    private void sendMessage(String message, String secondUser) {
        Platform.runLater(() -> {
            HttpURLConnection connection = null;
            URL url;
            int code;
            try {
                url = new URL(Const.URL + "?operation=sendmessage");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Cookie", CookiesWork.cookie);
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "windows-1251");
                String json = new Gson().toJson(new Message(CookiesWork.cookie, secondUser, message));
                request = json;
                wr.write(json);
                wr.flush();
                wr.close();
                code = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception e) {
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        });
    }

    private void getMessage(String secondUser) {
        HttpURLConnection connection = null;
        URL url;
        int code;
        try {
            url = new URL(Const.URL + "?operation=messages&receiver=" + secondUser);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", CookiesWork.cookie);
            code = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "windows-1251"));
            String json = in.readLine();
            in.close();
            connection.disconnect();
            bufMessages = new Gson().fromJson(json, new TypeToken<ArrayList<Message>>() {
            }.getType());
        } catch (Exception e) {
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    private void setInformationSobes(String sobes) {
        Thread th = new Thread(() -> {
            BufferedImage bufferedImage = GetUserInformation.getPicture(sobes);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgSobes.setImage(image);

            textLoginSobes.setText(sobes);
        });
        th.start();
    }

}
