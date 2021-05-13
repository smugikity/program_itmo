package commands;

import server.ServerReader;

public class CommandClear extends Command {
    public CommandClear(ServerReader serverReader, String des) {
        super(serverReader);
        setDescription(des);
    }
    @Override
    public synchronized String execute() {
        getCollection().clear();
        return ("Collection cleared");
    }
}
