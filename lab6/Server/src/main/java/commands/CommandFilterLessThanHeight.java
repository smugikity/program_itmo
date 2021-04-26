package commands;

import lab5.legacy.Person;
import server.ServerReader;

public class CommandFilterLessThanHeight extends Command {
    public CommandFilterLessThanHeight(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute(String s) {
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
