package client;

import lab5.legacy.*;

import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Client {
    private static int cacheCommandCount=1;
    private static Scanner fromKeyboard;
    private static Set<String> filePaths = new HashSet<>();
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try (SocketChannel socketChannel = SocketChannel.open();) {
                fromKeyboard = new Scanner(System.in);
                System.out.println("Connecting ...");
                socketChannel.connect(new InetSocketAddress("localhost",6967));
                Selector selector = Selector.open();
                socketChannel.configureBlocking(false);
                //Scanner scanner = new Scanner(System.in);
                socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                while (true) {
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(128);
                            //StringBuilder message = new StringBuilder();
                            String result = "";
                            while (socketChannel.read(buffer) > 0 || !result.contains("\0")) {
                                buffer.flip();
                                result += StandardCharsets.UTF_8.decode(buffer).toString();
                                buffer.clear();
                            }
                            System.out.print(result);
                            cacheCommandCount--;
                        }
                        if (key.isWritable() && cacheCommandCount==0) {
                            String command = "";
                            //while () {
                                System.out.print("$ ");
                                command = fromKeyboard.nextLine();
                            //}
                            if (!command.trim().isEmpty()) {switch_command(command, socketChannel);}
                        }
                    }
                    keys.remove();
                }
            } catch (IOException ex) {
                try {
                    System.err.println("No connection. Type 1 to try again, 0 to exit.");
                    System.out.print("$ ");
                    String re;
                    Scanner scanner = new Scanner(System.in);
                    while (!(re = scanner.nextLine().trim()).equals("1")) {
                        switch (re) {
                            case "":
                                System.out.print("$ ");
                                continue;
                            case "0":
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Type 1 or 0.");
                                System.out.print("$ ");
                        }
                    }
                    continue;
                } catch (NoSuchElementException e) {System.exit(0);}
            } catch (NoSuchElementException ex) {
                System.exit(0);
            }
        }
    }

    private static void write(SocketChannel socketChannel, String content) throws IOException {
        byte[] bytes = (content + "\n").getBytes(StandardCharsets.UTF_8);
        ByteBuffer outBuffer = ByteBuffer.wrap(bytes);
        socketChannel.write(outBuffer);
    }

    private static String setUser(String mode) {
        String username="";
        String password="";
        try {
            Scanner sc = new Scanner(System.in);
            while (username.isEmpty()) {
                try {
                    System.out.print(mode+": ");
                    username = sc.nextLine().trim();
                    if (username.contains("-") || username.contains(",") || username.contains("/") || username.contains(" "))
                        throw new IllegalCharacterException();
                    if (!isValidEmailAddress(username)) throw new IllegalCharacterException();
                } catch (IllegalCharacterException ex) {
                    System.out.println("Wrong "+mode+" format");
                    username = "";
                }
            }
            while (password.isEmpty()) {
                try {
                    System.out.print("Pass: ");
                    password = sc.nextLine();
                    if (password.contains("-") || password.contains(",") || password.contains("/") || password.contains(" "))
                        throw new IllegalCharacterException();
                } catch (IllegalCharacterException ex) {
                    System.out.println("Password can't contain character \"-\", \",\", \"/\", \" \".");
                    password="";
                }
            }
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        return username+","+password;
    }

    private static String setData() {
        Person p = new Person();
        try {
            Scanner scan = new Scanner(System.in);
            while (p.getName().isEmpty()) {
                try {
                    System.out.print("Name (can't be empty): ");
                    p.setName(scan.nextLine());
                    if (p.getName().contains("-") || p.getName().contains(",") || p.getName().contains("/"))
                        throw new IllegalCharacterException();
                } catch (IllegalCharacterException ex) {
                    System.out.println("Name can't contain character \"-\" or \",\" or \"/\"");
                    p.setName("");
                }
            }
            while (p.getCoordinates() == null) {
                System.out.print("Coordinates (can't be empty, format \"x y\", x double, y integer: ");
                String[] c = scan.nextLine().trim().split(" ", 2);
                try {
                    p.setCoordinates(new Coordinates(Double.parseDouble(c[0]), Integer.parseInt(c[1])));
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    System.out.println("Wrong coordinate format");
                    p.setCoordinates(null);
                }
            }
            while (p.getHeight() == null) {
                System.out.print("Height (can't be empty, larger than 0): ");
                try {
                    p.setHeight(Float.parseFloat(scan.nextLine().trim()));
                    if (p.getHeight() <= 0) throw new OverrangedException();
                } catch (NumberFormatException | OverrangedException ex) {
                    System.out.println("Wrong number format");
                    p.setHeight(null);
                }
            }
            while (p.getWeight() == null || p.getWeight() <= 0) {
                System.out.print("Weight (can't be empty, larger than 0): ");
                try {
                    p.setWeight(Long.parseLong(scan.nextLine().trim()));
                    if (p.getWeight() <= 0) throw new OverrangedException();
                } catch (NumberFormatException | OverrangedException ex) {
                    System.out.println("Wrong number format");
                    p.setWeight(null);
                }
            }
            while (p.getHairColor() == null) {
                System.out.print("Hair color (could be red, black, white or brown): ");
                try {
                    p.setHairColor(Color.valueOf(scan.nextLine().trim().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Wrong color format");
                    p.setHairColor(null);
                }
            }
            while (p.getNationality() == null) {
                System.out.print("Nationality (could be germany, vatican, thailand or south korea): ");
                try {
                    p.setNationality(Country.valueOf(scan.nextLine().trim().toUpperCase().replace(" ", "_")));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Wrong country format");
                    p.setNationality(null);
                }
            }
            while (p.getLocation() == null) {
                System.out.print("Location (can't be empty, format \"x y z name\", x double, y long, z double): ");
                String[] c = scan.nextLine().trim().split(" ", 4);
                try {
                    if (c[3].contains(",") || c[3].contains("/") || c[3].contains("-"))
                        throw new IllegalCharacterException();
                    p.setLocation(new Location(Double.parseDouble(c[0]), Long.parseLong(c[1]), Double.parseDouble(c[2]), c[3]));
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    System.out.println("Wrong location format");
                    p.setLocation(null);
                } catch (IllegalCharacterException e) {
                    System.out.println("Name can't contain character \"-\" or \",\" or \"/\"");
                    p.setLocation(null);
                }
            }
        } catch(NoSuchElementException ex){
            System.exit(0);
        }
        //name, coord x, y, height, weight, hair, nationality, location x, y, z, name
        return p.getName() + "," + p.getCoordinates().getX() + "," + p.getCoordinates().getY() + "," + p.getHeight() + "," + p.getWeight() + ","
                + p.getHairColor().toString() + "," + p.getNationality().toString() + "," + p.getLocation().getX() + "," + p.getLocation().getY()
                + "," + p.getLocation().getZ() + "," + p.getLocation().getName();
    }
    /**
     *
     * @param path
     * @return
     * @throws SecurityException
     * @throws IOException
     */
    private static void execute_script(String path, SocketChannel socketChannel) throws SecurityException, IOException {
        File file = new File(path);
        if (!file.exists() | file.length() == 0 | !file.canRead()) {
            write(socketChannel, "self_handled_error Can't read file");
            return;
        }
        if (filePaths.contains(path)) return;
        filePaths.add(path);
        try (BufferedReader inputStreamReader = new BufferedReader(new FileReader(file))) {
            String nextLine;
            while ((nextLine = inputStreamReader.readLine()) != null && !nextLine.trim().equals("")) {
                switch_command(nextLine, socketChannel);
            }
            filePaths.remove(path);
            return;
        }
    }

    private static void switch_command(String command, SocketChannel socketChannel) throws IOException {
        try {
            String[] cm_splited = command.trim().split(" ", 2);
            switch (cm_splited[0]) {
                case "exit": System.exit(0); break;
                case "reset": cacheCommandCount++;write(socketChannel,(cm_splited.length==2?command.trim():(cm_splited[0]+" "+setUser("Code")))); break;
                case "login":
                case "register": cacheCommandCount++;write(socketChannel,(cm_splited.length==2?command.trim():(cm_splited[0]+" "+setUser("Username")))); break;
                case "add":
                case "add_if_min":
                case "remove_greater": cacheCommandCount++;write(socketChannel,(cm_splited.length==2?command.trim():(cm_splited[0]+" "+setData()))); break;
                case "update": cacheCommandCount++;write(socketChannel,(command.trim().split(",",3).length==3?command.trim():("update "+cm_splited[1]+","+setData()))); break;

                case "execute_script": execute_script(cm_splited[1], socketChannel); break;
                default:
                    cacheCommandCount++;write(socketChannel, command);
            }
        } catch (IndexOutOfBoundsException ex) {
            write(socketChannel, "self_handled_error Argument missing");
        }
        return;
    }
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}



