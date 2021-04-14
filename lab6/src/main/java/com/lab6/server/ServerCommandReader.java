package com.lab6.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerCommandReader implements Runnable{
    private ServerReader r;
    private Socket socket;
    private String[] userCommand = new String[14];
    private String[] cm_splited = new String[2];
    {
        userCommand[0] = "";
    }
    public ServerCommandReader(ServerReader reader, Socket socket) {
        this.r = reader;
        this.socket = socket;
    }
    /** Distribute command to corresponding method
     * @param s command as string typed by user
     */
    public void executeCommand(String s) {
        //splitedUserCommand[0] is command, splitedUserCommand[1] is parameter

    }
    /**
     * Safe way to append new element into array (outofbounds exception)
     * @param oldArray array to execute
     * @param value element to add
     * @return new array
     */
    private String[] appendArray(String[] oldArray, String value) {
        String[] newArray = new String[14];
        newArray[0] = value;
        for (int i=1; i<14; i++) {
            newArray[i] = oldArray[i-1];
        }
        return newArray;
    }

    /**
     * Scan input from user and parse to executeCommand()
     * @param r class containing collection
     */
    public void readCommand(ServerReader r) {
        this.r = r;
        Scanner commandReader = new Scanner(System.in);
        try {
            while (!userCommand[0].equals("exit")) {
                System.out.print("$ ");
                executeCommand(commandReader.nextLine());
            }
        } catch (NoSuchElementException ex) {
            System.exit(0);
        }
    }

    /**
     * Display commands typed
     * @param n number of commands to display
     */
    public void displayHistory(int n) {
        for (int i=0; i<n; i++) {
            if (userCommand[n-1-i] != null && userCommand[n-1-i]!="")
                System.out.println(userCommand[n-1-i]);
        }
    }
    @Override
    public void run() {
        try  {
            System.out.println("fuckit");
            ObjectInputStream fr = new ObjectInputStream(this.socket.getInputStream());
            ObjectOutputStream to = new ObjectOutputStream(this.socket.getOutputStream());
            to.writeObject("Connected.");
            to.writeObject("Lessgo");
            while (true) {
                try {
                    String fr_string = (String) fr.readObject();
                    System.out.println("Received ["+fr_string +"] from "+this.socket +".");

                    //executeCommand(fr_string);
                    boolean v = true;
                    cm_splited = fr_string.trim().split(" ", 2);
                    try {
                        switch (cm_splited[0]) {
                            case "": v= false; break;
                            case "help": r.help(); break;
                            case "info": r.info(); break;
                            case "show": to.writeObject(r.show(socket)); break; //test
                            case "add": to.writeObject(r.add(socket)); break;
                            case "update": r.update(cm_splited[1]); break;
                            case "remove_by_id": r.remove_by_id(cm_splited[1]); break;
                            case "clear": r.clear(); break;
                            case "save": r.save(); break;
                            //case "execute_script": r.execute_script(cm_splited[1]); break;
                            case "add_if_min": r.add_if_min(); break;
                            case "remove_greater": r.remove_greater(); break;
                            //case "history": r.history(); break;
                            case "max_by_location": r.max_by_location(); break;
                            case "group_counting_by_coordinates": r.group_counting_by_coordinates(); break;
                            case "filter_less_than_height" : r.filter_less_than_height(cm_splited[1]); break;
                            case "exit": System.exit(1); break;
                            default: to.writeObject("Unidentified command. Type \'help\' for help. :(\n"); v = false;
                        }
                        if (v) {
                            userCommand = appendArray(userCommand, cm_splited[0]);
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.err.println("Argument missing.");
                    }
                } catch (SocketException e) {
                    System.out.println(this.socket + " disconnected to server."); //Windows
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException /*|ClassNotFoundException*/ ex) {
            System.err.println(this.socket + " disconnected to server."); //Unix
        }
    }
}
