package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandAdd extends Command {
    public CommandAdd(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data, ServerCommandReader caller) {
        Person p = new Person();
        if (!setData(p,data)) return "Parsing error";
        p.setOwner_id(caller.getID());
        getCollection().add(p);
        if (save()) return ("Add person "+p.getName()+" successfully with id "+p.getId()+"\1");
        else return "Error occurred. Please try again";
    }
}
