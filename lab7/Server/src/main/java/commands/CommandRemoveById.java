package commands;

import lab5.legacy.Person;

public class CommandRemoveById extends Command {
    public CommandRemoveById(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String s) {
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
