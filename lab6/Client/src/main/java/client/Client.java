package client;

import lab5.legacy.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Client {
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
    //                            (key.isValid() && key.isConnectable()) {
    //                                SocketChannel channel = (SocketChannel) key.channel();
    //                                if (channel.isConnectionPending()) {
    //                                    channel.finishConnect();
    //                                }
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(128);
                            //StringBuilder message = new StringBuilder();
                            while (socketChannel.read(buffer) > 0) {
                                buffer.flip();
                                System.out.print(StandardCharsets.UTF_8.decode(buffer).toString());
                                buffer.clear();
                            }
                            String command = "";

                            while (command.trim().isEmpty()) {
                                System.out.print("$ ");
                                command = fromKeyboard.nextLine();
                            }
                            switch_command(command, socketChannel);
                            TimeUnit.MILLISECONDS.sleep(100);
                        }
                    }
                    keys.remove();
                }
            } catch (IOException | InterruptedException ex) {
                try {
                    System.err.println("No connection. Type 1 to try again, 0 to exit.");
                    String re;
                    Scanner scanner = new Scanner(System.in);
                    while (!(re = scanner.nextLine().trim()).equals("1")) {
                        switch (re) {
                            case "":
                                System.out.println();
                                continue;
                            case "0":
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Type 1 or 0.");
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


    private static String setData() {
        Person p = new Person();
        try {
            Scanner scan = new Scanner(System.in);
            while (p.getName().equals("")) {
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
            System.out.println("Loading...");
            TimeUnit.SECONDS.sleep(1); //delay waiting for execute fully
            filePaths.remove(path);
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void switch_command(String command, SocketChannel socketChannel) throws IOException {
        try {
            String[] cm_splited = command.trim().split(" ", 2);
            switch (cm_splited[0]) {
                case "exit": System.exit(0); break;
                case "add": write(socketChannel,"add "+setData()); break;
                case "add_if_min": write(socketChannel,"add_if_min "+setData()); break;
                case "update": write(socketChannel,"update "+cm_splited[1]+","+setData()); break;
                case "remove_greater": write(socketChannel,"remove_greater "+setData()); break;
                case "execute_script": execute_script(cm_splited[1], socketChannel); break;
                default:
                    write(socketChannel, command);
            }
        } catch (IndexOutOfBoundsException ex) {
            write(socketChannel, "self_handled_error Argument missing");
        }
        return;
    }
}



