package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandAdd extends Command {
    public CommandAdd(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        Person p = new Person();
        setData(p,data);
        getCollection().add(p);
        return ("Add person "+p.getName()+" successfully with id "+p.getId());
    }
}
