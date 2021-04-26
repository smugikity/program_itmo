package server;

import commands.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerCommandReader implements Runnable{
    private ServerReader r;
    private Socket socket;
    private HashMap<String, Command> availableCommands;
    private String[] cm_splited = new String[2];
    private String[] history = new String[14];

    public ServerCommandReader(ServerReader reader, Socket socket) {
        this.r = reader;
        this.socket = socket;
        history[0] = "";
        availableCommands = new HashMap<>();
        availableCommands.put("show", new CommandShow(r,"show : print all collection items as string to standard output"));
        availableCommands.put("add", new CommandAdd(r,"add {element} : add a new element to the collection"));
        availableCommands.put("add_if_min", new CommandAddIfMin(r,"add_if_min {element} : add a new element to the collection if its value is less than the smallest element in this collection"));
        availableCommands.put("clear", new CommandClear(r,"clear : clear the collection"));
        availableCommands.put("filter_less_than_height", new CommandFilterLessThanHeight(r,"filter_less_than_height height : display elements whose height field value is less than the specified one"));
        availableCommands.put("group_counting_by_coordinates", new CommandGroupCountingByCoordinates(r,"group_counting_by_coordinates : group the elements of the collection by the value of the coordinates field, display the number of elements in each group"));
        availableCommands.put("help", new CommandHelp(r,"help : display help for available commands", availableCommands));
        availableCommands.put("info", new CommandInfo(r,"info : print information about the collection (type, date of initialization, number of elements, etc.) to standard output"));
        availableCommands.put("max_by_location", new CommandMaxByLocation(r,"max_by_location : display any object from the collection, the value of the location field of which is the maximum"));
        availableCommands.put("remove_by_id", new CommandRemoveById(r,"remove_by_id id : remove an item from the collection by its id"));
        availableCommands.put("remove_greater", new CommandRemoveGreater(r,"remove_greater {element} : remove all elements from the collection that are greater than the specified one"));
        availableCommands.put("save", new CommandSave(r,"save : save the collection to a file"));
        availableCommands.put("update", new CommandUpdate(r,"update id {element} : update the value of the collection element whose id is equal to the given one"));
        availableCommands.put("exit", new CommandExit(r,"exit : exit the program (without saving to file)"));
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
     * Display commands typed
     * @param n number of commands to display
     */
    public String displayHistory(int n) {
        String result="";
        for (int i=0; i<n; i++) {
            if (history[n-1-i] != null && history[n-1-i]!="")
                result += (history[n-1-i]+"\n");
        }
        return result;
    }
    @Override
    public void run() {
        try  {
            PrintWriter to = new PrintWriter(socket.getOutputStream(),true); //autoflush whenever using PrintWriter.println
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            to.println("Connected. Ready to receive commands.");
            while (true) {
                try {
                    String line = null;
                    while(line == null) {
                        line = in.readLine();
                    }

                    System.out.println("Received ["+line +"] from "+this.socket +".");

                    Command errorCommand = new Command(null) {
                        @Override
                        public String execute() {
                            return "Wrong format command. Type \"help\" for help.";
                        }
                    };
                    Command selfHandledErrorCommand = new Command(null) {
                        @Override
                        public String execute(String s) {
                            return s;
                        }
                    };
                    //executeCommand(fr_string);
//                    boolean v = true;
                    cm_splited = line.trim().split(" ", 2);
                    if (cm_splited[0].equals("self_handled_error")) {
                        to.println(cm_splited[1]);
                    } else if (cm_splited[0].equals("history")) {
                        to.println(displayHistory(14));
                        history = appendArray(history,"history");
                    } else if (availableCommands.containsKey(cm_splited[0])) {
                        if (cm_splited.length == 1)
                            to.println(availableCommands.get(cm_splited[0]).execute());
                        else if (cm_splited.length == 2)
                            to.println(availableCommands.getOrDefault(cm_splited[0], errorCommand).execute(cm_splited[1]));
                        history = appendArray(history, cm_splited[0]);
                    } else to.println(errorCommand.execute());

                } catch (SocketException e) {
                    System.out.println(this.socket + " disconnected to server."); //Windows
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println(this.socket + " disconnected to server. buh"); //Unix
            ex.printStackTrace();
        }
    }
}
