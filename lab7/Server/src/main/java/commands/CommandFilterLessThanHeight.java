package commands;

import datapack.Pack;
import datapack.StringPack;
import lab5.legacy.Person;
import main.ServerCommandReader;

public class CommandFilterLessThanHeight extends Command {
    public CommandFilterLessThanHeight(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(String s, ServerCommandReader caller) {
        float cH;
        try {cH = Float.parseFloat(s);} catch (NumberFormatException ex) {
            return new StringPack(false,"Height must be a number");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Person p: getCollection()) {
            if ((float) p.getHeight() < cH) {
                stringBuilder.append(p.toString()+"\n");
            }
        }
        if (stringBuilder.toString().isEmpty()) return new StringPack(true,"Theres no person with heigh less than "+cH);
        else return new StringPack(true,stringBuilder.toString());
    }
}
