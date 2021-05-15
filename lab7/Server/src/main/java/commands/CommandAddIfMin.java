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
        Person per = new Person();
        if (!setData(per,data)) return "Parsing error";
        if (getCollection().isEmpty()) {
            getCollection().add(per); return ("Added person "+per.getName()+" successfully with id "+per.getId());
        }
        Person min=null;
        for (Person p: getCollection()) {
            if (p.compareTo(min)<=0||min==null) {
                min = p;
            }
        }
        if (per.compareTo(min)<0) {
            getCollection().add(per); return ("Added person "+per.getName()+" successfully with id "+per.getId());
        }
        else return ("Value of new person larger than minimum in collection");
    }
}
