package server;

import commands.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class ServerCommandReader implements Runnable, Serializable {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private Socket socket;
    private int ID;
    private String activationCode="";
    private HashMap<String, Command> availableCommands=new HashMap<>();;
    private String[] history = new String[14];
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ServerCommandReader(Socket socket) {
        initialSetup(socket);
    }

    public HashMap<String, Command> getAvailableCommands() {
        return availableCommands;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getActivationCode() {
        return activationCode;
    }
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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
            System.out.println(socket + " connected to server. Handling thread: "+Thread.currentThread().getName());
            log("\n"+socket + " connected to server.",2);
            socket.setSoTimeout(120*1000);
            PrintWriter to = new PrintWriter(socket.getOutputStream(),true); //autoflush whenever using PrintWriter.println
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            to.println("Connected. Ready to receive commands."+'\0');
            while (true) {
                String line = null;
                while(line == null) {
                    line = in.readLine();
                }
                System.out.println("Received ["+line +"] from "+this.socket +".");
                log("Received ["+line +"] from "+this.socket +".",2);
                Command errorCommand = new Command() {
                    @Override
                    public String execute() {
                        return "Wrong format command. Type \"help\" for help.";
                    }
                };
                Command selfHandledErrorCommand = new Command() {
                    @Override
                    public String execute(String s) {
                        return s;
                    }
                };
                String[] cm_splited = line.trim().split(" ", 2);
                if (availableCommands.containsKey(cm_splited[0])) {
                    switch (cm_splited[0]){

                        //ESSENTIAL
                        case "self_handled_error": to.println(cm_splited[1]+'\0'); break;
                        case "history": to.println(displayHistory(14)+'\0'); history = appendArray(history,"history"); break;
                        case "help": to.println(availableCommands.get("help").execute(availableCommands.values(),this)+'\0');
                            history = appendArray(history,"help"); break;

                        //ACCOUNT
                        case "login":
                        case "reset":
                        case "send":
                        case "register": to.println(availableCommands.get(cm_splited[0]).execute(cm_splited[1],this)+"\0");
                            history = appendArray(history,cm_splited[0]); break;
                        case "logout": to.println(availableCommands.get(cm_splited[0]).execute(this)+"\0");
                            history = appendArray(history,cm_splited[0]); break;

                        //WRITE: cant write if concurrent is locked (feel free to READ :/)
                        case "add":
                        case "add_if_min":
                        case "remove_by_id":
                        case "remove_greater":
                        case "update":
                            if (lock.isWriteLocked()) to.println("Please try again later. Collection currently in use." +'\0');
                            else {
                                lock.writeLock().lock();
                                to.println(availableCommands.get(cm_splited[0]).execute(cm_splited[1],this)+"\0");
                                lock.writeLock().unlock();
                            }
                            history = appendArray(history,cm_splited[0]); break;
                        case "clear":
                            if (lock.isWriteLocked()) to.println("Please try again later. Collection currently in use."+'\0');
                            else {
                                lock.writeLock().lock();
                                to.println(availableCommands.get(cm_splited[0]).execute(this)+"\0");
                                lock.writeLock().unlock();
                            }
                            history = appendArray(history,cm_splited[0]); break;

                        //READ
                        case "filter_less_than_height":
                            if (lock.isWriteLocked()) to.println("Please try again later. Collection currently in use."+'\0');
                            else {to.println(availableCommands.get(cm_splited[0]).execute(cm_splited[1],this)+"\0");}
                            history = appendArray(history,cm_splited[0]); break;
                        case "group_counting_by_coordinates":
                        case "info":
                        case "max_by_location":
                        case "show":
                            if (lock.isWriteLocked()) to.println("Please try again later. Collection currently in use."+'\0');
                            else {to.println(availableCommands.get(cm_splited[0]).execute(this)+"\0"); }
                            history = appendArray(history,cm_splited[0]); break;
                    }
                } else to.println(errorCommand.execute()+'\0');
            }
        } catch (IOException ex ) {
            System.err.println(this.socket+" disconnected to server");//Unix
            log(this.socket+" disconnected to server",2);
            Server.getClients().remove(getID());
            //System.exit(0);
        }
    }

    public void initialSetup(Socket socket) {
        this.socket = socket;
        history[0] = "";
        availableCommands.put("help", new CommandHelp("help : display help for available commands"));
        availableCommands.put("login", new CommandLogin("login: login to manipulate the collection"));
        availableCommands.put("register", new CommandRegister("register: register new account"));
        availableCommands.put("reset", new CommandReset("reset: set new password wth activation code"));
        availableCommands.put("send", new CommandSend("send: send activation code to your email"));
        availableCommands.put("exit", new CommandExit("exit : exit the program"));
        availableCommands.put("history", new CommandHistory("history : display last 14 commands"));
    }

    private void log(String s, int level) {
        switch (level) {
            case 4: LOGGER.severe(s); break;
            case 3: LOGGER.warning(s); break;
            case 2: LOGGER.info(s); break;
            case 1: LOGGER.finest(s); break;
        }
    }
}
