package commands;

import datapack.Pack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandInfo extends Command {
    public CommandInfo(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        String str = "My ID:"+caller.getID()+"\nType of Collection element: "+ Person.class.getName()+("Size: "+getCollection().size()+"\n")+("Initial time: "+getServerReader().getTimeStamp());
        return new StringPack(true,str);
    }
}
