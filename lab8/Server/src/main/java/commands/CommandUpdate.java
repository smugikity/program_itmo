package commands;

import lab5.legacy.Person;
import server.ServerCommandReader;

import java.util.Iterator;

public class CommandUpdate extends Command {
    public CommandUpdate(String des) {
        setDescription(des);
    }
    @Override
    public synchronized String execute(String s, ServerCommandReader caller) {
        String[] d = s.split(",",2);
        Long cId;
        boolean rm = false;
        try {cId = Long.parseLong(d[0]);} catch (NumberFormatException ex) {
            return ("ID must be a number");
        }
        Iterator<Person> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            //System.out.println(p.getId());
            if ((long)cId == (long)p.getId()) {
                if (p.getOwner_id()==caller.getID()) {
                    iterator.remove();
                    rm = true;
                } else break;
            }
        }
        if (rm) {
            Person p = new Person();
            p.setId(cId);
            if (!setData(p, d[1])) return "Parsing error";
            getCollection().add(p);
            if (save()) return ("Updated person "+p.getName()+" successfully with id "+p.getId());
        }
        return ("ID owner or id Person not match");
    }
}
