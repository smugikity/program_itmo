package com.company;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

public class Reader {

    private String[] userCommand = new String[14];
    private String[] splitedUserCommand = new String[2];
    static HashSet<Person> collectionPerson = new HashSet();
    String timeStamp = new SimpleDateFormat("HH:mm:ss:SS dd/MM/yy").format(Calendar.getInstance().getTime());
    String fileSource;
    {
        userCommand[0] = "";
    }


    public Reader(String fileSource) {
        this.fileSource = fileSource;
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStreamReader in = new InputStreamReader(new FileInputStream( fileSource ), "utf-8" );
            BufferedReader reader = new BufferedReader(in);
            //no method to parse XML from a Reader -> wrap the reader in an InputSource (single input source for an XML entity)
            InputSource input = new InputSource(reader);
            Document data = builder.parse(input);
            //normalize
            data.getDocumentElement().normalize();
            //get root element (People)
            System.out.println("Root element: " + data.getDocumentElement().getNodeName());
            //parse data of Person into HashSet collection
            NodeList nodeList = data.getElementsByTagName("Person");
            //now XML is loaded as Document in memory, lets convert it to Object List
            for (int i = 0; i < nodeList.getLength(); i++) {
                collectionPerson.add(getPerson(nodeList.item(i)));
            }
            //lets print Employee list information
            in.close();
            readCommand();
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

    private Person getPerson(Node node) throws ParseException {
        Person person = new Person();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            //person.setId(Long.parseLong(getValue(element,"id")));
            person.setName(getValue(element,"name"));
            //creation date
            //person.setCreationDate(new SimpleDateFormat("HH:mm:ss:SS dd/MM/yy").parse(getValue(element,"creationdate")));
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

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    public String getElementValue( Node elem ) {
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

    private void readCommand() throws IOException {
        try (Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand[0].equals("exit")) {
                userCommand = appendArray(userCommand, commandReader.nextLine().trim());
                //splitedUserCommand[0] is command, splitedUserCommand[1] is parameter
                splitedUserCommand = userCommand[0].trim().split(" ", 2);
                try {
                    switch (splitedUserCommand[0]) {
                        case "":
                            break;
                        case "help": help(); break;
                        case "info": info(); break;
                        case "show": show(); break;
                        case "add": add(); break;
                        case "update": update(splitedUserCommand[1]); break;
                        case "remove_by_id": remove_by_id(splitedUserCommand[1]); break;
                        case "clear": clear(); break;
                        case "save": save(); break;
                        case "execute_script": execute_script(splitedUserCommand[1]); break;
                        case "add_if_min": add_if_min(splitedUserCommand[1]); break;
                        case "remove_greater": remove_greater(splitedUserCommand[1]); break;
                        case "history": history(); break;
                        case "max_by_location": max_by_location(); break;
                        case "group_counting_by_coordinates": group_counting_by_coordinates(); break;
                        case "filter_less_than_height" : filter_less_than_height(splitedUserCommand[1]); break;
                        case "exit": break;
                        default: System.out.println("Unidentified command. Type \'help\' for help. :(");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Argument missing.");
                }
            }
        }
    }

    private void filter_less_than_height(String s) {
        for (Person person: collectionPerson) {
            if (person.getHeight() < Float.parseFloat(s))
                person.display();
        }
    }

    private void group_counting_by_coordinates() {
    }

    private void max_by_location() {
        Person maxP = null;
        for (Person p: collectionPerson) {
            if (maxP == null || (Math.pow(p.getLocation().getX(),2)+Math.pow(p.getLocation().getY(),2)+Math.pow(p.getLocation().getZ(),2)>
                    Math.pow(maxP.getLocation().getX(),2)+Math.pow(maxP.getLocation().getY(),2)+Math.pow(maxP.getLocation().getZ(),2))) {
                maxP = p;
            }
        }
        System.out.println("Deleted person with id: "+maxP.getId());
        collectionPerson.remove(maxP);
    }

    private void history() {
        for (int i=0; i<14; i++) {
            if (userCommand[13-i] != null && userCommand[13-i]!="")
                System.out.println(userCommand[13-i]);
        }
    }

    private void remove_greater(String s) {
        for (Person p:collectionPerson) {
            if (p.getValue() > Double.parseDouble(s)) collectionPerson.remove(p);
        }
    }

    private void add_if_min(String s) {
        Double min = 99999999999999999999.0;
        for (Person p: collectionPerson) {
            if (p.getValue()<=min) {
                min = p.getValue();
            }
        }
        Person p = new Person();
        setData(p);
        if (p.getValue()<min) collectionPerson.add(p);
        else System.out.println("Value of new person larger than minimun in collection");
    }

    private void execute_script(String s) {
    }

    private void save() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("People");
            document.appendChild(root);
            for (Person p:collectionPerson) {
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
            PrintWriter writer1 =null;
            writer1 = new PrintWriter(new File(fileSource));
            writer1.write(toString(document));
            writer1.flush();
            writer1.close();
            //you can also use staff.setAttribute("id", "1") for this
        } catch (ParserConfigurationException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void clear() {
        collectionPerson.clear();
    }

    private void remove_by_id(String s) {
        for (Person p: collectionPerson) {
            if (p.getId() == Long.parseLong(s)) {
                System.out.println("Deleted person with id: "+p.getId());
                collectionPerson.remove(p);
                break;
            }
        }
        System.out.println("Didn't find any person with id: "+s);
    }

    private void update(String s) {
        for (Person p: collectionPerson) {
            if (p.getId()==Long.parseLong(s)) {
                setData(p);
            }
            else System.out.println("Person with input ID not found");
        }
    }

    private void add() {
        Person p = new Person();
        setData(p);
        collectionPerson.add(p);
    }

    private void show() {
        for (Person person: collectionPerson) {
            person.display();
        }
    }

    private void info() {
        System.out.println("Type of Collection element: "+collectionPerson.stream().skip
                (new Random().nextInt(collectionPerson.size())).findFirst().orElse(null).getClass().getName());
        System.out.println("Size: "+collectionPerson.size());
        System.out.println("Initial time: "+timeStamp);
    }

    private void help() {
        System.out.println("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "history : вывести последние 14 команд (без их аргументов)\n" +
                "max_by_location : вывести любой объект из коллекции, значение поля location которого является максимальным\n" +
                "group_counting_by_coordinates : сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе\n" +
                "filter_less_than_height height : вывести элементы, значение поля height которых меньше заданного");
    }
    private String[] appendArray(String[] oldArray, String value) {
        String[] newArray = new String[14];
        newArray[0] = value;
        for (int i=1; i<14; i++) {
            newArray[i] = oldArray[i-1];
        }
        return newArray;
    }
    private void setData(Person p) {
        Scanner commandReader = new Scanner(System.in);
        while (p.getName()=="") {
            System.out.println("name (can't be empty): ");
            p.setName(commandReader.nextLine());
        }
        while (p.getCoordinates()==null) {
            System.out.println("coordinates (can't be empty, format \"x y\", x double, y integer: ");
            String[] c = commandReader.nextLine().trim().split(" ",2);
            try {
                p.setCoordinates(new Coordinates(Double.parseDouble(c[0]), Integer.parseInt(c[1])));
            } catch (NumberFormatException ex) {
                p.setCoordinates(null);
            }
        }
        while (p.getHeight()==null||p.getHeight()<=0) {
            System.out.println("height (can't be empty, larger than 0): ");
            try {
                p.setHeight(Float.parseFloat(commandReader.nextLine().trim()));
            } catch (NumberFormatException ex) {
                p.setHeight(null);
            }
        }
        while (p.getWeight()==null||p.getWeight()<=0) {
            System.out.println("weight (can't be empty, larger than 0): ");
            try {
                p.setWeight(Long.parseLong(commandReader.nextLine().trim()));
            } catch (NumberFormatException ex) {
                p.setWeight(null);
            }
        }
        while (p.getHairColor()==null) {
            System.out.println("hair color (could be red, black, white or brown): ");
            try {
                p.setHairColor(Color.valueOf(commandReader.nextLine().trim().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                p.setHairColor(null);
            }
        }
        while (p.getNationality()==null) {
            System.out.println("nationality (could be germany, vatican, thailand or south korea): ");
            try {
                p.setNationality(Country.valueOf(commandReader.nextLine().trim().toUpperCase().replace(" ","_")));
            } catch (IllegalArgumentException ex) {
                p.setNationality(null);
            }
        }
        while (p.getLocation()==null) {
            System.out.println("coordinates (can't be empty, format \"x y z name\", x double, y long, z double): ");
            String[] c = commandReader.nextLine().trim().split(" ",4);
            try {
                p.setLocation(new Location(Double.parseDouble(c[0]),Long.parseLong(c[1]),Double.parseDouble(c[2]),c[3]));
            } catch (NumberFormatException ex) {
                p.setLocation(null);
            }
        }
    }
    public static String toString(Document doc) {
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