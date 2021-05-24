package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.Iterator;

public class CommandRemoveGreater extends Command {
    public CommandRemoveGreater(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data, ServerCommandReader caller) {
        Person person = new Person();
        if (!setData(person,data)) return "Parsing error";
        boolean none = true;
        StringBuilder result = new StringBuilder();
        Iterator<Person> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.compareTo(person)>0) {
                iterator.remove();
                result.append("Removed person " + p.getName() + " successfully with id " + p.getId() + "\n");
            }
        }
        if (result.toString().isEmpty()) return ("No person was removed");
        else {
            if (save()) return result.toString()+"\1";
            else return "Error occurred. Please try again";
        }
    }
}
