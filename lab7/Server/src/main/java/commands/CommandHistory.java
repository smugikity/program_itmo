package commands;

import datapack.Pack;
import main.ServerCommandReader;

public class CommandHistory extends Command {
    public CommandHistory(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        return null;
    }
}
