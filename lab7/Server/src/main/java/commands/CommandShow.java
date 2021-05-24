package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandShow extends Command {
    public CommandShow(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        if (getCollection().isEmpty()) {
            return "Collection is Empty";
        }
        StringBuilder result = new StringBuilder();
        for (Person person: getCollection()) {
            result.append(person.toString()+"\n");
        }
        return result.toString()+"\1";
    }
}
