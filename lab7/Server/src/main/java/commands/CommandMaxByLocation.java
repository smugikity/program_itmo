package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

import java.util.ArrayList;

public class CommandMaxByLocation extends Command {
    public CommandMaxByLocation(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        if (getCollection().isEmpty()) {
            return ("Collection is empty");
        }
        ArrayList<Person> maxP = new ArrayList<Person>();
        for (Person p: getCollection()) {
            if (maxP.isEmpty() || p.getLocationValue() > maxP.get(0).getLocationValue()) {
                maxP.clear();
                maxP.add(p);
            } else if (p.getLocationValue() == maxP.get(0).getLocationValue()) {
                maxP.add(p);
            }
        }
        StringBuilder result = new StringBuilder();
        for (Person p: maxP) {
            result.append(p.toString()+"\n");
        }
        return result.toString()+"";
    }
}
