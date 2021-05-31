package commands;

import datapack.Pack;
import datapack.RemovePack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.HashSet;
import java.util.Iterator;

public class CommandClear extends Command {
    public CommandClear(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(ServerCommandReader caller) {
        Iterator<Person> iterator = getCollection().iterator();
        HashSet<Long> removed = new HashSet<>();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getOwner_id()==caller.getID()) {
                removed.add(p.getId());
                iterator.remove();
            }
        }
        if (save()) return new RemovePack(removed);
        else return new StringPack(false,"Error occurred. Please try again");
    }
}
