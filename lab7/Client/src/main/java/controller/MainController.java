package controller;

import converter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lab5.legacy.Color;
import lab5.legacy.Country;
import lab5.legacy.Person;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private HashSet<Person> collection = new HashSet<>();
    private HashSet<Person> getCollection() {
        return collection;
    }
    @FXML
    private TableView<Person> tableView;
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
    private TableColumn<Person, Button> col_update;
    @FXML
    private TableColumn<Person, Button> col_delete;
    @FXML
    private Button button_save;
    @FXML
    private Button button_execute;
    @FXML
    private TextArea textArea;
    @FXML
    private Button logoutButton;
    @FXML
    private Button settingButton;
    @FXML
    private ComboBox<?> localeComboBox;
    @FXML
    private ComboBox<?> modeComboBox;
    @FXML
    private Button applyButton;
    @FXML
    private Button backButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCols();
        loadData();
    }
    @FXML
    protected void buttonExecuteAction() {
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
        col_update.setCellValueFactory(new PropertyValueFactory<>("col_update"));
        col_delete.setCellValueFactory(new PropertyValueFactory<>("col_delete"));
        editableCols();
    }

    private void editableCols() {
        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });
        col_coord_x.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        col_coord_x.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getCoordinates().setX(e.getNewValue());
        });
        col_coord_y.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
        col_coord_y.setOnEditCommit((e)-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getCoordinates().setY(e.getNewValue());
        });
        col_height.setCellFactory(TextFieldTableCell.forTableColumn(new CustomFloatStringConverter()));
        col_height.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setHeight(e.getNewValue());
        });
        col_weight.setCellFactory(TextFieldTableCell.forTableColumn(new CustomLongStringConverter()));
        col_weight.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setWeight(e.getNewValue());
        });
        col_color.setCellFactory(TextFieldTableCell.forTableColumn(new CustomColorStringConverter()));
        col_color.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setHairColor(e.getNewValue());
        });
        col_nation.setCellFactory(TextFieldTableCell.forTableColumn(new CustomCountryStringConverter()));
        col_nation.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNationality(e.getNewValue());
        });
        col_location_x.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        col_location_x.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getLocation().setX(e.getNewValue());
        });
        col_location_y.setCellFactory(TextFieldTableCell.forTableColumn(new CustomLongStringConverter()));
        col_location_y.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getLocation().setY(e.getNewValue());
        });
        col_location_z.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        col_location_z.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getLocation().setZ(e.getNewValue());
        });
        col_location_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_location_name.setOnEditCommit((e)->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getLocation().setName(e.getNewValue());
        });
        tableView.setEditable(true);
    }

    private void loadData() {
        ObservableList<Person> table_data = FXCollections.observableArrayList();
        //loadFromFile("data/lab6.xml",table_data);
        tableView.setItems(table_data);
    }
}
