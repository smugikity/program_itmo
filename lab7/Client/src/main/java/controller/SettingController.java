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
import java.net.URISyntaxException;
import java.net.URL;
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

    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        GUIUtility.switchAnimation(pane,"login.fxml",1,-300, Interpolator.EASE_IN);
    }

    @FXML
    protected void handleApplyButtonAction() throws IOException {
        if (!localeComboBox.getValue().equals(ClientGUI.currentLanguage)) {
            ClientGUI.currentLanguage=localeComboBox.getValue().toString();
            try {
                ClientGUI.currentLanguage=localeComboBox.getValue().toString();
                GUIUtility.switchAnimation(pane,"setting.fxml",-1,300, Interpolator.EASE_IN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
