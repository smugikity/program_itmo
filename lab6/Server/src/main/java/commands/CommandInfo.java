package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandInfo extends Command {
    public CommandInfo(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute() {
        return ("Type of Collection element: "+ Person.class.getName()+"\n")+("Size: "+getCollection().size()+"\n")
                +("Initial time: "+getServerReader().timeStamp);
    }
}
