package com.lab6.server;
import com.lab5.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerReader reader = new ServerReader(System.getenv("path"));

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9999)) {
            System.out.print("Server started." + "\nPort: " + server.getLocalPort() +
                    " / Address: " + InetAddress.getLocalHost() + ".\nWaiting for clients  ");
            Thread loading_cursor = new Thread(() -> {
                String c = ("|/-\\"); int i=0;
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print("\b"+c.charAt((i==3?i=0:++i)));
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        //System.out.print("\b\n");
                        Thread.currentThread().interrupt();
                    }
                }
            });
            loading_cursor.setDaemon(true);
            loading_cursor.start();
            while (true) {
                Socket socket = server.accept();
                loading_cursor.interrupt();
                System.out.println("\n"+socket + " connected to server.");
                Runnable r = new ServerCommandReader(reader, socket);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
        }
    }
}
