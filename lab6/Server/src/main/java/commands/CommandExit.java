package commands;

import server.ServerReader;

public class CommandExit extends Command {
    public CommandExit(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute() {
        return "";
    }
}
