package commands;

import lab5.legacy.Person;
import main.ServerCommandReader;

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
        StringBuilder stringBuilder = new StringBuilder();
        for (Person p: getCollection()) {
            if ((float) p.getHeight() < cH) {
                stringBuilder.append(p.toString()+"\n");
            }
        }
        if (stringBuilder.toString().isEmpty()) return ("Theres no person with heigh less than "+cH+"");
        else return stringBuilder.toString()+"";
    }
}
