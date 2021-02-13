package com.company;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Reader {
    public static void main(String[] args) {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStreamReader in = new InputStreamReader(
                    new FileInputStream( "E:\\bai_tap\\program\\lab5\\src\\com\\company\\lab5.xml" ), "utf-8" );
            BufferedReader reader = new BufferedReader(in);
            //no method to parse XML from a Reader -> wrap the reader in an InputSource (single input source for an XML entity)
            InputSource input = new InputSource(reader);
            Document data = builder.parse(input);
            //normalize
            data.getDocumentElement().normalize();
            //get root element (People)
            System.out.println("Root element :" + data.getDocumentElement().getNodeName());
            //parse data of Person into HashSet collection
            NodeList nodeList = data.getElementsByTagName("Person");
            //now XML is loaded as Document in memory, lets convert it to Object List
            HashSet<Person> collectionPerson = new HashSet();
            for (int i = 0; i < nodeList.getLength(); i++) {
                collectionPerson.add(getPerson(nodeList.item(i)));
            }
            //lets print Employee list information
            in.close();
            for (Person p : collectionPerson) {
                p.lemmeseeurbooty();
            }
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println("No Such File Exists");
        }
        catch (IOException excpt)
        {
            System.out.println("IOException occured");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static private Person getPerson(Node node) throws ParseException {
        Person person = new Person();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            person.setId(Long.parseLong(getValue(element,"id")));
            person.setName(getValue(element,"name"));
            //creation date
            person.setCreationDate(
                    new SimpleDateFormat("dd/MM/yyyy").parse(getValue(element,"creationdate")));
            person.setHeight(Float.parseFloat(getValue(element,"height")));
            person.setWeight(Long.parseLong(getValue(element,"weight")));
            person.setHairColor(Color.valueOf(getValue(element,"haircolor").toUpperCase()));
            person.setNationality(Country.valueOf(getValue(element,"nationality").toUpperCase().replace(" ","_")));
            //set coordinates
            Element coordElement = (Element) element.getElementsByTagName("coordinates").item(0);
            Coordinates coordinates = new Coordinates(
                    Double.parseDouble(getValue(coordElement,"x")),
                    Integer.parseInt(getValue(coordElement,"y")));
            person.setCoordinates(coordinates);
            //set location
            Element coordLocation = (Element) element.getElementsByTagName("location").item(0);
            Location location = new Location(
                    Double.parseDouble(getValue(coordLocation,"x")),
                    Long.parseLong(getValue(coordLocation,"y")),
                    Double.parseDouble(getValue(coordLocation,"z")),
                    getValue(coordLocation,"name"));
            person.setLocation(location);

        }

        return person;
    }
    public static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    public static final String getElementValue( Node elem ) {
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