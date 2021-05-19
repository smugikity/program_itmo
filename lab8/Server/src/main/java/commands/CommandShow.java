package commands;

import lab5.legacy.Person;
import server.ServerCommandReader;

public class CommandShow extends Command {
    public CommandShow(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        if (getCollection().isEmpty()) {
            return "Collection is Empty";
        }
        String result = "";
        for (Person person: getCollection()) {
            result+=(person.toString()+"\n");
        }
        return result;
    }
}
