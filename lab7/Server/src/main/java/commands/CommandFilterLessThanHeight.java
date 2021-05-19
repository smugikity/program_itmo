package commands;

import lab5.legacy.Person;
import server.ServerCommandReader;

public class CommandFilterLessThanHeight extends Command {
    public CommandFilterLessThanHeight(String des) {
        setDescription(des);
    }
    @Override
    public String execute(String s, ServerCommandReader caller) {
        float cH;
        try {cH = Float.parseFloat(s);} catch (NumberFormatException ex) {
            return ("Height must be a number");
        }

        String result = "";
        for (Person p: getCollection()) {
            if ((float) p.getHeight() < cH) {
                result += p.toString()+"\n";
            }
        }
        if (result.isEmpty()) return ("Theres no person with heigh less than "+cH);
        else return result;
    }
}
