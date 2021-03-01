package com.company;

import java.util.Scanner;

public class CommandReader {
    private Reader r;
    private String[] userCommand = new String[14];
    private String[] splitedUserCommand = new String[2];
    {
        userCommand[0] = "";
    }
     /** Distribute command
     * @param s
     */
    public void executeCommand(String s) {
        //splitedUserCommand[0] is command, splitedUserCommand[1] is parameter
        userCommand = appendArray(userCommand, s.trim());
        splitedUserCommand = userCommand[0].trim().split(" ", 2);
        try {
            switch (splitedUserCommand[0]) {
                case "":
                    break;
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
                case "exit": break;
                default: System.out.println("Unidentified command. Type \'help\' for help. :(");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Argument missing.");
        }
    }
    /**
     * Safe way to append new element into array (to not face outofbounds error)
     * @param oldArray
     * @param value
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
     * Execute commands from panel
     * - Main process of program
     */
    public void readCommand(Reader r) {
        this.r = r;
        try (Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand[0].equals("exit")) {
                //splitedUserCommand[0] is command, splitedUserCommand[1] is parameter
                executeCommand(commandReader.nextLine());
            }
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
