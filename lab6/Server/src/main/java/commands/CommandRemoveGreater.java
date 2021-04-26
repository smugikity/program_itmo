package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandRemoveGreater extends Command {
    public CommandRemoveGreater(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute(String data) {
        Person person = new Person();
        setData(person, data);
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
