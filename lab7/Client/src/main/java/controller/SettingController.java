package controller;

import client.ClientGUI;
import client.GUIUtility;
import javafx.animation.Interpolator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private ComboBox<String> localeComboBox;
    private ObservableList<String> LOCALES;
    @FXML
    private ComboBox<String> modeComboBox;
    @FXML
    private Button applyButton;
    @FXML
    private Button switchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localeComboBox.getItems().addAll("en_US","es_SV","ru_RU","sr_RS","uk_UA");
        localeComboBox.setValue(ClientGUI.getCurrentLanguage());
        modeComboBox.getItems().addAll("Light mode","Dark mode");
        modeComboBox.setValue(ClientGUI.currentMode);
    }
    private void getBundleLocales() {
        try {
            File f = new File(this.getClass().getResource("/").toURI());
            final String bundle_prefix = "bundle_";// Bundle name prefix.
            for (String s : Objects.requireNonNull(f.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(bundle_prefix);
                }
            }))) {
                LOCALES.add(s.substring(bundle_prefix.length(), s.indexOf('.')));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        GUIUtility.switchAnimation(pane,"login.fxml",1,-300, Interpolator.EASE_IN,ClientGUI.currentMode);
    }

    @FXML
    protected void handleApplyButtonAction() {
        if (!localeComboBox.getValue().equals(ClientGUI.getCurrentLanguage()) || !modeComboBox.getValue().equals(ClientGUI.currentMode)) {
            try {
                ClientGUI.setCurrentLanguage(localeComboBox.getValue());
                GUIUtility.switchAnimation(pane,"setting.fxml",-1,300, Interpolator.EASE_IN,modeComboBox.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
