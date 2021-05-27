package main;

import ultility.MyHtmlFormatter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
public class Server {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    static private FileHandler fileHTML;
    static private Formatter formatterHTML;
    static private HashSet<Integer> clients = new HashSet<>(); //key: socket, long: account id
    static private ExecutorService service = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(6967)) {
            ServerReader.getInstance().initial("data/lab6.xml");//args[0]!=null?args[0]:
            logger_start();
            System.out.print("Server started." + "\nPort: " + server.getLocalPort() + " / Address: " + InetAddress.getLocalHost() + ".\nWaiting for clients  ");
            Thread loading_cursor = new Thread(() -> {
                String c = ("|/-\\"); int i=0;
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print("\b"+c.charAt((i==3?i=0:++i)));
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        System.out.println();
                        Thread.currentThread().interrupt();
                    }
                }
            });
            loading_cursor.setDaemon(true);
            loading_cursor.start();
            //my thread designed aint a task (run method) cuz itself saves some interacting variables
            //so basically pool is useless here :/ just fill up following the lab method. bruh
            while (true) {
                Socket socket = server.accept();
                loading_cursor.interrupt();
                service.execute(new ServerCommandReader(socket));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            service.shutdown();
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("No such file exist.");
            service.shutdown();
            System.exit(0);
        }
    }

    public static void logger_start() {
        try {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging.txt");
        fileHTML = new FileHandler("Logging.html");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // create an HTML formatter
        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
        }
    }

    public static HashSet<Integer> getClients() {return clients;}
}
