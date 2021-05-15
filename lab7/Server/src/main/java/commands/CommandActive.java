package commands;

import server.ServerReader;

public class CommandActive extends Command {
    public CommandActive(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute() {
        return "";
    }
}
