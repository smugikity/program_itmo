package com.company;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
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
    HashSet<String> loadedScript;
    CommandReader r;
    {
        userCommand[0] = "";
    }

    /**
     * Initial the program
     * @param fileSource input xml file
     */
    public Reader(String fileSource, CommandReader r) {
        this.fileSource = fileSource;
        try
        {
            this.r = r;
            /**
             *XML Document builder with file input using InputStreamReader class
             */
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
            //excute main process of program
            r.readCommand(this);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No such file exist");
        }
        catch (IOException e)
        {
            System.out.println("IOException occured");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create new person instance with node data from XML file
     * @param node input node to read
     * @return person instance
     */
    private Person getPerson(Node node) {
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
    /**
     * Convert XML Document to String (for printWriter into file)
     * @param doc
     * @return
     */
    private static String toString(Document doc) {
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

    //command control

    /**
     * Display all person instances with height lower than given value
     * @param s given height
     */
    public void filter_less_than_height(String s) {
        for (Person person: collectionPerson) {
            if (person.getHeight() < Float.parseFloat(s))
                person.display();
        }
    }

    /**
     * Idea: Group person elements by their distances (caculated by sum of x, y squares) and show number of elements of each group
     */
    public void group_counting_by_coordinates() {
        HashSet<Person> h = collectionPerson;
        for (int i=0;!h.isEmpty();i++) {
            int count = 0;
            HashSet<Person> tr=new HashSet<>();
            for (Person p: h) {
                Double distant = Math.sqrt(Math.pow(p.getCoordinates().getX(),2)+Math.pow(p.getCoordinates().getY(),2));
                if (distant >= Math.pow(10,i) && distant < Math.pow(10,i+1)) {
                    count ++;
                    tr.add(p);
                }
            }
            if (!tr.isEmpty())
            for (Person p:tr) {
                h.remove(p);
            }

            System.out.println("There are "+count+" persons with distance between "+Math.pow(10,i)+" and "+Math.pow(10,i+1));
        }
    }

    /**
     * Delete the person with largest location value (sum of x,y,z squares) out of collection
     */
    public void max_by_location() {
        Person maxP = null;
        for (Person p: collectionPerson) {
            if (maxP == null || (Math.pow(p.getLocation().getX(),2)+Math.pow(p.getLocation().getY(),2)+Math.pow(p.getLocation().getZ(),2)>Math.pow(maxP.getLocation().getX(),2)+Math.pow(maxP.getLocation().getY(),2)+Math.pow(maxP.getLocation().getZ(),2))) {
                maxP = p;
            }
        }
        System.out.println("Deleted person with id: "+maxP.getId());
        collectionPerson.remove(maxP);
    }

    /**
     * Display last 14 executed commands
     */
    public void history() {
        r.history();
    }

    /**
     * Delete all the persons with value larger than given person
     * @param s given number
     */
    public void remove_greater(String s) {
        Person person = new Person();
        setData(person);
        for (Person p:collectionPerson) {
            if (p.compareTo(person)>0) collectionPerson.remove(p);
        }
    }

    /**
     * Add new person if its value is minimum
     */
    public void add_if_min() {
        Person min=null;
        for (Person p: collectionPerson) {
            if (p.compareTo(min)<=0||min==null) {
                min = p;
            }
        }
        Person p = new Person();
        setData(p);
        if (p.compareTo(min)<0) collectionPerson.add(p);
        else System.out.println("Value of new person larger than minimum in collection");
    }

    /**
     * Import script file and execute it
     * @param s source file
     */
    public void execute_script(String s)  {
        if (loadedScript.contains(s)) return;
        loadedScript.add(s);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+s),"utf-8" ));
            while(reader.ready()) {
                String line = reader.readLine();
                r.executeCommand(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadedScript.remove(s);
    }

    /**
     * Save the collection data into initial XML file using class PrintWriter
     */
    public void save() {
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

    /**
     * obviously, clear lmao
     */
    public void clear() {
        collectionPerson.clear();
    }

    /**
     * Remove element with given ID out of collection
     * @param s ID
     */
    public void remove_by_id(String s) {
        for (Person p: collectionPerson) {
            if (p.getId() == Long.parseLong(s)) {
                System.out.println("Deleted person with id: "+p.getId());
                collectionPerson.remove(p);
                break;
            }
        }
        System.out.println("Didn't find any person with id: "+s);
    }

    /**
     * Change data of person with given ID
     * @param s
     */
    public void update(String s) {
        for (Person p: collectionPerson) {
            if (p.getId()==Long.parseLong(s)) {
                setData(p);
            }
            else System.out.println("Person with input ID not found");
        }
    }

    /**
     * Add new person
     */
    public void add() {
        Person p = new Person();
        setData(p);
        collectionPerson.add(p);
    }

    /**
     * Display data of persons in collection
     */
    public void show() {
        for (Person person: collectionPerson) {
            person.display();
        }
    }

    /**
     * Basic information of collection
     */
    public void info() {
        System.out.println("Type of Collection element: "+collectionPerson.stream().skip
                (new Random().nextInt(collectionPerson.size())).findFirst().orElse(null).getClass().getName());
        System.out.println("Size: "+collectionPerson.size());
        System.out.println("Initial time: "+timeStamp);
    }

    /**
     * HELP!
     */
    public void help() {
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
    /**
     * Manually write data for given person
     * @param p
     */
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
                System.out.println("Wrong number format");
                p.setCoordinates(null);
            }
        }
        while (p.getHeight()==null) {
            System.out.println("height (can't be empty, larger than 0): ");
            try {
                p.setHeight(Float.parseFloat(commandReader.nextLine().trim()));
                if (p.getHeight()<=0) throw new OverrangedException();
            } catch (NumberFormatException ex) {
                System.out.println("Wrong number format");
                p.setHeight(null);
            } catch (OverrangedException ex) {
                p.setHeight(null);
            }
        }
        while (p.getWeight()==null||p.getWeight()<=0) {
            System.out.println("weight (can't be empty, larger than 0): ");
            try {
                p.setWeight(Long.parseLong(commandReader.nextLine().trim()));
                if (p.getWeight()<=0) throw new OverrangedException();
            } catch (NumberFormatException ex) {
                System.out.println("Wrong number format");
                p.setWeight(null);
            } catch (OverrangedException ex) {
                p.setWeight(null);
            }
        }
        while (p.getHairColor()==null) {
            System.out.println("hair color (could be red, black, white or brown): ");
            try {
                p.setHairColor(Color.valueOf(commandReader.nextLine().trim().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                System.out.println("Wrong color format");
                p.setHairColor(null);
            }
        }
        while (p.getNationality()==null) {
            System.out.println("nationality (could be germany, vatican, thailand or south korea): ");
            try {
                p.setNationality(Country.valueOf(commandReader.nextLine().trim().toUpperCase().replace(" ","_")));
            } catch (IllegalArgumentException ex) {
                System.out.println("Wrong country format");
                p.setNationality(null);
            }
        }
        while (p.getLocation()==null) {
            System.out.println("coordinates (can't be empty, format \"x y z name\", x double, y long, z double): ");
            String[] c = commandReader.nextLine().trim().split(" ",4);
            try {
                p.setLocation(new Location(Double.parseDouble(c[0]),Long.parseLong(c[1]),Double.parseDouble(c[2]),c[3]));
            } catch (NumberFormatException ex) {
                System.out.println("Wrong number format");
                p.setLocation(null);
            }
        }
    }
}