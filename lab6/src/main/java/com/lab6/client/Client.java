package com.lab6.client;

import com.lab5.*;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static Scanner fromKeyboard;
    private static ObjectOutputStream toServer;
    private static ObjectInputStream fromServer;
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in); Selector selector = Selector.open();) {
            fromKeyboard = scanner;
            //while (true) {
                System.out.println("Connecting ...");
                try {
                    SocketChannel socketChannel = SocketChannel.open();
                    socketChannel.connect(new InetSocketAddress("localhost",9999));
//                    ByteBuffer buf = ByteBuffer.allocate(128);
//
//                    int bytesRead = socketChannel.read(buf);
//                    while (bytesRead != -1) {
//                        buf.flip();
//                        while (buf.hasRemaining()) {
//                            System.out.print((char) buf.get());
//                        }
//                        buf.clear();
//                        bytesRead = socketChannel.read(buf);
//                    }
                } catch (IOException e) {
                    System.err.println("No connection. Type 1 to try again, otherwise exit.");
                    switch (fromKeyboard.nextLine()) {
                        case "1":
                            //continue;
                        default: System.exit(0);
                    }
                }
            //}
        }
    }

    /**
     *
     * @throws IOException
     */
    private static void launch() throws IOException {
        try {
            String command;
            while (true) {
                String fr_string = fromServer.readObject().toString();
                if (fr_string.trim().split("-")[0].equals("setdata")) {
                    System.out.println("name");
                    System.out.print(fr_string);
                    toServer.writeObject(fromKeyboard.nextLine().trim());
                } else {
                    System.out.print(fr_string+"\n$ ");
                    command = fromKeyboard.nextLine();
                    String[] cm_splited = command.trim().split(" ", 2);
                    switch (cm_splited[0]) {
                        case "": break;
                        case "exit": System.exit(0); break;
                        case "add": setData();
                        case "import":
                            try {
                                toServer.writeObject(import_file(cm_splited[1]));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found.");
                            } catch (SecurityException e) {
                                System.out.println("Can't read file.");
                            } catch (IOException e) {
                                //System.out.println("Что-то не так с файлом.");
                            }
                            break;
                        default:
                            toServer.writeObject(command);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + e.getMessage());
        }
    }

    private static Person setData() {
        Person p = new Person();
        try {
            Scanner commandReader = new Scanner(System.in);
            while (p.getName() == "") {
                System.out.print("name (can't be empty): ");
                p.setName(commandReader.nextLine());
            }
            while (p.getCoordinates() == null) {
                System.out.print("coordinates (can't be empty, format \"x y\", x double, y integer: ");
                String[] c = commandReader.nextLine().trim().split(" ", 2);
                try {
                    p.setCoordinates(new Coordinates(Double.parseDouble(c[0]), Integer.parseInt(c[1])));
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    System.out.println("Wrong coordinate format");
                    p.setCoordinates(null);
                }
            }
            while (p.getHeight() == null) {
                System.out.print("height (can't be empty, larger than 0): ");
                try {
                    p.setHeight(Float.parseFloat(commandReader.nextLine().trim()));
                    if (p.getHeight() <= 0) throw new OverrangedException("Height need to be larger than 0");
                } catch (NumberFormatException ex) {
                    System.out.println("Wrong number format");
                    p.setHeight(null);
                } catch (OverrangedException ex) {
                    p.setHeight(null);
                }
            }
            while (p.getWeight() == null || p.getWeight() <= 0) {
                System.out.print("weight (can't be empty, larger than 0): ");
                try {
                    p.setWeight(Long.parseLong(commandReader.nextLine().trim()));
                    if (p.getWeight() <= 0) throw new OverrangedException("Weight need to larger than 0");
                } catch (NumberFormatException ex) {
                    System.out.println("Wrong number format");
                    p.setWeight(null);
                } catch (OverrangedException ex) {
                    p.setWeight(null);
                }
            }
            while (p.getHairColor() == null) {
                System.out.print("hair color (could be red, black, white or brown): ");
                try {
                    p.setHairColor(Color.valueOf(commandReader.nextLine().trim().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Wrong color format");
                    p.setHairColor(null);
                }
            }
            while (p.getNationality() == null) {
                System.out.print("nationality (could be germany, vatican, thailand or south korea): ");
                try {
                    p.setNationality(Country.valueOf(commandReader.nextLine().trim().toUpperCase().replace(" ", "_")));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Wrong country format");
                    p.setNationality(null);
                }
            }
            while (p.getLocation() == null) {
                System.out.print("location (can't be empty, format \"x y z name\", x double, y long, z double): ");
                String[] c = commandReader.nextLine().trim().split(" ", 4);
                try {
                    p.setLocation(new Location(Double.parseDouble(c[0]), Long.parseLong(c[1]), Double.parseDouble(c[2]), c[3]));
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    System.out.println("Wrong location format");
                    p.setLocation(null);
                }
            }
        } catch (NoSuchElementException ex) {
            System.exit(0);
        }
        return p;
    }

    /**
     *
     * @param path
     * @return
     * @throws SecurityException
     * @throws IOException
     */
     private static String import_file(String path) throws SecurityException, IOException {
        File file = new File(path);
        String ext = path.substring(path.lastIndexOf('.')+1);
        if (!file.exists() | file.length() == 0  | !ext.equals("xml")) throw new FileNotFoundException();
        if (!file.canRead()) throw new SecurityException();
        try (BufferedReader inputStreamReader = new BufferedReader(new FileReader(file))) {
            String nextLine;
            StringBuilder result = new StringBuilder();
            while ((nextLine = inputStreamReader.readLine()) != null) result.append(nextLine);
            return "import "+result;
        }
    }

//    private static String import_xml(String path) {
//        try {
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            InputStreamReader in = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
//            //BufferedReader reader = new BufferedReader(in);
//            if (!in.ready()) {
//                System.out.println("File is empty");
//                System.exit(0);
//            }
//            //no method to parse XML from a Reader -> wrap the reader in an InputSource (single input source for an XML entity)
//            InputSource input = new InputSource(in);
//            builder.setErrorHandler(new ParserErrorHandler());
//            Document data = builder.parse(input);
//            //normalize
//            data.getDocumentElement().normalize();
//            //get root element (People)
//            if (data.getDocumentElement().getNodeName() != "People") {
//                System.out.println("File not meet required format. Root file node should be People");
//                System.exit(0);
//            }
//            //System.out.println("Root element: " + data.getDocumentElement().getNodeName());
//            //parse data of Person into HashSet collection
//            NodeList nodeList = data.getElementsByTagName("Person");
//            //now XML is loaded as Document in memory, lets convert it to Object List
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Person p = getPerson(nodeList.item(i));
//                if (p != null) collectionPerson.add(p);
//            }
//            //lets print Employee list information
//            in.close();
//        } catch (FileNotFoundException e)
//        {
//            System.out.println("No such file exist");
//        } catch (IOException e)
//        {
//            System.out.println("IOException occured");
//        } catch (ParserConfigurationException | SAXException e) {
//            System.out.println("Parser error");
//        }
//    }
}
