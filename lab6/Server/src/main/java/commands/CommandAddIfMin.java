package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandAddIfMin extends Command {
    public CommandAddIfMin(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data) {
        Person min=null;
        for (Person p: getCollection()) {
            if (p.compareTo(min)<=0||min==null) {
                min = p;
            }
        }
        Person p = new Person();
        setData(p,data);
        if (p.compareTo(min)<0) {
            getCollection().add(p);
            return ("Added person "+p.getName()+" successfully with id "+p.getId());
        }
        else return ("Value of new person larger than minimum in collection");
    }
}
