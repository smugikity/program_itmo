package main;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import datapack.Pack;
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

    private volatile Set<Person> collectionPerson = Collections.synchronizedSet(new HashSet<>());
    private volatile Set<Pack> commit;
    private final String timeStamp = new SimpleDateFormat("HH:mm:ss:SS dd/MM/yy").format(Calendar.getInstance().getTime());
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;
    public Session session;

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
     */
    public void initial(int portL){
        initialTunnel(portL);
        initialCollection();
    }

    private void initialTunnel(int portL) {
        //localhost:6769 -> pg:5432 (default by postgres)
        //
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = System.getProperties();
            properties.load(inputStream);
            DB_URL = properties.getProperty("DB_URL");
            DB_USER = properties.getProperty("DB_USER");
            DB_PASS = properties.getProperty("DB_PASS");
            JSch jsch = new JSch();
            session = jsch.getSession(DB_USER,"se.ifmo.ru",2222);
            session.setPassword(DB_PASS);
            System.out.println("Connected to se.ifmo.ru:2222");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
            int assignedPort = session.setPortForwardingL(portL,"pg",5432);
            System.out.println("Assigned tunnel localhost:"+assignedPort+" -> pg:5432");
            //int assignedPort2 = session.setPortForwardingL(679,"smtp.gmail.com",587);
            //System.out.println("Assigned tunnel localhost:"+assignedPort2+" -> smtp.gmail.com:587");
        } catch (IOException | JSchException e) {
            System.out.println("Binded tunnel unsuccessfully, trying again. Error: "+e.getClass().getName());
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
    public Set<Pack> getCommit() {
        return commit;
    }
    public String getTimeStamp() {
        return timeStamp;
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
