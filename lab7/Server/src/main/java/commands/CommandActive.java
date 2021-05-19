package commands;

import server.ServerCommandReader;

public class CommandActive extends Command {
    public CommandActive(String des) {
        setDescription(des);
    }
    @Override
    public String execute(ServerCommandReader caller) {
        return "";
    }
}
