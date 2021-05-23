package client;

import converter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lab5.legacy.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private String fileSource;
    private HashSet<Person> collection = new HashSet<>();
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
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("People");
            document.appendChild(root);
            for (Person p:getCollection()) {
                Element person = document.createElement("Person");
                root.appendChild(person);
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(p.getId().toString()));
                person.appendChild(id);
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(p.getName()));
                person.appendChild(name);
                Element coordinates = document.createElement("coordinates");
                person.appendChild(coordinates);
                Element xCoord = document.createElement("x");
                xCoord.appendChild(document.createTextNode(String.valueOf(p.getCoordinates().getX())));
                coordinates.appendChild(xCoord);
                Element yCoord = document.createElement("y");
                yCoord.appendChild(document.createTextNode(String.valueOf(p.getCoordinates().getY())));
                coordinates.appendChild(yCoord);
                Element height = document.createElement("height");
                height.appendChild(document.createTextNode(p.getHeight().toString()));
                person.appendChild(height);
                Element weight = document.createElement("weight");
                weight.appendChild(document.createTextNode(p.getWeight().toString()));
                person.appendChild(weight);
                Element haircolor = document.createElement("haircolor");
                haircolor.appendChild(document.createTextNode(p.getHairColor().toString().toLowerCase()));
                person.appendChild(haircolor);
                Element nationality = document.createElement("nationality");
                nationality.appendChild(document.createTextNode(p.getNationality().toString().toLowerCase().replace("_"," ")));
                person.appendChild(nationality);
                Element location = document.createElement("location");
                person.appendChild(location);
                Element xLocation = document.createElement("x");
                xLocation.appendChild(document.createTextNode(String.valueOf(p.getLocation().getX())));
                location.appendChild(xLocation);
                Element yLocation = document.createElement("y");
                yLocation.appendChild(document.createTextNode(String.valueOf(p.getLocation().getY())));
                location.appendChild(yLocation);
                Element zLocation = document.createElement("z");
                zLocation.appendChild(document.createTextNode(String.valueOf(p.getLocation().getZ())));
                location.appendChild(zLocation);
                Element nameLocation = document.createElement("name");
                nameLocation.appendChild(document.createTextNode(p.getLocation().getName()));
                location.appendChild(nameLocation);
            }
            PrintWriter writer = new PrintWriter(new File(fileSource));
            writer.write(xmlToString(document));
            writer.flush();
            writer.close();
            //you can also use staff.setAttribute("id", "1") for this
        } catch (ParserConfigurationException | FileNotFoundException ex) {
            System.exit(0);
        }
    }

    private HashSet<Person> getCollection() {
        return collection;
    }

    private static String xmlToString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
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
        col_coord_y.setOnEditCommit((e)->{
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
        loadFromFile("data/lab6.xml",table_data);
        //loadFromFile("data/lab6.xml",table_data);
        tableView.setItems(table_data);
    }

    private void loadFromFile(String path, ObservableList<Person> datalist) {
        fileSource = path;
        System.out.println(fileSource);
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(fileSource), StandardCharsets.UTF_8)) {
            /**
             *XML Document builder with file input using InputStreamReader class
             */
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //BufferedReader reader = new BufferedReader(in);
            if (!in.ready()) {
                System.err.println("File is empty"); System.exit(0);
            }
            InputSource input = new InputSource(in);
            builder.setErrorHandler(new ParserErrorHandler());
            Document data = builder.parse(input);
            data.getDocumentElement().normalize();
            if (data.getDocumentElement().getNodeName() != "People") {
                System.err.println("File not meet required format. Root file node should be People"); System.exit(0);
            }
            System.out.println("Collection loaded. Root element: " + data.getDocumentElement().getNodeName());
            NodeList nodeList = data.getElementsByTagName("Person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Person p = getPerson(nodeList.item(i));
                if (p != null) {datalist.add(p);collection.add(p);}
            }
            in.close();
            //excute main process of program
            //r.readCommand(this);
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("File not found "+fileSource); System.exit(0);
        } catch (IOException e) {
            System.err.println("IOException occurred"); System.exit(0);
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println("Parser error"); System.exit(0);
        }
    }

    private Person getPerson(Node node) {
        try {
            Person person = new Person();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                //person.setId(Long.parseLong(getValue(element,"id")));
                person.setName(getValue(element, "name"));
                //creation date
                //person.setCreationDate(new SimpleDateFormat("HH:mm:ss:SS dd/MM/yy").parse(getValue(element,"creationdate")));
                if ((float) Float.parseFloat(getValue(element, "height")) <= 0 || (long) Long.parseLong(getValue(element, "weight")) <=0) {
                    throw (new OverrangedException());
                }
                person.setHeight(Float.parseFloat(getValue(element, "height")));
                person.setWeight(Long.parseLong(getValue(element, "weight")));
                person.setHairColor(Color.valueOf(getValue(element, "haircolor").toUpperCase()));
                person.setNationality(Country.valueOf(getValue(element, "nationality").toUpperCase().replace(" ", "_")));
                //set coordinates
                Element coordElement = (Element) element.getElementsByTagName("coordinates").item(0);
                Coordinates coordinates = new Coordinates(
                        Double.parseDouble(getValue(coordElement, "x")),
                        Integer.parseInt(getValue(coordElement, "y")));
                person.setCoordinates(coordinates);
                //set location
                Element coordLocation = (Element) element.getElementsByTagName("location").item(0);
                Location location = new Location(
                        Double.parseDouble(getValue(coordLocation, "x")),
                        Long.parseLong(getValue(coordLocation, "y")),
                        Double.parseDouble(getValue(coordLocation, "z")),
                        getValue(coordLocation, "name"));
                person.setLocation(location);
            }
            return person;
        } catch (NullPointerException | IllegalArgumentException | OverrangedException ex) {
            System.err.println(ex.getMessage()+" not followed defined format");
            return null;
        }
    }

    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    private String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
