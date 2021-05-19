package commands;

import server.ServerCommandReader;

public class CommandExit extends Command {
    public CommandExit(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        return "";
    }
}
