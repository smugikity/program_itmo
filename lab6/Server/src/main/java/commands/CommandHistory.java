package commands;

import server.ServerReader;

public class CommandHistory extends Command {
    public CommandHistory(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public String execute() {
        return "";
    }
}
