package commands;

import lab5.legacy.Person;

import java.util.ArrayList;

public class CommandMaxByLocation extends Command {
    public CommandMaxByLocation(String des) {
        setDescription(des);
    }
    @Override
    public String execute() {
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
        String result = "";
        for (Person p: maxP) {
            result += p.toString()+"\n";
        }
        return result;
    }
}
