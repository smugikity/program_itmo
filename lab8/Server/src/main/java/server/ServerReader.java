package server;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lab5.legacy.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerReader {

    private static Set<Person> collectionPerson = Collections.synchronizedSet(new HashSet<>());
    public String timeStamp = new SimpleDateFormat("HH:mm:ss:SS dd/MM/yy").format(Calendar.getInstance().getTime());
    public String fileSource;
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;

    //singleton
    private static volatile ServerReader instance = new ServerReader();
    public static ServerReader getInstance(){
        return instance;
    }
    private ServerReader() {}

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
    }
    /**
     * Initial the program
     * @param path input xml file
     */
    public void initial(String path){
//        this.fileSource = path;
//        System.out.println(fileSource);
//        try (InputStreamReader in = new InputStreamReader(new FileInputStream(fileSource), StandardCharsets.UTF_8)) {
//            /**
//             *XML Document builder with file input using InputStreamReader class
//             */
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            //BufferedReader reader = new BufferedReader(in);
//            if (!in.ready()) {
//                System.err.println("File is empty"); System.exit(0);
//            }
//            InputSource input = new InputSource(in);
//            builder.setErrorHandler(new ParserErrorHandler());
//            Document data = builder.parse(input);
//            data.getDocumentElement().normalize();
//            if (data.getDocumentElement().getNodeName() != "People") {
//                System.err.println("File not meet required format. Root file node should be People"); System.exit(0);
//            }
//            System.out.println("Collection loaded. Root element: " + data.getDocumentElement().getNodeName());
//            NodeList nodeList = data.getElementsByTagName("Person");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Person p = getPerson(nodeList.item(i));
//                if (p != null) collectionPerson.add(p);
//            }
//            in.close();
//            //excute main process of program
//            //r.readCommand(this);
//        } catch (FileNotFoundException | NullPointerException e) {
//            System.err.println("File not found "+fileSource); System.exit(0);
//        } catch (IOException e) {
//            System.err.println("IOException occurred"); System.exit(0);
//        } catch (ParserConfigurationException | SAXException e) {
//            System.err.println("Parser error"); System.exit(0);
//        }
        initialTunnel();
        initialCollection();
    }

    private void initialTunnel() {
        //localhost:6769 -> pg:5432 (default by postgres)
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = System.getProperties();
            properties.load(inputStream);
            DB_URL = properties.getProperty("DB_URL");
            DB_USER = properties.getProperty("DB_USER");
            DB_PASS = properties.getProperty("DB_PASS");
            JSch jsch = new JSch();
            Session session = jsch.getSession(DB_USER,"se.ifmo.ru",2222);
            session.setPassword(DB_PASS);
            System.out.println("Connected to se.ifmo.ru:2222");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
            int assignedPort = session.setPortForwardingL(6769,"pg",5432);
            System.out.println("assigned tunnel localhost:"+assignedPort+" -> pg:5432");
        } catch (IOException | JSchException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void initialCollection() {
        try (Connection connection = ServerReader.getInstance().getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM PEOPLE");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int cl = rsmd.getColumnCount();
            while (resultSet.next()) {
                collectionPerson.add(new Person(resultSet.getInt(1),resultSet.getString(2),
                        new Coordinates(resultSet.getDouble(3),resultSet.getInt(4)),resultSet.getFloat(5),
                        resultSet.getLong(6),Color.valueOf(resultSet.getString(7).toUpperCase()),
                        Country.valueOf(resultSet.getString(8).toUpperCase()), new Location(resultSet.getDouble(9),
                        resultSet.getLong(10),resultSet.getDouble(11),resultSet.getString(12))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Set<Person> getCollectionPerson() {
        return collectionPerson;
    }

    /**
     * Create new person instance with node data from XML file
     * @param node input node to read
     * @return person instance
     */
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

    /**
     * Get value of element by tag name
     * @param item element
     * @param str tag name
     * @return
     */
    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    /**
     * Safe way to get element value from node
     * @param elem node
     * @return string value
     */
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
