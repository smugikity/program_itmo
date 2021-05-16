package commands;

import lab5.legacy.Person;
import server.ServerCommandReader;

public class CommandAddIfMin extends Command {
    public CommandAddIfMin(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String data, ServerCommandReader caller) {
        Person per = new Person();
        if (!setData(per,data)) return "Parsing error";
        per.setOwner_id(caller.getID());
        if (getCollection().isEmpty()) {
            getCollection().add(per);
            if (save()) return ("Add person "+per.getName()+" successfully with id "+per.getId());
            else return "Error occurred. Please try again";
        }
        Person min=null;
        for (Person p: getCollection()) {
            if (p.compareTo(min)<=0||min==null) {
                min = p;
            }
        }
        if (per.compareTo(min)<0) {
            getCollection().add(per);
            if (save()) return ("Add person "+per.getName()+" successfully with id "+per.getId());
            else return "Error occurred. Please try again";
        }
        else return ("Value of new person larger than minimum in collection");
    }
}
