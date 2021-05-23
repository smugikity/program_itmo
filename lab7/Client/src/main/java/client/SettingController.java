package client;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private ComboBox localeComboBox;
    private ObservableList<String> LOCALES;
    @FXML
    private ComboBox modeComboBox;
    @FXML
    private Button applyButton;
    @FXML
    private Button switchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOCALES = localeComboBox.getItems();
        getBundleLocales();
        localeComboBox.setValue(ClientGUI.currentLanguage);
    }
    private void getBundleLocales() {
        try {
            File f = new File(this.getClass().getResource("/").toURI());
            final String bundle_prefix = "locale_";// Bundle name prefix.
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
    public void changeLanguage(String locale, String resource, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource(resource));
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(locale)));
        Parent root = loader.load();
        Scene scene = switchButton.getScene();

        root.translateYProperty().set(-scene.getHeight());
        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event->{
            stackPane.getChildren().remove(pane);
        });
        timeline.play();
    }

    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource("/login.fxml"));
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(ClientGUI.currentLanguage)));
        Parent root = loader.load();
        Scene scene = switchButton.getScene();

        root.translateXProperty().set(scene.getWidth());
        StackPane stackPane = (StackPane) scene.getRoot();
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

    @FXML
    protected void handleApplyButtonAction() throws IOException {
        if (!localeComboBox.getValue().equals(ClientGUI.currentLanguage)) {
            ClientGUI.currentLanguage=localeComboBox.getValue().toString();
            try {
                ClientGUI.currentLanguage=localeComboBox.getValue().toString();
                changeLanguage(ClientGUI.currentLanguage,"/setting.fxml","Client");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
