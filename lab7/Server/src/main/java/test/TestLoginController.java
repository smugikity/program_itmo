package test;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class TestLoginController implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    public ComboBox<String> comboBox ;
    private ObservableList<String> LOCALES;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOCALES = comboBox.getItems();
        getBundleLocales();
        comboBox.setValue(TestGUI.currentLanguage);
        TestGUI.controller=this;
    }

    @FXML
    protected void handleLoginButtonAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }

    @FXML
    private void handleResetButtonAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reset Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();

        if (!comboBox.getValue().equals(TestGUI.currentLanguage)) {
            TestGUI.currentLanguage=comboBox.getValue();
            try {
                TestGUI.currentLanguage=comboBox.getValue();
                TestGUI.stage.close();
                TestGUI.stage=changeLanguage(TestGUI.currentLanguage,"/login.fxml","Client");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Stage changeLanguage(String locale, String resource, String title) throws IOException {
        Stage stagetmp=new Stage();
        stagetmp.setTitle(title);
        stagetmp.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TestGUI.class.getResource(resource));
        loader.setResources(ResourceBundle.getBundle("interface", new Locale(locale)));
        stagetmp.setScene(new Scene(loader.load()));
        stagetmp.show();
        return stagetmp;
    }

    @FXML
    private void handleRegisterButtonAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }

    private void getBundleLocales() {
        try {
            File f = new File(this.getClass().getResource("/").toURI());
            final String bundle_prefix = "interface_";// Bundle name prefix.
            for (String s : Objects.requireNonNull(f.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(bundle_prefix);
                }
            }))) {
                LOCALES.add(s.substring(bundle_prefix.length(), s.indexOf('.')));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
