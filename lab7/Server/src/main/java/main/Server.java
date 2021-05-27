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
        System.out.println("Program requires 2 variables: 2 ports, one for listen clients, one for setting tunnel for ssh." +
                "\nBy default - port1: 6767, port2: 6769.");
        try (ServerSocket server = new ServerSocket((args.length>=1)?Integer.parseInt(args[0]):6767)) {
            ServerReader.getInstance().initial((args.length>=2)?Integer.parseInt(args[1]):6769);//args[0]!=null?args[0]:
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
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.err.println("Program requires 2 variables: 2 ports, one for listen clients, one for setting tunnel for ssh." +
                    "\n ");
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
