package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandRemoveById extends Command {
    public CommandRemoveById(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute(String s) {
        long cId;
        try {cId = Long.parseLong(s);} catch (NumberFormatException ex) {
            return ("ID must be a number");
        }
        for (Person p: getCollection()) {
            if ((long) p.getId() == cId) {
                getCollection().remove(p);
                return ("Deleted person with id: "+p.getId());
            }
        }
        return ("Didn't find any person with id: "+s);
    }
}
