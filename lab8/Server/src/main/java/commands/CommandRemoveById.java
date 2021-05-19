package commands;

import lab5.legacy.Person;
import server.ServerCommandReader;

import java.util.Iterator;

public class CommandRemoveById extends Command {
    public CommandRemoveById(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String s, ServerCommandReader caller) {
        long cId;
        try {cId = Long.parseLong(s);} catch (NumberFormatException ex) {
            return ("ID must be a number");
        }
        Iterator<Person> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if ((long) p.getId() == cId && p.getOwner_id() == caller.getID()) {
                iterator.remove();
                if (save()) return ("Deleted person with id: "+p.getId());
                else return "Error occurred. Please try again";
            }
        }
        return ("ID owner or id Person not match");
    }
}
