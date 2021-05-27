package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandInfo extends Command {
    public CommandInfo(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        return ("My ID:"+caller.getID()+"\nType of Collection element: "+ Person.class.getName()+"\n")+("Size: "+getCollection().size()+"\n")
                +("Initial time: "+getServerReader().timeStamp)+"\1";
    }
}
