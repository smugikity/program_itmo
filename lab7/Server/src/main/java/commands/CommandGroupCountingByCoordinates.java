package commands;

import datapack.Pack;
import datapack.StringPack;
import main.ServerCommandReader;

public class CommandGroupCountingByCoordinates extends  Command{
    public CommandGroupCountingByCoordinates(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        if (getCollection().isEmpty()) {
            return new StringPack(true,"Colletion is empty");
        }
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i=0;count<getCollection().size();i++) {
            int finalI = i;
            long temp = getCollection().stream().filter(p->
                    Math.pow(p.getCol_coord_x(),2)+Math.pow(p.getCol_coord_y(),2)>=Math.pow(finalI*10,2) &&
                            Math.pow(p.getCol_coord_x(),2)+Math.pow(p.getCol_coord_y(),2)<Math.pow((finalI+1)*10,2)).count();
            result.append("There is "+temp+" people in range from "+finalI*10+" to "+(finalI+1)*10);
            count += temp;
        }
        return new StringPack(true,result.toString());
    }
}
