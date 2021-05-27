package controller;

import client.ClientGUI;
import client.Connection;
import client.GUIUtility;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane pane;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button switchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        LOCALES = comboBox.getItems();
//        getBundleLocales();
//        comboBox.setValue(TestGUI.currentLanguage);
    }

    @FXML
    protected void handleLoginButtonAction() throws IOException {
        try {
            if (GUIUtility.checkExceptionLogin(textField.getText(),passwordField.getText())) {
                Connection.getInstance().write("login "+textField.getText()+","+passwordField.getText());
                String str;
                if ((str = Connection.getInstance().read()).contains("\1")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Dialog");
                    alert.setHeaderText("Successfully");
                    alert.setContentText(str);
                    alert.showAndWait();
                    GUIUtility.login(textField.getText());
                } else {
                    Connection.getInstance().write("register "+textField.getText()+","+passwordField.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Register Dialog");
                    alert.setHeaderText("Account not existed, Register instead");
                    alert.setContentText(Connection.getInstance().read());
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            GUIUtility.throwException(e);
        }
    }

    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        GUIUtility.switchAnimation(pane,"reset.fxml",1,-300, Interpolator.EASE_IN);
    }

    public static Stage changeLanguage(String locale, String resource, String title) throws IOException {
        Stage stagetmp=new Stage();
        stagetmp.setTitle(title);
        stagetmp.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource(resource));
        loader.setResources(ResourceBundle.getBundle("interface", new Locale(locale)));
        stagetmp.setScene(new Scene(loader.load()));
        stagetmp.show();
        return stagetmp;
    }
}
