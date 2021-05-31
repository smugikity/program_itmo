package commands;

import datapack.Pack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CommandSave extends Command {
    public CommandSave(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(String fileSource, ServerCommandReader caller) {
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
            return new StringPack(true,"Saved into file "+fileSource);
            //you can also use staff.setAttribute("id", "1") for this
        } catch (ParserConfigurationException | FileNotFoundException ex) {
            return new StringPack(false,ex.toString());
        }
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
}
