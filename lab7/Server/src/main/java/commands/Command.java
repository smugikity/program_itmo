package commands;

import lab5.legacy.*;
import server.ServerReader;

import java.util.Collection;
import java.util.Set;

public abstract class Command {
    public ServerReader serverReader;
    private String description;
    protected String invalidArguments="Error: Invalid arguments";
    protected String sqlException="Error: SQL Exception";

    public Command(ServerReader serverReader) {
        this.serverReader = serverReader;
    }

    public synchronized String execute() {
        return "Argument missing.";
    }
    public synchronized String execute(Collection<Command> avaicm) {
        return "Argument missing.";
    }
    public synchronized String execute(String arg) {
        return execute();
    }

    public ServerReader getServerReader() {
        return serverReader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServerReader(ServerReader serverReader) {
        this.serverReader = serverReader;
    }

    public Set<Person> getCollection(){
        return serverReader.getCollectionPerson();
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
}

