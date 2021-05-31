package commands;

import datapack.Pack;
import datapack.RemovePack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommandRemoveGreater extends Command {
    public CommandRemoveGreater(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(String data, ServerCommandReader caller) {
        Person person = new Person();
        if (!setData(person,data)) return new StringPack(false,"Parsing error");
        boolean none = true;
        Set<Long> result = new HashSet<>();
        Iterator<Person> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.compareTo(person)>0 && p.getOwner_id()==caller.getID()) {
                iterator.remove();
                result.add(p.getId());
            }
        }
        if (result.isEmpty()) return new StringPack(false,"No person was removed");
        else {
            if (save()) return new RemovePack(result);
            else return new StringPack(false,"Error occurred. Please try again");
        }
    }
}
