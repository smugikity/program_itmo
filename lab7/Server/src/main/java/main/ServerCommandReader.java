package main;

import commands.*;
import datapack.InitialMegaPack;
import datapack.Pack;
import datapack.StringPack;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class ServerCommandReader implements Runnable, Serializable {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private Socket socket;
    private int ID=0;
    private String activationCode="";
    private HashMap<String, Command> availableCommands=new HashMap<>();
    //history
    private String[] history = new String[14];
    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private int localCommit = 0;

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
            System.out.println(socket + " connected output server. Handling thread: "+Thread.currentThread().getName());
            log("\n"+socket + " connected output server.",2);
            socket.setSoTimeout(120*1000);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            //PrintWriter output = new PrintWriter(socket.getOutputStream(),true); //autoflush whenever using PrintWriter.println
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output.writeObject(new StringPack(true,"Connected. Ready to receive commands."));
            output.writeObject(new InitialMegaPack(new HashSet<>(ServerReader.getInstance().getCollectionPerson())));
            output.flush();
            localCommit = getServerCommit().size();
            while (true) {
                if (getID()!=0)
                    while (localCommit<getServerCommit().size()) {
                    output.writeObject(getServerCommit().get(localCommit++));
                    output.flush();
                }
                if (input.ready()) {
                    String line = input.readLine();//input.readLine();

                    System.out.println("Received [" + line + "] from " + this.socket + ".");
                    log("Received [" + line + "] from " + this.socket + ".", 2);
                    Command errorCommand = new Command() {
                        @Override
                        public Pack execute() {
                            return new StringPack(false, "Wrong format command. Type \"help\" for help.");
                        }
                    };
                    Command selfHandledErrorCommand = new Command() {
                        @Override
                        public Pack execute(String s) {
                            return new StringPack(false, s);
                        }
                    };
                    String[] cm_splited = line.trim().split(" ", 2);
                    if (availableCommands.containsKey(cm_splited[0])) {
                        switch (cm_splited[0]) {

                            //ESSENTIAL
                            case "self_handled_error":
                                output.writeObject(new StringPack(false, cm_splited[1]));
                                break;
                            case "history":
                                output.writeObject(new StringPack(false, displayHistory(14)));
                                ;
                                history = appendArray(history, "history");
                                break;
                            case "help":
                                output.writeObject(availableCommands.get("help").execute(availableCommands.values(), this));
                                history = appendArray(history, "help");
                                break;

                            //ACCOUNT
                            case "login":
                            case "reset":
                            case "send":
                            case "register":
                                output.writeObject(availableCommands.get(cm_splited[0]).execute(cm_splited[1], this));
                                history = appendArray(history, cm_splited[0]);
                                break;
                            case "logout":
                                output.writeObject(availableCommands.get(cm_splited[0]).execute(this));
                                history = appendArray(history, cm_splited[0]);
                                break;

                            //WRITE: cant write if concurrent is locked (feel free output READ :/)
                            case "add":
                            case "add_if_min":
                            case "remove_by_id":
                            case "remove_greater":
                            case "update":
                                if (lock.isWriteLocked())
                                    output.writeObject(new StringPack(false, "Please try again later. Collection currently input use."));
                                else {
                                    lock.writeLock().lock();
                                    Pack pack = availableCommands.get(cm_splited[0]).execute(cm_splited[1], this);
                                    if (pack instanceof StringPack) output.writeObject(pack);
                                    else getServerCommit().add(pack);
                                    lock.writeLock().unlock();
                                }
                                history = appendArray(history, cm_splited[0]);
                                break;
                            case "clear":
                                if (lock.isWriteLocked())
                                    output.writeObject(new StringPack(false, "Please try again later. Collection currently input use."));
                                else {
                                    lock.writeLock().lock();
                                    Pack pack = availableCommands.get(cm_splited[0]).execute(this);
                                    if (pack instanceof StringPack) output.writeObject(pack);
                                    else getServerCommit().add(pack);
                                    lock.writeLock().unlock();
                                }
                                history = appendArray(history, cm_splited[0]);
                                break;

                            //READ
                            case "filter_less_than_height":
                                if (lock.isWriteLocked())
                                    output.writeObject(new StringPack(false, "Please try again later. Collection currently input use."));
                                else
                                    output.writeObject(availableCommands.get(cm_splited[0]).execute(cm_splited[1], this));
                                history = appendArray(history, cm_splited[0]);
                                break;
                            case "group_counting_by_coordinates":
                            case "info":
                            case "max_by_location":
                            case "show":
                                if (lock.isWriteLocked())
                                    output.writeObject(new StringPack(false, "Please try again later. Collection currently input use."));
                                else output.writeObject(availableCommands.get(cm_splited[0]).execute(this));
                                history = appendArray(history, cm_splited[0]);
                                break;
                        }
                    } else output.writeObject(errorCommand.execute());
                    output.flush();
                }
            }
        } catch (IOException ex ) {
            System.err.println(this.socket+" disconnected to server");//Unix
            log(this.socket+" disconnected to server",2);
            Server.getClients().remove(getID());
            //System.exit(0);
        }
    }
    private ArrayList<Pack> getServerCommit() {
        return ServerReader.getInstance().getCommit();
    }

    private void initialSetup(Socket socket) {
        this.socket = socket;
        history[0] = "";
        availableCommands.put("help", new CommandHelp("help : display help for available commands"));
        availableCommands.put("login", new CommandLogin("login: login to manipulate the collection"));
        availableCommands.put("register", new CommandRegister("register: register new account"));
        //availableCommands.put("reset", new CommandReset("reset: set new password wth activation code"));
        //availableCommands.put("send", new CommandSend("send: send activation code to your email"));
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
