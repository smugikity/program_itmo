package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandRemoveGreater extends Command {
    public CommandRemoveGreater(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        Person person = new Person();
        if (!setData(person,data)) return "Parsing error";
        boolean none = true;
        String result = "";
        for (Person p:getCollection()) {
            if (p.compareTo(person)>0) {
                getCollection().remove(p);
                result += ("Removed person " + p.getName() + " successfully with id " + p.getId() + "\n");
            }
        }
        if (result.isEmpty()) return ("No person was removed");
        else return result;
    }
}
