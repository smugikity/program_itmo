package server;

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
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerReader {

    private static Set<Person> collectionPerson = Collections.synchronizedSet(new HashSet<>());
    public String timeStamp = new SimpleDateFormat("HH:mm:ss:SS dd/MM/yy").format(Calendar.getInstance().getTime());
    public String fileSource;
    HashSet<String> loadedScript = new HashSet<>();

    /**
     * Initial the program
     * @param path input xml file
     */
    public ServerReader(String path){
        this.fileSource = path;
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))
        {
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
                if (p != null) collectionPerson.add(p);
            }
            in.close();
            //excute main process of program
            //r.readCommand(this);
        } catch (FileNotFoundException e)
        {
            System.err.println("File not found"); System.exit(0);
        } catch (IOException e)
        {
            System.err.println("IOException occurred"); System.exit(0);
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println("Parser error"); System.exit(0);
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
