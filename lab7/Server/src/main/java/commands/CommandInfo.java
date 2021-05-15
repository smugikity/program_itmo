package commands;

import lab5.legacy.Person;

public class CommandInfo extends Command {
    public CommandInfo(String des) {
        setDescription(des);
    }
    @Override
    public String execute() {
        return ("Type of Collection element: "+ Person.class.getName()+"\n")+("Size: "+getCollection().size()+"\n")
                +("Initial time: "+getServerReader().timeStamp);
    }
}
