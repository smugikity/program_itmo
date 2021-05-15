package commands;

import lab5.legacy.Person;

public class CommandAdd extends Command {
    public CommandAdd(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        Person p = new Person();
        if (!setData(p,data)) return "Parsing error";
        getCollection().add(p);
        return ("Add person "+p.getName()+" successfully with id "+p.getId());
    }
}
