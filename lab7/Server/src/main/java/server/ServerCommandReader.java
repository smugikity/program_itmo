package server;

import commands.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerCommandReader implements Runnable, Serializable {
    private Socket socket;
    private int ID=0;
    private String activationCode="";
    private HashMap<String, Command> availableCommands;
    private String[] cm_splited = new String[2];
    private String[] history = new String[14];
    private static Logger LOGGER;

    public ServerCommandReader(Socket socket) {
        LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        System.out.println("\n"+socket + " connected to server.");
        log("\n"+socket + " connected to server.",2);
        ServerReader r = ServerReader.getInstance();
        this.socket = socket;
        history[0] = "";
        availableCommands = new HashMap<>();
        availableCommands.put("help", new CommandHelp("help : display help for available commands", availableCommands));
        availableCommands.put("login", new CommandLogin("login: login to manipulate the collection"));
        availableCommands.put("register", new CommandRegister("register: register new account"));
        availableCommands.put("reset", new CommandReset("reset: set new password wth activation code"));
        availableCommands.put("send", new CommandSend("send: send activation code to your email"));
        availableCommands.put("exit", new CommandExit("exit : exit the program (without saving to file)"));
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
                cm_splited = line.trim().split(" ", 2);
                if (availableCommands.containsKey(cm_splited[0])) {
                    switch (cm_splited[0]){
                        case "self_handled_error": to.println(cm_splited[1]+'\0'); break;
                        case "history": to.println(displayHistory(14)+'\0'); history = appendArray(history,"history"); break;
                        case "help": to.println(availableCommands.get("help").execute(availableCommands.values())+'\0');
                            history = appendArray(history,"help"); break;
                        case "login":
                        case "reset":
                        case "send":
                        case "add":
                        case "add_if_min":
                        case "remove_by_id":
                        case "remove_greater":
                        case "update":
                        case "register": to.println(availableCommands.get(cm_splited[0]).execute(cm_splited[1],this)+"\0");
                            history = appendArray(history,"help"); break;
                        case "info":
                        case "clear": to.println(availableCommands.get(cm_splited[0]).execute(this)+"\0");
                            history = appendArray(history,"clear"); break;
                        default:
                                if (cm_splited.length == 1)
                                to.println(availableCommands.get(cm_splited[0]).execute(this)+'\0');
                                else if (cm_splited.length == 2)
                                to.println(availableCommands.getOrDefault(cm_splited[0], errorCommand).execute(cm_splited[1],this)+'\0');
                                history = appendArray(history, cm_splited[0]);
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
    private void log(String s, int level) {
        switch (level) {
            case 4: LOGGER.severe(s); break;
            case 3: LOGGER.warning(s); break;
            case 2: LOGGER.info(s); break;
            case 1: LOGGER.finest(s); break;
        }
    }
}
