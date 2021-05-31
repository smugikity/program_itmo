package commands;

import datapack.Pack;
import main.ServerCommandReader;

public class CommandExit extends Command {
    public CommandExit(String des) {
        setDescription(des);
    }
    @Override
    public Pack execute(ServerCommandReader caller) {
        return null;
    }
}
