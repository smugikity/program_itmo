package commands;

import lab5.legacy.*;
import server.Server;
import server.ServerCommandReader;
import server.ServerReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class Command {
    private ServerCommandReader caller;
    private String description;
    protected String invalidArguments="Error: Invalid arguments";
    protected String sqlException="Error: SQL Exception";

    protected ServerReader getServerReader() {
        return ServerReader.getInstance();
    }
    public String execute() {
        return "Argument missing.";
    }
    public String execute(Collection<Command> avaicm) {
        return "Argument missing.";
    }
    public String execute(String arg) {
        return execute();
    }
    public String execute(String arg, ServerCommandReader caller) {return null;}
    public String execute(ServerCommandReader caller) {return null;}

    public ServerCommandReader getCaller() {
        return caller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCaller(ServerCommandReader caller) {
        this.caller = caller;
    }

    public Set<Person> getCollection(){
        return ServerReader.getInstance().getCollectionPerson();
    }

    public boolean setData(Person p, String data) {
        try {
            String[] d = data.split(",", 11);
            p.setName(d[0]);
            Coordinates c = new Coordinates(Double.parseDouble(d[1]), Integer.parseInt(d[2]));
            p.setCoordinates(c);
            p.setHeight(Float.parseFloat(d[3]));
            p.setWeight(Long.parseLong(d[4]));
            p.setHairColor(Color.valueOf(d[5]));
            p.setNationality(Country.valueOf(d[6]));
            Location l = new Location(Double.parseDouble(d[7]), Long.parseLong(d[8]), Double.parseDouble(d[9]), d[10]);
            p.setLocation(l);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected void login(int id, ServerCommandReader caller) {
        caller.setID(id);
        Server.getClients().add(id);
        HashMap<String, Command> availableCommands = caller.getAvailableCommands();
        availableCommands.remove("login");
        availableCommands.remove("register");
        availableCommands.remove("reset");
        availableCommands.remove("send");
        availableCommands.put("logout", new CommandShow("logout: log out"));
        availableCommands.put("show", new CommandShow("show : print all collection items as string to standard output"));
        availableCommands.put("add", new CommandAdd("add {element} : add a new element to the collection"));
        availableCommands.put("add_if_min", new CommandAddIfMin("add_if_min {element} : add a new element to the collection if its value is less than the smallest element in this collection"));
        availableCommands.put("clear", new CommandClear("clear : clear the collection"));
        availableCommands.put("filter_less_than_height", new CommandFilterLessThanHeight("filter_less_than_height height : display elements whose height field value is less than the specified one"));
        availableCommands.put("group_counting_by_coordinates", new CommandGroupCountingByCoordinates("group_counting_by_coordinates : group the elements of the collection by the value of the coordinates field, display the number of elements in each group"));
        availableCommands.put("info", new CommandInfo("info : print information about the collection (type, date of initialization, number of elements, etc.) to standard output"));
        availableCommands.put("max_by_location", new CommandMaxByLocation("max_by_location : display any object from the collection, the value of the location field of which is the maximum"));
        availableCommands.put("remove_by_id", new CommandRemoveById("remove_by_id id : remove an item from the collection by its id"));
        availableCommands.put("remove_greater", new CommandRemoveGreater("remove_greater {element} : remove all elements from the collection that are greater than the specified one"));
        //availableCommands.put("save", new CommandSave("save : save the collection to a file"));
        availableCommands.put("update", new CommandUpdate("update id {element} : update the value of the collection element whose id is equal to the given one"));
    }
    protected void logout(ServerCommandReader caller) {
        caller.setID(0);
        HashMap<String, Command> availableCommands = caller.getAvailableCommands();
        availableCommands.put("login", new CommandLogin("login: login to manipulate the collection"));
        availableCommands.put("register", new CommandRegister("register: register new account"));
        availableCommands.put("reset", new CommandReset("reset: set new password wth activation code"));
        availableCommands.put("send", new CommandSend("send: send activation code to your email"));
        availableCommands.remove("show");
        availableCommands.remove("add");
        availableCommands.remove("add_if_min");
        availableCommands.remove("clear");
        availableCommands.remove("filter_less_than_height");
        availableCommands.remove("group_counting_by_coordinates");
        availableCommands.remove("info");
        availableCommands.remove("max_by_location");
        availableCommands.remove("remove_by_id");
        availableCommands.remove("remove_greater");
        //availableCommands.remove("save");
        availableCommands.remove("update");
        availableCommands.remove("logout");
    }
    protected boolean save() {
        try (Connection connection = ServerReader.getInstance().getConnection();
             Statement request = connection.createStatement()) {
            connection.setAutoCommit(false);
            request.addBatch("DELETE FROM PEOPLE;");
            for (Person p: getCollection()) {
                String result = "INSERT INTO PEOPLE VALUES ("+p.getOwner_id()+",'"+p.getName()+"',"+p.getCoordinates().getX()+","
                        +p.getCoordinates().getY()+","+p.getHeight()+","+p.getWeight()+",'"+p.getHairColor().toString()+"','"
                        +p.getNationality().toString()+"',"+p.getLocation().getX()+","+p.getLocation().getY()+","
                        +p.getLocation().getZ()+",'"+p.getLocation().getName()+"');";
                request.addBatch(result);
            }
            request.executeBatch();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

