package client;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
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
    protected void handleLoginButtonAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }

    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource("/reset.fxml"));
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(ClientGUI.currentLanguage)));
        Parent root = loader.load();
        Scene scene = switchButton.getScene();

        root.translateXProperty().set(scene.getWidth());
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event->{
            stackPane.getChildren().remove(pane);
        });
        timeline.play();
    }

//    @FXML
//    private void handleResetButtonAction() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Reset Dialog");
//        alert.setHeaderText("Look, an Information Dialog");
//        alert.setContentText("I have a great message for you!");
//        alert.showAndWait();
//
//        if (!comboBox.getValue().equals(TestGUI.currentLanguage)) {
//            TestGUI.currentLanguage=comboBox.getValue();
//            try {
//                TestGUI.currentLanguage=comboBox.getValue();
//                TestGUI.stage.close();
//                TestGUI.stage=changeLanguage(TestGUI.currentLanguage,"/login.fxml","Client");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

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
                //LOCALES.add(s.substring(bundle_prefix.length(), s.indexOf('.')));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
