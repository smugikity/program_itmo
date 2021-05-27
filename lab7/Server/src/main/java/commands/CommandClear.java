package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.Iterator;

public class CommandClear extends Command {
    public CommandClear(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(ServerCommandReader caller) {
        Iterator<Person> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getOwner_id()==caller.getID())
            iterator.remove();
        }
        if (save()) return ("Collection cleared");
        else return "Error occurred. Please try again";
    }
}
