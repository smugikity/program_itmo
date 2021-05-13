package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandShow extends Command {
    public CommandShow(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute() {
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
