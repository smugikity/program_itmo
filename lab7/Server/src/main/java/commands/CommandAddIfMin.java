package commands;

import datapack.AddPack;
import datapack.Pack;
import datapack.StringPack;
import lab5.legacy.*;
import main.ServerCommandReader;

public class CommandAddIfMin extends Command {
    public CommandAddIfMin(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(String data, ServerCommandReader caller) {
        Person p = new Person();
        if (!setData(p,data)) return new StringPack(false,"Parsing error");
        p.setOwner_id(caller.getID());
        if (getCollection().isEmpty()) {
            getCollection().add(p);
            if (save()) return new AddPack(p);
            else return new StringPack(false,"Error occurred. Please try again");
        }
        Person min=getCollection().stream().min(Person::compareTo).get();
        if (p.compareTo(min)<0) {
            getCollection().add(p);
            if (save()) return new AddPack(p);
            else return new StringPack(false,"Error occurred. Please try again");
        }
        else return new StringPack (false,"Value of new person larger than minimum in collection");
    }
}
