package commands;

import datapack.Pack;
import main.ServerCommandReader;

public class CommandActive extends Command {
    public CommandActive(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        return null;
    }
}
