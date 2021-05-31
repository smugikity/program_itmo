package commands;

import datapack.Pack;
import datapack.StringPack;
import datapack.UpdatePack;
import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.Iterator;

public class CommandUpdate extends Command {
    public CommandUpdate(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(String s, ServerCommandReader caller) {
        String[] d = s.split(",",2);
        Long cId;
        boolean rm = false;
        try {cId = Long.parseLong(d[0]);} catch (NumberFormatException ex) {
            return new StringPack(false,"ID must be a number");
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
            p.setOwner_id(caller.getID());
            if (!setData(p, d[1])) return new StringPack(false,"Parsing error");
            getCollection().add(p);
            if (save()) return new UpdatePack(p.getId(),p);
        }
        return new StringPack(false,"ID owner or id Person not match");
    }
}
