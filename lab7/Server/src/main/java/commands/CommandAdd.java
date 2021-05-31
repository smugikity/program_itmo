package commands;

import datapack.AddPack;
import datapack.Pack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandAdd extends Command {
    public CommandAdd(String des) {
        setDescription(des);
    }
    @Override
    public synchronized Pack execute(String data, ServerCommandReader caller) {
        Person p = new Person();
        if (!setData(p,data)) return new StringPack(false,"Parsing error");
        p.setOwner_id(caller.getID());
        getCollection().add(p);
        if (save()) return new AddPack(p);
        else return new StringPack(false,"Error occurred. Please try again");
    }
}
