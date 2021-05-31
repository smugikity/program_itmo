package commands;

import datapack.Pack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandShow extends Command {
    public CommandShow(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        if (getCollection().isEmpty()) {
            return new StringPack(true,"Collection is Empty");
        }
        StringBuilder result = new StringBuilder();
        for (Person person: getCollection()) {
            result.append(person.toString()+"\n");
        }
        return new StringPack(true,result.toString());
    }
}
