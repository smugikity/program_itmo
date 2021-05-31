package commands;

import datapack.Pack;
import datapack.RemovePack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommandRemoveById extends Command {
    public CommandRemoveById(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(String s, ServerCommandReader caller) {
        long cId;
        try {cId = Long.parseLong(s);} catch (NumberFormatException ex) {
            return new StringPack(false,"ID must be a number");
        }
        Iterator<Person> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if ((long) p.getId() == cId && p.getOwner_id() == caller.getID()) {
                iterator.remove();
                if (save()) {
                    Set<Long> i = new HashSet<Long>(); i.add(p.getId());
                    return new RemovePack(i);
                }
                else return new StringPack(false,"Error occurred. Please try again");
            }
        }
        return new StringPack(false,"ID owner or id Person not match");
    }
}
