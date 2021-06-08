package controller;

import client.ClientGUI;
import client.Connection;
import client.GUIUtility;
import converter.*;
import javafx.animation.Interpolator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import lab5.legacy.Color;
import lab5.legacy.Country;
import lab5.legacy.Person;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private HBox commandBox;
    @FXML
    private HBox settingBox;
    @FXML
    private TableColumn<Person, Long> col_id;
    @FXML
    private TableColumn<Person, String> col_name;
    @FXML
    private TableColumn<Person, Double> col_coord_x;
    @FXML
    private TableColumn<Person, Integer> col_coord_y;
    @FXML
    private TableColumn<Person, String> col_date;
    @FXML
    private TableColumn<Person, Float> col_height;
    @FXML
    private TableColumn<Person, Long> col_weight;
    @FXML
    private TableColumn<Person, Color> col_color;
    @FXML
    private TableColumn<Person, Country> col_nation;
    @FXML
    private TableColumn<Person, Double> col_location_x;
    @FXML
    private TableColumn<Person, Long> col_location_y;
    @FXML
    private TableColumn<Person, Double> col_location_z;
    @FXML
    private TableColumn<Person, String> col_location_name;
    @FXML
    private TableColumn<Person, Void> col_delete;
    @FXML
    private Button executeButton;
    @FXML
    private Button createButton;
    @FXML
    private Button unfilterButton;
    @FXML
    private TextArea textArea;
    @FXML
    private Button logoutButton;
    @FXML
    private Button settingButton;
    @FXML
    private ComboBox<String> localeComboBox;
    @FXML
    private ComboBox<String> modeComboBox;
    @FXML
    private ComboBox<String> commandComboBox;
    @FXML
    private TextField argumentField;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Button applyButton;
    @FXML
    private Button backButton;
    private ObservableList<String> LOCALES;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection.getInstance().setMain(this);
        initCols();
        initCommandComboBox();
        Connection.getInstance().setTableData(Connection.getInstance().getFilterMode());

        textArea.setEditable(false);
        textArea.setWrapText(true);

        int id = Connection.getInstance().getID();
        String str = "rgb("+((id-id%16)/16%4+1)*60+","+((id-id%4)/4%4+1)*60+","+(id%4+1)*60+")";
        infoLabel.setStyle("-fx-font-weight: bold;"+"-fx-font-size: 32;"+
                "-fx-text-fill: "+str+";"+"-fx-alignment: "+ TextAlignment.CENTER+";");
        infoLabel.setText("ID User: "+id);

        localeComboBox.getItems().addAll("en_US","es_SV","ru_RU","sr_RS","uk_UA");
        localeComboBox.setValue(ClientGUI.currentLanguage);

        modeComboBox.getItems().addAll("Light mode","Dark mode");
        modeComboBox.setValue(ClientGUI.currentMode);
    }
    @FXML
    protected void executeButtonAction() {
        try {
            switch (commandComboBox.getValue()) {
                case "filter_less_than_height": Connection.getInstance().setTableData((int) Long.parseLong(argumentField.getText())); break;
                case "max_by_location": Connection.getInstance().setTableData(-1); break;
                case "remove_by_id": Connection.getInstance().writeLine(commandComboBox.getValue()+" "+Long.parseLong(argumentField.getText()));
                    textArea.setText(Connection.getInstance().readForceStringPack().toPrint()); break;
                case "clear": Connection.getInstance().writeLine("clear"); break;
                default: Connection.getInstance().writeLine(commandComboBox.getValue());
                    textArea.setText(Connection.getInstance().readForceStringPack().toPrint());
            }
        } catch (NumberFormatException | InterruptedException e) {
            GUIUtility.throwException(e);
        }
    }
    @FXML
    protected void createButtonAction() {
        Connection.getInstance().writeLine("add ######,0,0,1,1,RED,THAILAND,0,0,0,0");
    }
    @FXML
    protected void settingButtonAction() {
        try {
            GUIUtility.relocateAnimation(commandBox,settingBox,300, Interpolator.EASE_IN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void commandButtonAction() {
        try {
            GUIUtility.relocateAnimation(settingBox,commandBox,300, Interpolator.EASE_IN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void applyButtonAction() {
        if (!localeComboBox.getValue().equals(ClientGUI.currentLanguage) || !modeComboBox.getValue().equals(ClientGUI.currentMode)) {
            try {
                ClientGUI.currentLanguage=localeComboBox.getValue();
                System.out.println(ClientGUI.currentLanguage);
                GUIUtility.switchAnimation(borderPane,"main.fxml",-1,300, Interpolator.EASE_IN,modeComboBox.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    protected void unfilterButtonAction() {
        Connection.getInstance().setTableData(0);
    }

    private void initCommandComboBox() {
        commandComboBox.getItems().addAll("help","clear","history","info","group_counting_by_coordinates","max_by_location","filter_less_than_height","remove_by_id");
        commandComboBox.setValue("help");
    }


    private void initCols() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_coord_x.setCellValueFactory(new PropertyValueFactory<>("col_coord_x"));
        col_coord_y.setCellValueFactory(new PropertyValueFactory<>("col_coord_y"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("col_date"));
        col_height.setCellValueFactory(new PropertyValueFactory<>("height"));
        col_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        col_color.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        col_nation.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        col_location_x.setCellValueFactory(new PropertyValueFactory<>("col_location_x"));
        col_location_y.setCellValueFactory(new PropertyValueFactory<>("col_location_y"));
        col_location_z.setCellValueFactory(new PropertyValueFactory<>("col_location_z"));
        col_location_name.setCellValueFactory(new PropertyValueFactory<>("col_location_name"));
        col_delete.setCellValueFactory(new PropertyValueFactory<>("col_delete"));

        editableCols();
        tableView.setRowFactory(tv -> new TableRow<Person>() {
            @Override
            protected void updateItem(Person p, boolean empty) {
                super.updateItem(p, empty);
                if (p == null || p.getOwner_id() == 0)
                    setStyle("");
                else {
                    String str = "rgb("+((p.getOwner_id()-p.getOwner_id()%16)/16%4+1)*60+","+((p.getOwner_id()-p.getOwner_id()%4)/4%4+1)*60+","+(p.getOwner_id()%4+1)*60+")";
                    setStyle("-fx-background-color:"+str+";");
                }
            }
        });
    }

    private void editableCols() {

        col_delete.setCellFactory(col -> new TableCell<Person, Void>() {
            private final Button button;
            {
                button = new Button(col_delete.getText());
                button.setOnAction(evt -> {
                    if (col.getTableView().getItems().get(getIndex()).getOwner_id()==Connection.getInstance().getID())
                    Connection.getInstance().writeLine("remove_by_id "+col.getTableView().getItems().get(getIndex()).getId());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });

        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(0,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_coord_x.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        col_coord_x.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(1,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_coord_y.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        col_coord_y.setOnEditCommit((e)-> {
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(2,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_height.setCellFactory(TextFieldTableCell.forTableColumn(new CustomFloatStringConverter()));
        col_height.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(3,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_weight.setCellFactory(TextFieldTableCell.forTableColumn(new CustomLongStringConverter()));
        col_weight.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(4,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_color.setCellFactory(TextFieldTableCell.forTableColumn(new CustomColorStringConverter()));
        col_color.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(5,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_nation.setCellFactory(TextFieldTableCell.forTableColumn(new CustomCountryStringConverter()));
        col_nation.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(6,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_location_x.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        col_location_x.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(7,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_location_y.setCellFactory(TextFieldTableCell.forTableColumn(new CustomLongStringConverter()));
        col_location_y.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(8,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_location_z.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        col_location_z.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(9,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        col_location_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_location_name.setOnEditCommit((e)->{
            if (!e.getNewValue().equals(e.getOldValue()))
            sendCommit(10,e.getTableView().getItems().get(e.getTablePosition().getRow()),e.getNewValue());
        });
        tableView.setEditable(true);
    }

    public TableView<Person> getTableView() {
        return tableView;
    }
    public TextArea getTextArea() {
        return textArea;
    }

    private void sendCommit(int i, Person p, Object o) {
        tableView.refresh();
        if (p.getOwner_id()==Connection.getInstance().getID()) {
            String[] str = p.toStringCSV().split(",",11);
            str[i] = String.valueOf(o);
            Connection.getInstance().writeLine("update "+p.getId()+","+str[0]+","+str[1]+","+str[2]+","+str[3]+","+str[4]+","+str[5]+","+str[6]+","+str[7]+","+str[8]+","+str[9]+","+str[10]);
        }
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
