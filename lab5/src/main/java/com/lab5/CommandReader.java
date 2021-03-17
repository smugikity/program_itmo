package com.lab5;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandReader {
    private Reader r;
    private String[] userCommand = new String[14];
    private String[] splitedUserCommand = new String[2];
    {
        userCommand[0] = "";
    }
     /** Distribute command to corresponding method
     * @param s command as string typed by user
     */
    public void executeCommand(String s) {
        //splitedUserCommand[0] is command, splitedUserCommand[1] is parameter
        boolean v = true;
        splitedUserCommand = s.trim().split(" ", 2);
        try {
            switch (splitedUserCommand[0]) {
                case "": v= false; break;
                case "help": r.help(); break;
                case "info": r.info(); break;
                case "show": r.show(); break;
                case "add": r.add(); break;
                case "update": r.update(splitedUserCommand[1]); break;
                case "remove_by_id": r.remove_by_id(splitedUserCommand[1]); break;
                case "clear": r.clear(); break;
                case "save": r.save(); break;
                case "execute_script": r.execute_script(splitedUserCommand[1]); break;
                case "add_if_min": r.add_if_min(); break;
                case "remove_greater": r.remove_greater(splitedUserCommand[1]); break;
                case "history": r.history(); break;
                case "max_by_location": r.max_by_location(); break;
                case "group_counting_by_coordinates": r.group_counting_by_coordinates(); break;
                case "filter_less_than_height" : r.filter_less_than_height(splitedUserCommand[1]); break;
                case "exit": System.exit(1); break;
                default: System.out.println("Unidentified command. Type \'help\' for help. :("); v = false;
            }
            if (v) {
                userCommand = appendArray(userCommand, splitedUserCommand[0]);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Argument missing.");
        }
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
    public void readCommand(Reader r) {
        this.r = r;
        Scanner commandReader = new Scanner(System.in);
        try {
        while (!userCommand[0].equals("exit")) {
            System.out.print("$ ");
            //splitedUserCommand[0] is command, splitedUserCommand[1] is parameter
            //try {
            //if (commandReader.hasNextLine())
            executeCommand(commandReader.nextLine());
            //} catch (NoSuchElementException ex) {
            //    System.out.println("stop it");
            //}
        }
        } catch (NoSuchElementException ex) {
            System.out.println("bye bye");
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
}
